package io.euntak.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "io.euntak")
@Import({DbConfig.class, FilesConfig.class}) // DBConfig 를 설정한다.
public class RootApplicationContextConfig {

}
