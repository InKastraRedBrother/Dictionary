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
}
