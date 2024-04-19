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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
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
        logger.debug("CommentsController getAdComments()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        List<Comment> comments = commentService.getAllByAd(ad);
        CommentsDto result = commentMapper.toCommentsDto(comments);
        return ResponseEntity.ok().body(result);
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
        logger.debug("CommentsController postAdComment()");
        Ad ad = adService.getById(id).orElseThrow(() -> new AdNotFoundException());
        Author author = authorService.getByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException());
        Comment newComment = commentMapper.toComment(body);
        newComment.setAd(ad);
        newComment.setAuthor(author);
        newComment.setCreatedAt(System.currentTimeMillis());
        Comment createdComment = commentService.add(newComment);
        CommentDto result = commentMapper.toCommentDto(createdComment);
        return ResponseEntity.ok().body(result);
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
    @PreAuthorize("@userSecurity.isCommentAuthor(#commentId) or hasAuthority('ADMIN')")
    public ResponseEntity<CommentsDto> deleteAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId, Authentication authentication) {
        logger.debug("CommentsController deleteAdComment()");
        Ad ad = adService.getById(adId).orElseThrow(() -> new AdNotFoundException());
        Comment comment = commentService.getById(commentId).orElseThrow(() -> new CommentNotFoundException());
        commentService.delete(comment);
        List<Comment> comments = commentService.getAllByAd(ad);
        CommentsDto result = commentMapper.toCommentsDto(comments);
        return ResponseEntity.ok().body(result);
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
    @PreAuthorize("@userSecurity.isCommentAuthor(#commentId) or hasAuthority('ADMIN')")
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("adId") int adId, @PathVariable("commentId") int commentId, @RequestBody CreateOrUpdateCommentDto body, Authentication authentication) {
        logger.debug("CommentsController updateAdComment()");
        Ad ad = adService.getById(adId).orElseThrow(() -> new AdNotFoundException());
        Comment comment = commentService.getById(commentId).orElseThrow(() -> new CommentNotFoundException());
        Comment updateComment = commentMapper.toComment(body);
        comment.setText(updateComment.getText());
        Comment result = commentService.add(comment);
        CommentDto resultDto = commentMapper.toCommentDto(result);
        return ResponseEntity.ok().body(resultDto);
    }
}
