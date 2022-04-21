package com.xjtlu.myzhxy.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @project: zhxy
 * @description: 上传文件的工具类
 */
public class UploadFile {

    //存储文件上传失败的错误信息
    private static Map<String, Object> error_result = new HashMap<>();
    //存储头像的上传结果信息
    private static Map<String, Object> upload_result = new HashMap<>();

    /**
     * @description: 效验所上传图片的大小及格式等信息...
     * @param: photo
     * @param: path
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    private static Map<String, Object> uploadPhoto(MultipartFile photo, String path) {
        //限制头像大小(20M)
        int MAX_SIZE = 20971520;
        //获取图片的原始名称
        String orginalName = photo.getOriginalFilename();
        //如果保存文件的路径不存在,则创建该目录
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        //限制上传文件的大小
        if (photo.getSize() > MAX_SIZE) {
            error_result.put("success", false);
            error_result.put("msg", "上传的图片大小不能超过20M哟!");
            return error_result;
        }
        // 限制上传的文件类型
        String[] suffixs = new String[]{".png", ".PNG", ".jpg", ".JPG", ".jpeg", ".JPEG", ".gif", ".GIF", ".bmp", ".BMP"};
        SuffixFileFilter suffixFileFilter = new SuffixFileFilter(suffixs);
        if (!suffixFileFilter.accept(new File(path + orginalName))) {
            error_result.put("success", false);
            error_result.put("msg", "禁止上传此类型文件! 请上传图片哟!");
            return error_result;
        }

        return null;
    }

    /**
     * @description: (提取公共代码 : 提高代码的可重用性)获取头像的上传结果信息
     * @param: photo
     * @param: dirPath
     * @param: portraitPath
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getUploadResult(MultipartFile photo, String dirPath, String portraitPath) {

        if (!photo.isEmpty() && photo.getSize() > 0) {
            //获取图片的原始名称
            String orginalName = photo.getOriginalFilename();
            //上传图片,error_result:存储头像上传失败的错误信息
            Map<String, Object> error_result = UploadFile.uploadPhoto(photo, dirPath);
            if (error_result != null) {
                return error_result;
            }
            //使用UUID重命名图片名称(uuid__原始图片名称)
            String newPhotoName = UUID.randomUUID() + "__" + orginalName;
            //将上传的文件保存到目标目录下
            try {
                photo.transferTo(new File(dirPath + newPhotoName));
                upload_result.put("success", true);
                upload_result.put("portrait_path", portraitPath + newPhotoName);//将存储头像的项目路径返回给页面
            } catch (IOException e) {
                e.printStackTrace();
                upload_result.put("success", false);
                upload_result.put("msg", "上传文件失败! 服务器端发生异常!");
                return upload_result;
            }

        } else {
            upload_result.put("success", false);
            upload_result.put("msg", "头像上传失败! 未找到指定图片!");
        }
        return upload_result;
    }

    public static void testUpload(String fileName, InputStream input) throws QiniuException {
        String accessKey = "fsflNtY_jkOWRChdbkfcvGNbd1QZVyMJ6r-Wy_21";
        String secretKey = "DsMkh0FtfwQsnncuXR_J1m4LAVyS1ek0k-ksphXL";
        String bucketName = "cpt402";
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        String key = fileName;
        Response r = uploadManager.put(input, key, token,null,null);

    }
}
