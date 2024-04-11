package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AuthorMapper;
import ru.skypro.homework.service.impl.AuthorServiceImpl;
import ru.skypro.homework.service.impl.AvatarServiceImpl;

import java.io.IOException;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthorMapper authorMapper;
    private final AuthorServiceImpl authorService;
    private final AvatarServiceImpl avatarService;

    @Operation(summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Пользователи")
    @PostMapping("/set_password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPasswordDto updatePassword) {
        if (true) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Пользователи")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthenticatedUserInfo(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Author author = authorService.getByEmail(authentication.getName()).orElseThrow();
            UserDto result = authorMapper.toUserDto(author);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateUserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Пользователи")
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateAuthenticatedUser(@RequestBody UpdateUserDto updateUserDto, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Author foundAuthor = authorService.getByEmail(authentication.getName()).orElseThrow();
            Author updateAuthor = authorMapper.toAuthor(updateUserDto);

            foundAuthor.setFirstName(updateAuthor.getFirstName());
            foundAuthor.setLastName(updateAuthor.getLastName());
            foundAuthor.setPhone(updateAuthor.getPhone());

            UpdateUserDto result = authorMapper.toUpdateUserDto(authorService.add(foundAuthor));

            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Пользователи")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAuthenticatedUserImage(@RequestParam MultipartFile image, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Author author = authorService.getByEmail(authentication.getName()).orElseThrow();
            try {
                avatarService.upload(author, image);
            } catch (IOException e) {
                System.out.println(e.fillInStackTrace());
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
