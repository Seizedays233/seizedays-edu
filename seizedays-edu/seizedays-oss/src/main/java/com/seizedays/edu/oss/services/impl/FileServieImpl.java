package com.seizedays.edu.oss.services.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.seizedays.edu.common.constants.ResultCodeEnum;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.oss.services.FileService;
import com.seizedays.edu.oss.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class FileServieImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file) {

        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        String uploadUrl = null;

        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                //设置访问权限
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //创建文件上传目录 根据当前日期创建
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //原始文件名
            String oriFileName = file.getOriginalFilename();
            String fileType = oriFileName.substring(oriFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileType;

            //文件上传到服务器的具体位置
            String fileUrl = fileHost + "/" + filePath + "/" + fileName;

            //文件上传
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //拼接上传URL地址
            uploadUrl = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new SeizedaysException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return uploadUrl;
    }
}
