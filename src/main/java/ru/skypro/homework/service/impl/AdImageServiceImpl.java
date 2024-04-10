package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.AdImageRepository;
import ru.skypro.homework.service.AdImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AdImageServiceImpl implements AdImageService {

    @Value("${ad.adImage.dir.path}")
    private String adImagesDir;

    private final AdImageRepository adImageRepository;

    public AdImageServiceImpl(AdImageRepository adImageRepository) {
        this.adImageRepository = adImageRepository;
    }

    public AdImage upload(Ad ad, MultipartFile file) throws IOException {
        Path filePath = Path.of(adImagesDir, ad.getId() + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Optional<AdImage> adImage = adImageRepository.findByAd(ad);
        if (adImage.isEmpty()) {
            adImage = Optional.of(new AdImage());
        }
        adImage.get().setFilePath(filePath.toString());
        adImage.get().setFileSize(file.getSize());
        adImage.get().setMediaType(file.getContentType());
        adImage.get().setAd(ad);

        return adImageRepository.save(adImage.get());
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
