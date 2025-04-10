package com.itheima.ds.service.impl;

import com.itheima.ds.exception.GlobalException;
import com.itheima.ds.service.FileStorageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO文件存储服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MinioFileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;
    
    @Value("${minio.endpoint}")
    private String endpoint;
    
    @Value("${minio.url-prefix:${minio.endpoint}}")
    private String urlPrefix;
    
    @Value("${minio.default-bucket:seckill}")
    private String defaultBucket;
    
    @Value("${minio.access-url-expire:7}")
    private Integer accessUrlExpire;

    @Override
    public String uploadFile(MultipartFile file, String bucketName) {
        if (file == null || file.isEmpty()) {
            throw new GlobalException("上传文件不能为空");
        }
        
        if (!StringUtils.hasText(bucketName)) {
            bucketName = defaultBucket;
        }
        
        try {
            return uploadFile(file.getInputStream(), file.getOriginalFilename(), bucketName);
        } catch (Exception e) {
            log.error("MinIO上传文件失败", e);
            throw new GlobalException("文件上传失败");
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String originalFilename, String bucketName) {
        if (inputStream == null) {
            throw new GlobalException("上传文件不能为空");
        }
        
        if (!StringUtils.hasText(bucketName)) {
            bucketName = defaultBucket;
        }
        
        try {
            // 检查桶是否存在，不存在则创建
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            
            // 生成文件名
            String fileName = generateFileName(originalFilename);
            
            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .contentType(getContentType(fileName))
                            .stream(inputStream, -1, 10485760)
                            .build());
            
            // 返回访问URL
            return getFileUrl(fileName, bucketName);
        } catch (Exception e) {
            log.error("MinIO上传文件失败", e);
            throw new GlobalException("文件上传失败");
        }
    }

    @Override
    public boolean deleteFile(String fileName, String bucketName) {
        if (!StringUtils.hasText(fileName)) {
            throw new GlobalException("文件名不能为空");
        }
        
        if (!StringUtils.hasText(bucketName)) {
            bucketName = defaultBucket;
        }
        
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
            return true;
        } catch (Exception e) {
            log.error("MinIO删除文件失败", e);
            return false;
        }
    }

    @Override
    public String getFileUrl(String fileName, String bucketName) {
        if (!StringUtils.hasText(fileName)) {
            throw new GlobalException("文件名不能为空");
        }
        
        if (!StringUtils.hasText(bucketName)) {
            bucketName = defaultBucket;
        }
        
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(accessUrlExpire, TimeUnit.DAYS)
                            .build());
            
            // 替换为自定义URL前缀（如果配置了）
            if (!endpoint.equals(urlPrefix)) {
                url = url.replace(endpoint, urlPrefix);
            }
            
            return url;
        } catch (Exception e) {
            log.error("MinIO获取文件URL失败", e);
            throw new GlobalException("获取文件访问路径失败");
        }
    }
    
    /**
     * 生成文件名
     */
    private String generateFileName(String originalFilename) {
        // 获取文件后缀
        String suffix = "";
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        // 使用UUID生成文件名
        return UUID.randomUUID().toString().replaceAll("-", "") + suffix;
    }
    
    /**
     * 获取文件ContentType
     */
    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".bmp")) {
            return "image/bmp";
        } else if (fileName.endsWith(".webp")) {
            return "image/webp";
        } else if (fileName.endsWith(".mp4")) {
            return "video/mp4";
        } else if (fileName.endsWith(".avi")) {
            return "video/x-msvideo";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
            return "application/msword";
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            return "application/vnd.ms-excel";
        } else if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
            return "application/vnd.ms-powerpoint";
        } else {
            return "application/octet-stream";
        }
    }
} 