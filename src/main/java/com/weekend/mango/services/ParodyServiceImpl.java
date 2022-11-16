package com.weekend.mango.services;

import com.weekend.mango.entities.ParodyEntity;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.models.Parody;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.ParodyEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ParodyServiceImpl implements ParodyService{

    private final ParodyEntityRepository parodyEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public ParodyServiceImpl(ParodyEntityRepository parodyEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.parodyEntityRepository = parodyEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<ParodyEntity> iterator, Long id) {

        long index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public List<Parody> getParodies() {

        List<ParodyEntity> parodyEntities = parodyEntityRepository.findAll();

        return parodyEntities.stream().map((parodyEntity) ->
                Parody.builder()
                        .id(parodyEntity.getId())
                        .name(parodyEntity.getName())
                        .build()
        ).toList();
    }

    @Override
    public Map<String, Object> getPaginatedParodies(int page, int size) {

        List<Parody> parodyList = new ArrayList<>();
        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        Page<ParodyEntity> parodyEntities = parodyEntityRepository.findAll(paging);
        Iterable<ParodyEntity> parodyEntityIterable = parodyEntityRepository.findAll(paging.getSort());

        List<PageIndex> pageIndices = new ArrayList<>();

        for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
        {
            ParodyEntity parodyAlphabet = parodyEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (parodyAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(parodyEntityIterable.iterator(),parodyAlphabet.getId()));
                pageIndices.add(pageIndex);

            }
        }

        for (ParodyEntity p: parodyEntities
             ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByParodyId(p.getId());
            Parody parody = new Parody();
            parody.setId(p.getId());
            parody.setName(p.getName());
            parody.setMangaCount(mangaCount);
            parodyList.add(parody);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("parodyList",parodyList);
        response.put("currentPage",parodyEntities.getNumber());
        response.put("totalItems",parodyEntities.getTotalElements());
        response.put("totalPages",parodyEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
    }

    @Override
    public Parody saveParody(Parody parodyModel) throws Exception {

        ParodyEntity parody = new ParodyEntity();

        try {
            parody.setName(parodyModel.getName());
            parodyEntityRepository.save(parody);
            parodyModel.setId(parody.getId());

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return parodyModel;
    }

    @Override
    public Parody updateParody(Long id, Parody parodyModel) throws Exception {

        try {
            Optional<ParodyEntity> fetchParody = parodyEntityRepository.findById(id);

            fetchParody.ifPresent(parodyEntity -> {
                parodyEntity.setName(parodyModel.getName());
                parodyEntityRepository.save(parodyEntity);

                parodyModel.setName(parodyEntity.getName());
            });
        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

        return parodyModel;
    }

    @Override
    public boolean deleteParody(Long id) throws Exception {

        try {
            Optional<ParodyEntity> fetchParody = parodyEntityRepository.findById(id);

            fetchParody.ifPresent(parodyEntityRepository::delete);

        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }

        return true;
    }


}
