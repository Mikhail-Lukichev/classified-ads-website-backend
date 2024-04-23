package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;

import java.io.*;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
public class AvatarServiceImpl implements AvatarService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar upload(Author author, MultipartFile file) throws IOException {
        logger.debug("AvatarServiceImpl upload()");
        Avatar avatar = new Avatar();
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setAuthor(author);
        avatar.setData(file.getBytes());

        Optional<Avatar> foundAvatar = avatarRepository.findByAuthor(author);
        foundAvatar.ifPresent(a -> avatar.setId(a.getId()));

        return avatarRepository.save(avatar);
    }

    public Optional<Avatar> getById(Integer id) {
        logger.debug("AvatarServiceImpl getById()");
        return avatarRepository.findById(id);
    }

    public Optional<Avatar> getByAuthor(Author author) {
        logger.debug("AvatarServiceImpl getByAuthor()");
        return avatarRepository.findByAuthor(author);
    }
}
