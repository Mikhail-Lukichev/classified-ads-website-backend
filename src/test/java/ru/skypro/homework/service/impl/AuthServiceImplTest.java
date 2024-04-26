package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AuthorMapper;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.security.DatabaseUserDetailsService;
import ru.skypro.homework.service.AuthorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private DatabaseUserDetailsService manager;

    @Mock
    private AuthorService authorService;
    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthServiceImpl out;

    @Test
    void loginTest() {
//        Author author = TestUtils.getAuthor1();
//        String username = author.getEmail();
//        String password = author.getPassword();
//        CustomUserDetails customUserDetails = new CustomUserDetails();
//        customUserDetails.setUser(author);
//        boolean expected = encoder.matches(password, customUserDetails.getPassword());
//
//        when(manager.loadUserByUsername(anyString())).thenReturn(customUserDetails);
//        boolean actual = out.login(username,password);
//        assertEquals(expected,actual);
    }

    @Test
    void register() {
    }

    @Test
    void updatePassword() {
    }
}