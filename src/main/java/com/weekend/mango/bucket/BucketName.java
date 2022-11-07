package com.weekend.mango.bucket;

public enum BucketName {

    BUCKET("dimasblog");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName(){
        return bucketName;
    }
}
