package com.weekend.mango.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStoreServiceImpl implements FileStoreService{

    private final AmazonS3 s3;

    @Autowired
    public FileStoreServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }



    @Override
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(stringStringMap -> {
            objectMetadata.setContentType("image/jpeg");
        });
        try {
            s3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to store content!",e);
        }
    }

    @Override
    public void saveThumbnail(String thumbnailPath, String originalFilename, ObjectMetadata thumbnailMetadata, InputStream inputStream) {

        thumbnailMetadata.setContentType("image/jpeg");

        try {
            s3.putObject(thumbnailPath, originalFilename, inputStream, thumbnailMetadata);
        } catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to store content!",e);
        }
    }

}
