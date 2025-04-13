package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@Api(tags = "文件管理接口")
@RestController
@RequestMapping("/file/v1")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> uploadFile(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "存储桶名称") @RequestParam(value = "bucketName", required = false) String bucketName) {
        
        String url = fileStorageService.uploadFile(file, bucketName);
        return Result.success(url);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteFile(
            @ApiParam(value = "文件名", required = true) @RequestParam("fileName") String fileName,
            @ApiParam(value = "存储桶名称") @RequestParam(value = "bucketName", required = false) String bucketName) {
        
        boolean result = fileStorageService.deleteFile(fileName, bucketName);
        return Result.success(result);
    }

    @ApiOperation("获取文件URL")
    @GetMapping("/url")
    public Result<String> getFileUrl(
            @ApiParam(value = "文件名", required = true) @RequestParam("fileName") String fileName,
            @ApiParam(value = "存储桶名称") @RequestParam(value = "bucketName", required = false) String bucketName) {
        
        String url = fileStorageService.getFileUrl(fileName, bucketName);
        return Result.success(url);
    }
} 