package ru.dictionary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.dictionary.dao.*;

@Configuration
@ComponentScan(basePackages = {"ru.dictionary"})
@PropertySource("classpath:application.properties")
public class AnnotationConfig {

    @Bean
    @Value("${row.path}")
    InterfaceRowDAO rowDAO(String path) {
        return new RowDAO(path);
    }

    @Bean
    @Value("${word.path}")
    InterfaceWordDAO wordDAO(String path) {
        return new WordDAO(path);
    }

    @Bean
    @Value("${language.path}")
    InterfaceLanguageDAO languageDAO(String path) {
        return new LanguageDAO(path);
    }
}
