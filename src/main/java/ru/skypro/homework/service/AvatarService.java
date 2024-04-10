package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;

import java.io.IOException;

public interface AvatarService {
    Avatar upload(Author author, MultipartFile file) throws IOException;
}
