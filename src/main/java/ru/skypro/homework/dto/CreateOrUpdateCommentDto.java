package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class CreateOrUpdateCommentDto {

    @Schema(minLength = 8, maxLength = 64, description = "текст комментария", required = true)
    private String text;
}
