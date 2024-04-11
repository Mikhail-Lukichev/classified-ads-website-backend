package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private Long fileSize;
    private String mediaType;

    @OneToOne
    @JoinColumn(name="ad_id", referencedColumnName = "id")
    private Ad ad;

}
