package com.tong.common.core.obs.obsEnum;

public enum BucketEnum {

    测试专用桶1("yuejiahuixuan-test1"),
    深蓝专用桶("tong-public");

    private String bucketName;

    private BucketEnum(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }
}
