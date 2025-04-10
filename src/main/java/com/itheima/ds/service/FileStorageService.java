package com.itheima.ds.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件存储服务接口
 */
public interface FileStorageService {
    
    /**
     * 上传文件
     * @param file 文件对象
     * @param bucketName 桶名称
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String bucketName);
    
    /**
     * 上传文件
     * @param inputStream 输入流
     * @param originalFilename 原始文件名
     * @param bucketName 桶名称
     * @return 文件访问URL
     */
    String uploadFile(InputStream inputStream, String originalFilename, String bucketName);
    
    /**
     * 删除文件
     * @param fileName 文件名
     * @param bucketName 桶名称
     * @return 是否删除成功
     */
    boolean deleteFile(String fileName, String bucketName);
    
    /**
     * 获取文件访问URL
     * @param fileName 文件名
     * @param bucketName 桶名称
     * @return 文件访问URL
     */
    String getFileUrl(String fileName, String bucketName);
} 