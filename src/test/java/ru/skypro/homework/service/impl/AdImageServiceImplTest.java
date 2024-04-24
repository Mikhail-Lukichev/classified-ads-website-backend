package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.repository.AdImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdImageServiceImplTest {

    private String adImagesDir = "AdImages";

    @Mock
    private MultipartFile mockedFile;

    @Mock
    private AdImageRepository adImageRepository;

    @InjectMocks
    private AdImageServiceImpl out;

    @Test
    void getByIdTest() {
        Optional<AdImage> expected = Optional.of(TestUtils.getAdImage());
        when(adImageRepository.findById(anyInt())).thenReturn(expected);
        Optional<AdImage> actual = out.getById(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByAdTest() {
        Optional<AdImage> expected = Optional.of(TestUtils.getAdImage());
        Ad ad = TestUtils.getAd1();
        when(adImageRepository.findByAd(any())).thenReturn(expected);
        Optional<AdImage> actual = out.getByAd(ad);
        Assertions.assertEquals(expected, actual);
    }
}