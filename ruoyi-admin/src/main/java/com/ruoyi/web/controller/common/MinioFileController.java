package com.ruoyi.web.controller.common;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.minio.MinioConfig;
import com.ruoyi.framework.minio.MinioUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @project_name: RuoYi-Vue-master
 * @description: MinIO储存库
 * @create_name: kikock
 * @create_date: 2024-07-10 16:57
 **/
@RestController
@RequestMapping("/minio")
@Tag(name = "MinIO储存库接口")
public class MinioFileController{

    @Autowired
    MinioUtils minioService;

    @Autowired
    MinioConfig minioConfig;
    //列表
    @GetMapping("/list")
    @Operation(summary = "获取MinIO储存库附件列表")
    public AjaxResult list(){
        List<String> strings = minioService.listObjects();
        return AjaxResult.success(strings);
    }

    //删除
    @PutMapping("/delete")
    @Operation(summary = "删除MinIO储存库附件")
    @Parameters({
            @Parameter(name = "filename", description = "文件名称", required = true, in = ParameterIn.QUERY)
    })
    public AjaxResult delete(@RequestParam String filename){
        minioService.deleteObject(filename);
        return AjaxResult.success(true);
    }

    //上传文件
    @PostMapping("/upload")
    @Operation(summary = "上传附件到MinIO储存库")
    @Parameters({
            @Parameter(name = "file", description = "文件", required = true, in = ParameterIn.QUERY)
    })
    public AjaxResult upload(@RequestParam("file") MultipartFile file){
        try {
            //http://119.6.253.214:8002/jx-minio/1720662975260.jpg
            StringBuilder sb =new StringBuilder();
            sb.append(minioConfig.getEndpoint()).append("/").append(minioConfig.getBucketName()).append("/");
            InputStream is = file.getInputStream(); //得到文件流
            String originalFilename = file.getOriginalFilename(); //文件名
            String newFileName = DateUtils.dateTime()+System.currentTimeMillis() + "." + StringUtils.substringAfterLast(originalFilename, ".");
            String contentType = file.getContentType();  //文件类型
            sb.append(newFileName);
            minioService.uploadObject(is, newFileName, contentType);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", sb.toString());
            ajax.put("fileName", sb.toString());
            ajax.put("newFileName", newFileName); //minio服务器文件名称
            ajax.put("originalFilename", originalFilename); //上传文件名称
            return ajax;
        } catch (Exception e) {
            throw new ServiceException("上传失败",HttpStatus.ERROR);
        }
    }

    //下载minio服务的文件
    @GetMapping("/download/{filename}")
    @Operation(summary = "下载MinIO储存库附件")
    @Parameters({
            @Parameter(name = "filename", description = "文件名称", required = true, in = ParameterIn.QUERY)
    })
    public void download(@PathVariable("filename") String filename, HttpServletResponse response){
        try {
            if (!FileUtils.checkAllowDownload(filename)) {
                throw new Exception(com.ruoyi.common.utils.StringUtils.format("文件名称({})非法，不允许下载。 ", filename));
            }
            InputStream fileInputStream = minioService.getObject(filename);
            // todo 完善文件命名逻辑
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(filename, ".");
            response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            throw new ServiceException("下载失败",HttpStatus.ERROR);
        }
    }

    //获取minio文件的下载地址
    @GetMapping("/getHttpUrl")
    @Operation(summary = "获取MinIO储存库附件地址")
    public AjaxResult getHttpUrl(@RequestParam String filename){
        try {
            String url = minioService.getObjectUrl(filename);
            return AjaxResult.success(url);
        } catch (Exception e) {
            throw new ServiceException( e.getMessage(),HttpStatus.ERROR);
        }
    }


}
