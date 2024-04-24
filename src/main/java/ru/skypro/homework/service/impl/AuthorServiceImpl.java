package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.AuthorRepository;
import ru.skypro.homework.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author add(Author author) {
        logger.debug("AuthorServiceImpl add()");
        return authorRepository.save(author);
    }

    public List<String> getAuthorities(Author author) {
        logger.debug("AuthorServiceImpl getAuthorities()");
        return List.of(author.getRole().name());
    }

    public Optional<Author> getById(int id) {
        logger.debug("AuthorServiceImpl getById()");
        return authorRepository.findById(id);
    }

    public Optional<Author> getByEmail(String email) {
        logger.debug("AuthorServiceImpl getByEmail()");
        return authorRepository.findByEmail(email);
    }

    public boolean isAdAuthor(Integer adId, Author author) {
        logger.debug("AuthorServiceImpl isAdAuthor()");
        return authorRepository.isAdAuthor(adId,author.getEmail());
    }

    public boolean isCommentAuthor(Integer commentId, Author author) {
        logger.debug("AuthorServiceImpl isCommentAuthor()");
        return authorRepository.isCommentAuthor(commentId, author.getEmail());
    }
}
