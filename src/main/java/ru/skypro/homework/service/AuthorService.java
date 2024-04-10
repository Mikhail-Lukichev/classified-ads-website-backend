package ru.skypro.homework.service;

import ru.skypro.homework.entity.Author;

import java.util.Optional;

public interface AuthorService {
    Author add(Author author);

    Optional<Author> getById(int id);
    Optional<Author> getByEmail(String email);
}
