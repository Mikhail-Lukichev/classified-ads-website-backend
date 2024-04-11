package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;

import java.io.IOException;
import java.util.Optional;

public interface AvatarService {
    Avatar upload(Author author, MultipartFile file) throws IOException;

    Optional<Avatar> getById(Integer id);
}
