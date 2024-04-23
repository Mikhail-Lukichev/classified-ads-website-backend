package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthorService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void toCommentTest() {
        CreateOrUpdateCommentDto dto = TestUtils.getCreateOrUpdateCommentDto1();
        Comment expected = new Comment();
        expected.setText(dto.getText());
        Comment actual = commentMapper.toComment(dto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCommentDto() {
        Comment comment = TestUtils.getComment1();
        CommentDto expected = TestUtils.getCommentDto1();
        CommentDto actual = commentMapper.toCommentDto(comment);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toCommentsDto() {
        Comment comment = TestUtils.getComment1();
        List<Comment> comments = List.of(comment);

        CommentsDto expected = new CommentsDto();
        CommentDto dto = TestUtils.getCommentDto1();
        expected.setCount(1);
        expected.setResults(List.of(dto));

        CommentsDto actual = commentMapper.toCommentsDto(comments);
        Assertions.assertEquals(expected, actual);
    }
}