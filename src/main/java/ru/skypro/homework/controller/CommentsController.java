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
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@RestController
@RequiredArgsConstructor
public class CommentsController {

    @Operation(summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Комментарии")
    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<CommentsDto> getAdComments(@PathVariable("id") int id) {
        if (true) {
            return ResponseEntity.ok().build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    )
            }, tags = "Комментарии")
    @PostMapping(value = "/{id}/comments")
    public ResponseEntity<CommentsDto> postAdComment(@PathVariable("id") int id, @RequestBody CreateOrUpdateCommentDto body) {
        if (true) {
            return ResponseEntity.ok().build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
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
            }, tags = "Комментарии")
    @DeleteMapping(value = "/{adId}/comments/{commentId}")
    public ResponseEntity<CommentsDto> deleteAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId) {
        if (true) {
            return ResponseEntity.ok().build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CommentDto.class))
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
            }, tags = "Комментарии")
    @PatchMapping(value = "/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId, @RequestBody CreateOrUpdateCommentDto body) {
        if (true) {
            return ResponseEntity.ok().build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
