package com.weekend.mango.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.weekend.mango.bucket.BucketName;
import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.PageEntity;
import com.weekend.mango.models.PageModel;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.PageEntityRepository;
import org.apache.http.entity.ContentType;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class PageServiceImpl implements PageService {

    private final MangaEntityRepository mangaEntityRepository;
    private final PageEntityRepository pageEntityRepository;
    private final FileStoreService fileStoreService;

    public PageServiceImpl(MangaEntityRepository mangaEntityRepository, PageEntityRepository pageEntityRepository, FileStoreService fileStoreService) {
        this.mangaEntityRepository = mangaEntityRepository;
        this.pageEntityRepository = pageEntityRepository;
        this.fileStoreService = fileStoreService;
    }

    Optional<MangaEntity> getManga(Long id){
        return mangaEntityRepository.findById(id);
    }

    Optional<PageEntity> getPage(String file){
        return pageEntityRepository.findPageEntityByFile(file);
    }



    private BufferedImage thumbnailGenerator (BufferedImage bi)
    {
        if (bi == null)
            return null ;

        Scalr.Mode mode = Scalr.Mode.AUTOMATIC ;
        int maxSize = Math.min(350, 500) ;
        double dh = (double)bi.getHeight() ;
        if (dh > Double.MIN_VALUE)
        {
            double imageAspectRatio = (double)bi.getWidth() / dh ;
            if (500 * imageAspectRatio <= 350)
            {
                maxSize = 500;
                mode = Scalr.Mode.FIT_TO_HEIGHT ;
            }
            else
            {
                mode = Scalr.Mode.FIT_TO_WIDTH ;
            }
        }
        return Scalr.resize(bi, Scalr.Method.QUALITY, mode, maxSize, Scalr.OP_ANTIALIAS) ;
    }


    @Override
    public List<PageModel> getPagesByMangaId(Long mangaId) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);
            List<PageModel> pages = new ArrayList<>();
            if (fetchManga.isPresent()){
                List<PageEntity> fetchMangaPages = pageEntityRepository.findPageEntitiesByMangaId(fetchManga.get());
                for (PageEntity a:fetchMangaPages
                ) {
                    PageModel pageModel = new PageModel();
                    pageModel.setId(a.getId());
                    pageModel.setPageOrder(a.getPageOrder());
                    pageModel.setFile(a.getFile());
                    pages.add(pageModel);
                }
            }
            return pages;
        }catch (Exception e){
            throw new Exception("can't execute order " + e);
        }
    }

    @Override
    public PageModel createMangaPage(Long mangaId, PageModel pageModel, MultipartFile file) throws Exception {

        Optional<PageEntity> isDuplicate = getPage(pageModel.getFile());

        if (isDuplicate.isPresent()){
            return null;
        }

        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())){
            throw new Exception("file must be image");
        }

        BufferedImage thumbnail = thumbnailGenerator(ImageIO.read(file.getInputStream()));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(thumbnail,"jpg",os);
        byte[] buffer = os.toByteArray();
        InputStream is = new ByteArrayInputStream(buffer);





        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        ObjectMetadata thumbnailMetadata  = new ObjectMetadata();
        thumbnailMetadata.setContentLength(buffer.length);



        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);

            if (fetchManga.isPresent()){
                String path = String.format("%s/%s/%s", BucketName.BUCKET.getBucketName(),"galleries",fetchManga.get().getId());
                String thumbnailPath = String.format("%s/%s/%s", BucketName.BUCKET.getBucketName(),"galleries/thumbnail",fetchManga.get().getId());

                PageEntity pageEntity = new PageEntity();
                pageEntity.setPageOrder(pageModel.getPageOrder());
                pageEntity.setFile(pageModel.getFile());
                pageEntity.setMangaId(fetchManga.get());
                pageEntityRepository.save(pageEntity);
                pageModel.setId(pageEntity.getId());

                try {
                    fileStoreService.save(path,file.getOriginalFilename(),Optional.of(metadata),file.getInputStream());
                    fileStoreService.saveThumbnail(thumbnailPath, file.getOriginalFilename(), thumbnailMetadata,is);
                }catch (IOException e){
                    throw new IllegalStateException(e);
                }

                return pageModel;
            }else{
                throw new Exception("manga not found!");
            }

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

    }

    @Override
    public boolean deletePage(Long id, Long mangaId) throws Exception {
        try {
            Optional<MangaEntity> fetchManga =getManga(mangaId);
            if (fetchManga.isPresent()){
                Optional<PageEntity> fetchPage = pageEntityRepository.findPageEntityByIdAndMangaId(id,fetchManga.get());
                fetchPage.ifPresent(pageEntityRepository::delete);
            }else {
                throw new Exception("manga not found!");
            }
        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }
        return true;
    }
}
