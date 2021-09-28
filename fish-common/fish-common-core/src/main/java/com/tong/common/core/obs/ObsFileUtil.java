package com.tong.common.core.obs;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.CompleteMultipartUploadResult;
import com.obs.services.model.DeleteObjectResult;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.UploadFileRequest;
import com.tong.common.core.obs.obsConfig.ObsConfig;
import com.tong.common.core.obs.obsEnum.BucketEnum;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;
import javax.imageio.ImageIO;

public class ObsFileUtil {
    public ObsFileUtil() {
    }

    private static String generateObjectKey(String filePath, String prefix, boolean useOriginalName) {
        String suffix = null;
        if (useOriginalName) {
            suffix = filePath.substring(filePath.lastIndexOf("/") + 1);
        } else {
            suffix = UUID.randomUUID() + filePath.substring(filePath.lastIndexOf("."));
        }

        prefix = prefix.replaceAll("[^/!\\-_a-zA-Z0-9]", "");
        prefix = prefix.replaceAll("/{2,}", "/");
        if (!prefix.endsWith("/")) {
            prefix = prefix + "/";
        }

        if (prefix.startsWith("/")) {
            prefix = prefix.length() == 1 ? "" : prefix.substring(1);
        }

        return prefix + suffix;
    }

    public static String uploadFile(BucketEnum bucketEnum, String filePath, String prefix) {
        return uploadFile(bucketEnum, filePath, prefix, false);
    }

    public static String uploadFile(BucketEnum bucketEnum, String filePath, String prefix, boolean useOriginalName) {
        String bucketName = bucketEnum.getBucketName();
        String objectKey = generateObjectKey(filePath, prefix, useOriginalName);
        File file = new File(filePath);
        ObsClient obsClient = ObsConfig.getObsClient();
        PutObjectResult result = obsClient.putObject(bucketName, objectKey, file);

        try {
            obsClient.close();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return result.getObjectKey();
    }

    public static String uploadStream(BucketEnum bucketEnum, BufferedImage image, String objectKey) {
        String bucketName = bucketEnum.getBucketName();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", os);
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        ObsClient obsClient = ObsConfig.getObsClient();
        PutObjectResult result = obsClient.putObject(bucketName, objectKey, new ByteArrayInputStream(os.toByteArray()));

        try {
            obsClient.close();
            os.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return result.getObjectKey();
    }

    public static String uploadStream(BucketEnum bucketEnum, byte[] byteArr, String objectKey) {
        String bucketName = bucketEnum.getBucketName();
        ObsClient obsClient = ObsConfig.getObsClient();
        PutObjectResult result = obsClient.putObject(bucketName, objectKey, new ByteArrayInputStream(byteArr));

        try {
            obsClient.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return result.getObjectKey();
    }

    public static String multipartPartUpload(BucketEnum bucketEnum, String filePath, String prefix) {
        String bucketName = bucketEnum.getBucketName();
        String objectKey = generateObjectKey(filePath, prefix, false);
        UploadFileRequest request = new UploadFileRequest(bucketName, objectKey, filePath);
        request.setUploadFile(filePath);
        request.setTaskNum(5);
        request.setPartSize(52428800L);
        request.setEnableCheckpoint(true);
        ObsClient obsClient = ObsConfig.getObsClient();

        CompleteMultipartUploadResult result;
        try {
            result = obsClient.uploadFile(request);
        } catch (ObsException var10) {
            result = obsClient.uploadFile(request);
        }

        try {
            obsClient.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return result.getObjectKey();
    }

    public static void downloadObject(BucketEnum bucketEnum, String objectKey, String savePath) {
        ObsClient obsClient = ObsConfig.getObsClient();
        GetObjectRequest request = new GetObjectRequest(bucketEnum.getBucketName(), objectKey);
        ObsObject obsObject = obsClient.getObject(request);
        InputStream is = obsObject.getObjectContent();
        String saveDir = savePath.substring(0, savePath.lastIndexOf("/"));
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        byte[] buff = new byte[1024];
        boolean var10 = false;

        try {
            FileOutputStream os = new FileOutputStream(savePath);

            while(is.read(buff) != -1) {
                os.write(buff);
            }

            os.close();
            is.close();
            obsClient.close();
        } catch (IOException var12) {
            var12.printStackTrace();
        }

    }

    public static boolean deleteObject(BucketEnum bucketEnum, String obejctName) {
        ObsClient obsClient = ObsConfig.getObsClient();
        DeleteObjectResult deleteResult = obsClient.deleteObject(bucketEnum.getBucketName(), obejctName);

        try {
            obsClient.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return !deleteResult.isDeleteMarker();
    }

    public static void listAllObject(BucketEnum bucketEnum) {
        ObsClient obsClient = ObsConfig.getObsClient();
        ObjectListing result = obsClient.listObjects(bucketEnum.getBucketName());
        Iterator var4 = result.getObjects().iterator();

        while(var4.hasNext()) {
            ObsObject object = (ObsObject)var4.next();
            System.out.println(object.getObjectKey());
        }

        try {
            obsClient.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private static void deleteAllObject(BucketEnum bucketEnum) {
        ObsClient obsClient = ObsConfig.getObsClient();
        ObjectListing result = obsClient.listObjects(bucketEnum.getBucketName());
        Iterator var4 = result.getObjects().iterator();

        while(var4.hasNext()) {
            ObsObject object = (ObsObject)var4.next();
            obsClient.deleteObject(bucketEnum.getBucketName(), object.getObjectKey());
        }

        try {
            obsClient.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        System.out.println("已经删除了" + bucketEnum.getBucketName() + "桶中的所有对象");
    }
}
