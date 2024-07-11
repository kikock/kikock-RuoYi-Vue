package com.ruoyi.web.controller.common;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.minio.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
@Api(value = "MinIO储存库接口",tags = "MinIO储存库接口")
public class MinioFileController{

    @Autowired
    MinioUtils minioService;

    //列表
    @GetMapping("/list")
    @ApiOperation("获取MinIO储存库附件列表")
    public AjaxResult list(){
        List<String> strings = minioService.listObjects();
        return AjaxResult.success(strings);
    }

    //删除
    @PutMapping("/delete")
    @ApiOperation("删除MinIO储存库附件")
    public AjaxResult delete(@RequestParam String filename){
        minioService.deleteObject(filename);
        return AjaxResult.success(true);
    }

    //上传文件
    @PostMapping("/upload")
    @ApiOperation("上传附件到MinIO储存库")
    public AjaxResult upload(@RequestParam("file") MultipartFile file){
        try {
            // todo 完善文件命名逻辑
            InputStream is = file.getInputStream(); //得到文件流
            String fileName = file.getOriginalFilename(); //文件名
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            // todo 完善类型校验逻辑
            String contentType = file.getContentType();  //类型
            minioService.uploadObject(is, newFileName, contentType);
            return AjaxResult.success(newFileName);
        } catch (Exception e) {
            throw new ServiceException("上传失败",HttpStatus.ERROR);
        }
    }

    //下载minio服务的文件
    @GetMapping("/download")
    @ApiOperation("下载MinIO储存库附件")
    public void download(@RequestParam String filename, HttpServletResponse response){
        try {
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
    @ApiOperation("获取MinIO储存库附件地址")
    public AjaxResult getHttpUrl(@RequestParam String filename){
        try {
            String url = minioService.getObjectUrl(filename);
            return AjaxResult.success(url);
        } catch (Exception e) {
            throw new ServiceException( e.getMessage(),HttpStatus.ERROR);
        }
    }


}
