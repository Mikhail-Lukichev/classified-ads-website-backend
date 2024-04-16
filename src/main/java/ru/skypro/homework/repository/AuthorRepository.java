package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Optional<Author> findByEmail(String email);

    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "select * from ad as ad " +
            "inner join author as author " +
            "on ad.id = :adId " +
            "and ad.author_id = author.id " +
            "and author.email = :username" +
            ")THEN TRUE ELSE FALSE END"
            , nativeQuery = true)
    boolean isAdAuthor(Integer adId, String username);

    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "select * from comment as c " +
            "inner join author as author " +
            "on c.id = :commentId " +
            "and c.author_id = author.id " +
            "and author.email = :username" +
            ")THEN TRUE ELSE FALSE END"
            , nativeQuery = true)
    boolean isCommentAuthor(Integer commentId, String username);
}
