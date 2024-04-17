package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;

import java.io.*;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar upload(Author author, MultipartFile file) throws IOException {
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
        return avatarRepository.findById(id);
    }

    public Optional<Avatar> getByAuthor(Author author) {
        return avatarRepository.findByAuthor(author);
    }
}
