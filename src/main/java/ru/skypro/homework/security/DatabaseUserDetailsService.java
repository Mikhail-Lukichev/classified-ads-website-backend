package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final AuthorService authorService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Author author = authorService.getByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username + " not found")
        );

        List<SimpleGrantedAuthority> grantedAuthorities = authorService.getAuthorities(author).stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(author.getEmail(), author.getPassword(), grantedAuthorities);
    }
}
