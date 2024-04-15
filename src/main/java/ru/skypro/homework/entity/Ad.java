package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @OneToMany(mappedBy = "ad")
    private Set<Comment> comments;

    @OneToOne(mappedBy = "ad")
    private AdImage adImage;

}
