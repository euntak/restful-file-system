package io.euntak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;

@Configuration
@PropertySource("classpath:/files.properties")
public class FilesConfig {

    @Value("${spring.uploadfile.max-size}")
    private long uploadMaxFileSize;

    @Value("${spring.uploadfile.root-directory}")
    private String baseDiretory;


    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(uploadMaxFileSize);
        return multipartResolver;
    }

    @Bean
    public String getBaseDireroty() {
        return baseDiretory;
    }
}
