package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final AuthorService authorService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("DatabaseUserDetailsService loadUserByUsername()");

        Author author = authorService.getByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username + " not found")
        );

        List<SimpleGrantedAuthority> grantedAuthorities = authorService.getAuthorities(author).stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(author.getEmail(), author.getPassword(), grantedAuthorities);
    }
}
