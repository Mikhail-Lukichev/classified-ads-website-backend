package ru.skypro.homework.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.AuthorRepository;
import ru.skypro.homework.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final PasswordEncoder encoder;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(PasswordEncoder encoder, AuthorRepository authorRepository) {
        this.encoder = encoder;
        this.authorRepository = authorRepository;
    }

    public Author add(Author author) {
        author.setPassword(encoder.encode(author.getPassword()));
        return authorRepository.save(author);
    }

    public List<String> getAuthorities(Author author) {
        return List.of(author.getRole().name());
    }

    public Optional<Author> getById(int id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> getByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public boolean isAdAuthor(Integer adId, Author author) {
        return authorRepository.isAdAuthor(adId,author.getEmail());
    }

    public boolean isCommentAuthor(Integer commentId, Author author) {
        return authorRepository.isCommentAuthor(commentId, author.getEmail());
    }

    public boolean updatePassword(Author author, NewPasswordDto passwordDto) {
        if (encoder.matches(passwordDto.getCurrentPassword(), author.getPassword())) {
            author.setPassword(encoder.encode(passwordDto.getNewPassword()));
            authorRepository.save(author);
            return true;
        }
        return false;
    }
}
