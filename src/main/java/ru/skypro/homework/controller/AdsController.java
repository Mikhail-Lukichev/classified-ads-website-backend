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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.impl.AdImageServiceImpl;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.AuthorServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private final AdImageServiceImpl adImageService;
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
        String baseUrl = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();

        List<Ad> ads = adService.getAll();

        AdsDto result = new AdsDto();
        result.setCount(ads.size());
        result.setResults(ads.stream().map(a -> adMapper.toAdDto(baseUrl, a)).collect(Collectors.toList()));

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
    public ResponseEntity<AdDto> postAd(@RequestPart CreateOrUpdateAdDto properties, @RequestPart MultipartFile image, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();

            Author author = authorService.getByEmail(authentication.getName()).get();

            Ad ad = adMapper.toAd(properties);
            ad.setAuthor(author);
            Ad createdAd = adService.add(ad);

            try {
                AdImage adImage = adImageService.upload(createdAd, image);
                createdAd.setAdImage(adImage);
            } catch (IOException e) {
                System.out.println(e.fillInStackTrace());
            }

            AdDto result = adMapper.toAdDto(baseUrl, createdAd);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
                ExtendedAdDto result = adMapper.toExtendedAdDto(ad.get());
                return ResponseEntity.status(HttpStatus.OK).body(result);
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
    public ResponseEntity<ExtendedAdDto> updateAd(@PathVariable("id") Integer id, @RequestBody(required = true) CreateOrUpdateAdDto properties, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            if (adService.getById(id).isPresent()) {
                Ad foundAd = adService.getById(id).get();

                Ad updateAd = adMapper.toAd(properties);
                updateAd.setId(id);
                updateAd.setComments(foundAd.getComments());
                updateAd.setAuthor(foundAd.getAuthor());
                updateAd.setAdImage(foundAd.getAdImage());

                adService.updateAd(updateAd);

                ExtendedAdDto result = adMapper.toExtendedAdDto(updateAd);

                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
            String baseUrl = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
            String email = authentication.getName();
            Author author = authorService.getByEmail(email).get();
            List<Ad> ads = adService.getByAuthor(author);
            return ResponseEntity.ok().body(adMapper.toAdsDto(baseUrl, ads));
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
    public ResponseEntity<String[]> updateAdImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            if (adService.getById(id).isPresent()) {
                Ad foundAd = adService.getById(id).get();
                AdImage adImage = new AdImage();
                try {
                    adImage = adImageService.upload(foundAd, image);
                } catch (IOException e) {
                    System.out.println(e.fillInStackTrace());
                }
                return ResponseEntity.status(HttpStatus.OK).body(new String[]{adImage.toString()});
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
