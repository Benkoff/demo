package com.example.demo.config;

import com.example.demo.services.converters.StudentRequestToStudentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
public class AppConfig {

    @Bean
    public ConversionService conversionService() {
        final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new StudentRequestToStudentConverter());

        return conversionService;
    }
}

