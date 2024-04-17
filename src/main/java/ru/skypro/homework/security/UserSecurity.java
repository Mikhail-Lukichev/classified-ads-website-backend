package ru.skypro.homework.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.service.AuthorService;

@Component
public class UserSecurity {

    private final AuthorService authorService;

    public UserSecurity(AuthorService authorService) {
        this.authorService = authorService;
    }

    public boolean isAdAuthor(Integer adId) {
        Author author = authorService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        return authorService.isAdAuthor(adId, author);
    }

    public boolean isCommentAuthor(Integer commentId) {
        Author author = authorService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        return authorService.isCommentAuthor(commentId, author);
    }
}