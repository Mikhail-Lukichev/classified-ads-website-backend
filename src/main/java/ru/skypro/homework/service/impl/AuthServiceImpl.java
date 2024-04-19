package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AuthorMapper;
import ru.skypro.homework.security.DatabaseUserDetailsService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.AuthorService;

import java.util.Optional;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final PasswordEncoder encoder;
    private final DatabaseUserDetailsService manager;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthServiceImpl(DatabaseUserDetailsService manager,
                           PasswordEncoder passwordEncoder, AuthorService authorService, AuthorMapper authorMapper) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        logger.debug("AuthServiceImpl login()");
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        logger.debug("AuthServiceImpl register()");
        Optional<Author> author = authorService.getByEmail(registerDto.getUsername());
        if (author.isPresent()) {
            return false;
        }
        Role role = registerDto.getRole() == null ? USER : registerDto.getRole();
        registerDto.setRole(role);


        Author newAuthor = authorMapper.toAuthor(registerDto);
        authorService.add(newAuthor);

        return true;
    }

}
