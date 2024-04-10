package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthorService;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final UserRepository userRepository;

    public AuthorServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Author add(Author author) {
        return userRepository.save(author);
    }

    public Optional<Author> getById(int id) {
        return userRepository.findById(id);
    }

    public Optional<Author> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
