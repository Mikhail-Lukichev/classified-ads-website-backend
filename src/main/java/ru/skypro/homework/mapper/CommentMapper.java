package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    private final AuthorService authorService;
    private final AdService adService;

    public CommentMapper(AuthorService authorService, AdService adService) {
        this.authorService = authorService;
        this.adService = adService;
    }

    public Comment toComment(CreateOrUpdateCommentDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        return comment;
    }

    public CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setAuthor(comment.getAuthor().getId());
        dto.setAuthorImage("/image/avatar/" + comment.getAuthor().getId());
        dto.setAuthorFirstName(comment.getAuthor().getFirstName());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setPk(comment.getId());
        dto.setText(comment.getText());
        return dto;
    }

    public CommentsDto toCommentsDto(List<Comment> comments) {
        CommentsDto dto = new CommentsDto();
        dto.setCount(comments.size());
        dto.setResults(
                comments.stream()
                        .map(this::toCommentDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
