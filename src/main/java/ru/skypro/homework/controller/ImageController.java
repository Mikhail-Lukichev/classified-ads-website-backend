package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.exception.*;
import ru.skypro.homework.service.AdImageService;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthorService;
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

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final AvatarService avatarService;
    private final AdImageService adImageService;
    private final AuthorService authorService;
    private final AdService adService;

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
    @GetMapping(value = "/avatar/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getAvatarImage(@PathVariable("id") Integer id) {
        logger.debug("ImageController getAvatarImage()");
        Author author = authorService.getById(id).orElseThrow(UserNotFoundException::new);
        Avatar avatar = avatarService.getByAuthor(author).orElseThrow(AvatarNotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
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
    @GetMapping(value = "/adImage/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getAdImage(@PathVariable("id") Integer id, HttpServletResponse response) {
        logger.debug("ImageController getAvatarImage()");
        Ad ad = adService.getById(id).orElseThrow(AdNotFoundException::new);
        AdImage adImage = adImageService.getByAd(ad).orElseThrow(AdImageNotFoundException::new);
        Path path = Path.of(adImage.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();
        ) {
            response.setContentType(adImage.getMediaType());
            response.setContentLength(adImage.getFileSize().intValue());
            is.transferTo(os);
        } catch (IOException e) {
            throw new InvalidImageStreamException();
        }
        return ResponseEntity.ok().build();
    }
}
