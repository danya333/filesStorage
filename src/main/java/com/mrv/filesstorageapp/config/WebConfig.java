package com.mrv.filesstorageapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/logos/**")
                .addResourceLocations("classpath:/logos/");
        registry.addResourceHandler("/covers/**")
                .addResourceLocations("classpath:/covers/");
        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:/files/");
    }
}
