package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema
public class tempDto {
    private CreateOrUpdateAdDto properties;
    private MultipartFile image;
}
