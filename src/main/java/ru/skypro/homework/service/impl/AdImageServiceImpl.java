package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.repository.AdImageRepository;
import ru.skypro.homework.service.AdImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
public class AdImageServiceImpl implements AdImageService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Value("${ad.adImage.dir.path}")
    private String adImagesDir;

    private final AdImageRepository adImageRepository;

    public AdImageServiceImpl(AdImageRepository adImageRepository) {
        this.adImageRepository = adImageRepository;
    }

    public AdImage upload(Ad ad, MultipartFile file) throws IOException {
        logger.debug("AdImageServiceImpl upload()");
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

        AdImage adImage = new AdImage();
        adImage.setFilePath(filePath.toString());
        adImage.setFileSize(file.getSize());
        adImage.setMediaType(file.getContentType());
        adImage.setAd(ad);

        Optional<AdImage> foundAdImage = adImageRepository.findByAd(ad);
        foundAdImage.ifPresent(image -> adImage.setId(image.getId()));

        return adImageRepository.save(adImage);
    }

    private String getExtension(String fileName) {
        logger.debug("AdImageServiceImpl getExtension()");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Optional<AdImage> getById(Integer id) {
        logger.debug("AdImageServiceImpl getById()");
        return adImageRepository.findById(id);
    }

    public Optional<AdImage> getByAd(Ad ad) {
        logger.debug("AdImageServiceImpl getByAd()");
        return adImageRepository.findByAd(ad);
    }
}
