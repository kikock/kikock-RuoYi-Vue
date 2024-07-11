package com.ruoyi.framework.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @project_name: RuoYi-Vue-master
 * @description: MinIO配置类
 * @create_name: kikock
 * @create_date: 2024-07-10 16:48
 **/
@Data
@Configuration
public class MinioConfig{
    //
    @Value("${file.minio.endpoint}")
    private String endpoint;
    @Value("${file.minio.accessKey}")
    private String accessKey;
    @Value("${file.minio.secretKey}")
    private String secretKey;
    @Value("${file.minio.bucket}")
    private String bucketName;

    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        return minioClient;
    }

}
