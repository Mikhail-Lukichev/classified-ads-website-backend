package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;

import java.io.IOException;
import java.util.Optional;

public interface AdImageService {
    AdImage upload(Ad ad, MultipartFile file) throws IOException;
    Optional<AdImage> getById(Integer id);
    Optional<AdImage> getByAd(Ad ad);
}
