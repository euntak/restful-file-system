package io.euntak.config;

import io.euntak.resolver.CustomMultipartResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;

@Configuration
@PropertySource ("classpath:/files.properties")
public class FilesConfig {

    @Value ("${spring.uploadfile.max-size}")
    private long uploadMaxFileSize;

    @Value ("${spring.uploadfile.root-directory}")
    private String baseDiretory;


    @Bean
    public MultipartResolver multipartResolver() {

        CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
        multipartResolver.setMaxUploadSize(uploadMaxFileSize);
        // Whether to resolve the multipart request lazily at the time of file or parameter access.
        multipartResolver.setResolveLazily(false);
        return multipartResolver;
    }


    @Bean
    public String getBaseDireroty() {

        return baseDiretory;
    }
}
