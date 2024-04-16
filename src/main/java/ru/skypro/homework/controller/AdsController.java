package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.exception.AdNotFoundException;
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

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

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
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Объявления")
    @GetMapping()
    public ResponseEntity<AdsDto> getAds() {
        logger.info("AdsController getAds()");
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
    public ResponseEntity<AdDto> postAd(@RequestPart CreateOrUpdateAdDto properties, @RequestPart MultipartFile image, Authentication authentication) {
        logger.info("AdsController postAd()");
        Author author = authorService.getByEmail(authentication.getName()).get();
        Ad ad = adMapper.toAd(properties);
        ad.setAuthor(author);
        Ad createdAd = adService.add(ad);
        try {
            AdImage adImage = adImageService.upload(createdAd, image);
            createdAd.setAdImage(adImage);
        } catch (IOException e) {
            System.out.println("Cannot upload ad image.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        AdDto result = adMapper.toAdDto(createdAd);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
        logger.info("AdsController getAd()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        Author author = ad.getAuthor();
        AdImage adImage = ad.getAdImage();
        ExtendedAdDto result = adMapper.toExtendedAdDto(ad);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
    @PreAuthorize("@userSecurity.isAdAuthor(#id) or hasAuthority('ADMIN')")
    public ResponseEntity<ExtendedAdDto> deleteAd(@PathVariable("id") Integer id, Authentication authentication) {
        logger.info("AdsController deleteAd()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        adService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    @PreAuthorize("@userSecurity.isAdAuthor(#id) or hasAuthority('ADMIN')")
    public ResponseEntity<ExtendedAdDto> updateAd(@PathVariable("id") Integer id, @RequestBody(required = true) CreateOrUpdateAdDto properties, Authentication authentication) {
        logger.info("AdsController updateAd()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        Ad updateAd = adService.mergeCreateOrUpdateAdDto(properties, ad);
        adService.updateAd(updateAd);
        ExtendedAdDto result = adMapper.toExtendedAdDto(updateAd);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
        logger.info("AdsController getAuthenticatedUserAds()");
        String email = authentication.getName();
        Author author = authorService.getByEmail(email).get();
        List<Ad> ads = adService.getByAuthor(author);
        return ResponseEntity.ok().body(adMapper.toAdsDto(ads));
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
    @PreAuthorize("@userSecurity.isAdAuthor(#id) or hasAuthority('ADMIN')")
    public ResponseEntity<String[]> updateAdImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image, Authentication authentication) {
        logger.info("AdsController updateAdImage()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        AdImage adImage = new AdImage();
        try {
            adImage = adImageService.upload(ad, image);
        } catch (IOException e) {
            System.out.println("Cannot upload ad image.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new String[]{adImage.toString()});
    }
}
