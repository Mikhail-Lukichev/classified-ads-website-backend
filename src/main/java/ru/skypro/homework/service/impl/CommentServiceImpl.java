package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment add(Comment comment) {
        logger.debug("CommentServiceImpl add()");
        return commentRepository.save(comment);
    }

    public List<Comment> getAllByAd(Ad ad) {
        logger.debug("CommentServiceImpl getAllByAd()");
        return commentRepository.findByAd(ad);
    }

    public void delete(Comment comment) {
        logger.debug("CommentServiceImpl delete()");
        commentRepository.delete(comment);
    }

    public Optional<Comment> getById(Integer id) {
        logger.debug("CommentServiceImpl getById()");
        return commentRepository.findById(id);
    }
}
