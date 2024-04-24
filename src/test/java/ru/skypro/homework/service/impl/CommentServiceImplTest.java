package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl out;

    @Test
    void addTest() {
        Comment expected = TestUtils.getComment1();
        when(commentRepository.save(any())).thenReturn(expected);
        Comment actual = out.add(expected);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getAllByAdTest() {
        Ad ad = TestUtils.getAd1();
        Comment comment = TestUtils.getComment1();
        List<Comment> expected = List.of(comment);
        when(commentRepository.findByAd(any())).thenReturn(expected);
        List<Comment> actual = out.getAllByAd(ad);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void deleteTest() {
        Comment comment = TestUtils.getComment1();
        out.delete(comment);
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void getByIdTest() {
        Optional<Comment> expected = Optional.of(TestUtils.getComment1());
        when(commentRepository.findById(anyInt())).thenReturn(expected);
        Optional<Comment> actual = out.getById(1);
        Assertions.assertEquals(expected,actual);
    }
}