package ru.skypro.homework.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.service.AuthorService;

@Slf4j
@Component
public class UserSecurity {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final AuthorService authorService;

    public UserSecurity(AuthorService authorService) {
        this.authorService = authorService;
    }

    public boolean isAdAuthor(Integer adId) {
        logger.debug("UserSecurity isAdAuthor()");
        Author author = authorService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        return authorService.isAdAuthor(adId, author);
    }

    public boolean isCommentAuthor(Integer commentId) {
        logger.debug("UserSecurity isCommentAuthor()");
        Author author = authorService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        return authorService.isCommentAuthor(commentId, author);
    }
}