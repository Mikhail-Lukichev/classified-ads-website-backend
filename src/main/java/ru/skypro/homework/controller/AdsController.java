package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@RestController
@RequiredArgsConstructor
public class AdsController {

    private final AdServiceImpl adService;
    private final AuthorServiceImpl authorService;
    private final AdMapper adMapper;

    @Operation(summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    )
            }, tags = "Объявления")
    @GetMapping()
    public ResponseEntity<AdsDto> getAds() {
        List<Ad> ads = adService.getAll();

        AdsDto result = new AdsDto();
        result.setCount(ads.size());
        result.setResults(ads.stream().map(a -> adMapper.toAdDto(a)).collect(Collectors.toList()));

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> postAd(@RequestPart CreateOrUpdateAdDto properties, @RequestPart MultipartFile image) {
//    public ResponseEntity<AdDto> postAd(@RequestBody CreateOrUpdateAdDto properties, @RequestParam MultipartFile image) {
//    public ResponseEntity<AdDto> postAd(@ModelAttribute("properties") CreateOrUpdateAdDto properties, @RequestParam MultipartFile image) {
//    public ResponseEntity<AdDto> postAd(@ModelAttribute tempDto properties) {
        if (true) {
//            System.out.println(image.getSize());
            Ad ad = adMapper.toAd(properties);

            Author author = new Author();
            author.setId(1);

            ad.setAuthor(author);

//            AdDto result = adMapper.toAdDto(adService.add(ad));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            adMapper.toAdDto(adService.add(ad)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtendedAdDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExtendedAdDto> getAd(@PathVariable("id") Integer id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Optional<Ad> ad = adService.getById(id);
            if (ad.isPresent()) {
                Author author = ad.get().getAuthor();
                AdImage adImage = ad.get().getAdImage();
                ExtendedAdDto result = adMapper.toExtendedAdDto(ad.get(), author, adImage);
                return ResponseEntity.status(HttpStatus.OK).body(adMapper.toExtendedAdDto(ad.get(), author, adImage));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ExtendedAdDto> deleteAd(@PathVariable("id") Integer id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Optional<Ad> ad = adService.getById(id);
            if (ad.isPresent()) {
                adService.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ExtendedAdDto> updateAd(@PathVariable("id") Integer id, @RequestPart(required = true) CreateOrUpdateAdDto properties, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @GetMapping(value = "/me")
    public ResponseEntity<AdsDto> getAuthenticatedUserAds(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String email = authentication.getName();
            Author author = authorService.getByEmail(email).get();
            List<Ad> ads = adService.getByAuthor(author);
            return ResponseEntity.ok().body(adMapper.toAdsDto(ads));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String[]> updateAdImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image) {
        if (true) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
