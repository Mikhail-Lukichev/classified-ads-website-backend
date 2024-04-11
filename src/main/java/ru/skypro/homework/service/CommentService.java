package ru.skypro.homework.service;

import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment add(Comment comment);
    List<Comment> getAllByAd(Ad ad);
    void delete(Comment comment);
    Optional<Comment> getById(Integer id);
}
