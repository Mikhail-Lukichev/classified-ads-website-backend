package ru.skypro.homework.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private  AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl out;

    @Test
    void addTest() {
        Author author = TestUtils.getAuthor1();
        Author expected = TestUtils.getAuthor1();
        when(authorRepository.save(any())).thenReturn(expected);
        Author actual = out.add(author);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAuthoritiesTest() {
        Author author = TestUtils.getAuthor1();
        List<String> expected = List.of(author.getRole().name());
        List<String> actual = out.getAuthorities(author);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getByIdTest() {
        Author expected = TestUtils.getAuthor1();
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(expected));
        Optional<Author> actual = out.getById(1);
        Assertions.assertEquals(Optional.of(expected),actual);
    }

    @Test
    void getByEmailTest() {
        Author expected = TestUtils.getAuthor1();
        when(authorRepository.findByEmail(anyString())).thenReturn(Optional.of(expected));
        Optional<Author> actual = out.getByEmail("email");
        Assertions.assertEquals(Optional.of(expected),actual);
    }

    @Test
    void isAdAuthorTest() {
        Author author = TestUtils.getAuthor1();
        Ad ad = TestUtils.getAd1();
        when(authorRepository.isAdAuthor(anyInt(),anyString())).thenReturn(true);
        boolean expected = true;
        boolean actual = out.isAdAuthor(ad.getId(),author);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void isCommentAuthorTest() {
        Comment comment = TestUtils.getComment1();
        Author author = TestUtils.getAuthor1();
        when(authorRepository.isCommentAuthor(anyInt(),anyString())).thenReturn(true);
        boolean expected = true;
        boolean actual = out.isCommentAuthor(comment.getId(),author);
        Assertions.assertEquals(expected, actual);
    }
}