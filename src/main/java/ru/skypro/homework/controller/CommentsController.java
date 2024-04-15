package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthorService;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@RestController
@RequiredArgsConstructor
public class CommentsController {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final AdService adService;
    private final AuthorService authorService;

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
    public ResponseEntity<CommentsDto> getAdComments(@PathVariable("id") int id, Authentication authentication) {
        logger.info("CommentsController getAdComments()");
        if (authentication.isAuthenticated()) {
            Optional<Ad> ad = adService.getById(id);
            if (ad.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            List<Comment> comments = commentService.getAllByAd(ad.get());
            CommentsDto result = commentMapper.toCommentsDto(comments);

            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<CommentDto> postAdComment(@PathVariable("id") int id, @RequestBody CreateOrUpdateCommentDto body, Authentication authentication) {
        logger.info("CommentsController postAdComment()");
        if (authentication.isAuthenticated()) {
            Optional<Ad> ad = adService.getById(id);
            if (ad.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            Optional<Author> author = authorService.getByEmail(authentication.getName());
            if (author.isEmpty()) throw new RuntimeException();

            Comment newComment = commentMapper.toComment(body);
            newComment.setAd(ad.get());
            newComment.setAuthor(author.get());
            newComment.setCreatedAt(System.currentTimeMillis());

            Comment createdComment = commentService.add(newComment);
            CommentDto result = commentMapper.toCommentDto(createdComment);

            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<CommentsDto> deleteAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId, Authentication authentication) {
        logger.info("CommentsController deleteAdComment()");

        if (authentication.isAuthenticated()) {
            Optional<Comment> comment = commentService.getById(commentId);
            if (comment.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            Optional<Ad> ad = adService.getById(adId);
            if (ad.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            commentService.delete(comment.get());

            List<Comment> comments = commentService.getAllByAd(ad.get());
            CommentsDto result = commentMapper.toCommentsDto(comments);

            return ResponseEntity.ok().body(result);
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId, @RequestBody CreateOrUpdateCommentDto body, Authentication authentication) {
        logger.info("CommentsController updateAdComment()");
        if (authentication.isAuthenticated()) {
            Optional<Comment> comment = commentService.getById(commentId);
            if (comment.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            Optional<Ad> ad = adService.getById(adId);
            if (ad.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            Comment updateComment = commentMapper.toComment(body);
            comment.get().setText(updateComment.getText());

            Comment result = commentService.add(comment.get());
            CommentDto resultDto = commentMapper.toCommentDto(result);

            return ResponseEntity.ok().body(resultDto);
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
