package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class AdsDto {

    @Schema(description = "общее количество объявлений")
    private Integer count;

    List<AdDto> results;

}
