package ru.skypro.homework.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name="ad_id")
    private Ad ad;

    private Long createdAt;

    private String text;

}
