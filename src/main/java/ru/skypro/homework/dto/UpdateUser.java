package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateUser {

    @Schema(minLength = 2, maxLength = 16, description = "имя пользователя")
    private String firstName;

    @Schema(minLength = 2, maxLength = 16, description = "фамилия пользователя")
    private String lastName;

    @Schema(pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", description = "телефон пользователя")
    private String phone;
}
