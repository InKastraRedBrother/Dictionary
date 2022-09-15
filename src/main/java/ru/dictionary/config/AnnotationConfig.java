package ru.dictionary.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;

@ComponentScan(basePackages = {"ru.dictionary"})
@Configuration
@PropertySource("classpath:application.properties")
public class AnnotationConfig {

}
