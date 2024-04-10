package ru.skypro.homework.service;

import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AdService {
    Ad add(Ad ad);
    List<Ad> getAll();
    Optional<Ad> getById(Integer id);
    List<Ad> getByAuthor(Author author);
    void deleteById(Integer id);
    Ad updateAd(Ad ad);
}
