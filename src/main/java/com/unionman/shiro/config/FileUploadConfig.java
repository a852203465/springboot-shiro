package com.unionman.shiro.config;

import com.unionman.shiro.constants.FileConstant;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @description 文件上传管理
 * @author Rong.Jia
 * @date 2019/02/19 15:24:22
 */
@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();

        //路径有可能限制
        String location = System.getProperty(FileConstant.USER_DIR) + FileConstant.SEPARATOR + FileConstant.FILE_DIR;
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }

        factory.setLocation(location);

        // 当上传文件达到10MB的时候进行磁盘写入
        factory.setFileSizeThreshold(DataSize.ofMegabytes(FileConstant.FILE_SIZE_THRESHOLD));

        /**
         * 设置文件大小限制
         * 大小：KB,MB
         */
        factory.setMaxFileSize(DataSize.ofMegabytes(FileConstant.FILE_SIZE));

        //设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(FileConstant.FILE_SIZE));

        return factory.createMultipartConfig();
    }

}
