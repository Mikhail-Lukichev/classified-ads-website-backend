package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    @OneToMany(mappedBy = "author")
    private Set<Ad> ads;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @OneToOne(mappedBy = "author")
    private Avatar avatar;

}
