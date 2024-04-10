package ru.skypro.homework.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class Avatar {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    private Long fileSize;
    @Getter
    private String mediaType;

    @Getter
    @Column(name = "data", columnDefinition="bytea")
    private byte[] data;

    @OneToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private Author author;

    public Avatar() {
    }

}
