package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.service.AdImageService;
import ru.skypro.homework.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("image")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final AvatarService avatarService;
    private final AdImageService adImageService;

    @Operation(summary = "Получение аватара",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtendedAdDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Изображения")
    @GetMapping(value = "avatar/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getAvatarImage(@PathVariable("id") Integer id) {
        Optional<Avatar> avatar = avatarService.getById(id);
        if (avatar.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.get().getMediaType()));
            headers.setContentLength(avatar.get().getFileSize());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.get().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Получение фотографии объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtendedAdDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Изображения")
    @GetMapping(value = "adImage/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getAdImage(@PathVariable("id") Integer id, HttpServletResponse response) {
        Optional<AdImage> adImage = adImageService.getById(id);
        if (adImage.isPresent()) {
            Path path = Path.of(adImage.get().getFilePath());

            try (InputStream is = Files.newInputStream(path);
                 OutputStream os = response.getOutputStream();
            ) {
                response.setContentType(adImage.get().getMediaType());
                response.setContentLength(adImage.get().getFileSize().intValue());
                is.transferTo(os);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
