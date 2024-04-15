package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar,Integer> {
    Optional<Avatar> findByAuthor(Author author);
}
