package ru.dictionary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.dictionary.dao.Dao;

@ComponentScan(basePackages = {"ru.dictionary"})
@Configuration
@PropertySource(value = "classpath:application.properties")
public class AnnotationConfig {
//
//    @Bean
//    @Value("${dictionary.storage.path}")
//    public Dao getPathToDictionary(){
//        return new Dao();
//    }
}
