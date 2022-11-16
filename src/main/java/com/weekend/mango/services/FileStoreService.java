package com.weekend.mango.services;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {

    void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) throws Exception;

    void saveThumbnail(String thumbnailPath, String originalFilename, ObjectMetadata thumbnailMetadata, InputStream inputStream);
}
