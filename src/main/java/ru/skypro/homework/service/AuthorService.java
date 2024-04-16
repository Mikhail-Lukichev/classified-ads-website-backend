package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author add(Author author);
    Optional<Author> getById(int id);
    Optional<Author> getByEmail(String email);
    List<String> getAuthorities(Author author);
    boolean isAdAuthor(Integer adId, Author author);
    boolean isCommentAuthor(Integer commentId, Author author);
    boolean updatePassword(Author author, NewPasswordDto passwordDto);
}
