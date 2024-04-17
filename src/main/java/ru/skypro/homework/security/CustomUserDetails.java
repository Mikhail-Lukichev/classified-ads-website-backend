package ru.skypro.homework.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Author;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetails implements UserDetails {
    private Author author;

    public Author getUser() {
        return author;
    }

    public void setUser(Author author) {
        this.author = author;
    }

    @Override
    public String getUsername() {
        return author.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(author.getRole().name()));
    }

    @Override
    public String getPassword() {
        return author.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
