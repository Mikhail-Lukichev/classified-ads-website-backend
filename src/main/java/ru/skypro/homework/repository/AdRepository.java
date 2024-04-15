package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AdRepository  extends JpaRepository<Ad,Integer> {
    List<Ad> findByAuthor(Author author);
}
