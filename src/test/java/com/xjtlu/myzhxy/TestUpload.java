package com.xjtlu.myzhxy;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import com.qiniu.http.Response;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;


public class TestUpload {

    @Test
    public void testUpload(String fileName, MultipartFile file) throws QiniuException {
        String accessKey = "fsflNtY_jkOWRChdbkfcvGNbd1QZVyMJ6r-Wy_21";
        String secretKey = "DsMkh0FtfwQsnncuXR_J1m4LAVyS1ek0k-ksphXL";
        String bucketName = "cpt402";
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        String key = "file save key";
        Response r = uploadManager.put("hello world".getBytes(), key, token);

    }

}
