package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Author;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Author,Integer> {
    Optional<Author> findByEmail(String email);
}
