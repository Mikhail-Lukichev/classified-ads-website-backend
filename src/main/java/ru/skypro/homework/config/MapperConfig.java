package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.AuthorMapper;

@Configuration
public class MapperConfig {
    @Bean
    public AuthorMapper AuthorMapperBean() {
        return new AuthorMapper();
    }

    @Bean
    public AdMapper AdMapperBean() {
        return new AdMapper();
    }
}
