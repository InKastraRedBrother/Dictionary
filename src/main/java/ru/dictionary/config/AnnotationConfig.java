package ru.dictionary.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = {"ru.dictionary"})
@Configuration
@PropertySource(value = "classpath:application.properties")
public class AnnotationConfig {
}
