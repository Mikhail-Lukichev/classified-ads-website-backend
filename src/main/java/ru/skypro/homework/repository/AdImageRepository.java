package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AdImageRepository extends JpaRepository<AdImage,Integer> {
    Optional<AdImage> findByAd(Ad ad);
}
