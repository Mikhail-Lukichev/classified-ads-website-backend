package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.AdImageRepository;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.AuthorRepository;
import ru.skypro.homework.service.impl.AdImageServiceImpl;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.AuthorServiceImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdImageRepository adImageRepository;

    @InjectMocks
    private AdsController adsController;

    private Integer savedAdId;

    @BeforeEach
    public void setup() {
        Author author = TestUtils.getAuthor1();
        author.setId(null);
        author.setPassword(encoder.encode(author.getPassword()));
        authorRepository.save(author);

        Ad ad = TestUtils.getAd1();
        ad.setId(null);
        ad.setAuthor(author);
        Ad savedAd = adRepository.save(ad);
        savedAdId = savedAd.getId();

        AdImage adImage = TestUtils.getAdImage();
        adImage.setId(null);
        adImage.setAd(savedAd);
        adImageRepository.save(adImage);
    }

    @Test
    void getAdsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAdTest() throws Exception {
        Ad ad = adRepository.findAll().stream().findAny().get();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ad.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("email1", "password", StandardCharsets.UTF_8))
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void postAdTest() throws Exception {

        MockMultipartFile image = new MockMultipartFile("image", "", "image/png", "{\"image\": \"test\\computer.png\"}".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile("properties", "", "application/json", "{\"title\": \"string\",\"price\": 111,\"description\": \"stringst\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/ads")
                        .file(jsonFile)
                        .file(image)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("email1", "password", StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(201));
    }

    @Test
    void deleteAdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/" + savedAdId)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("email1", "password", StandardCharsets.UTF_8))
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    @Disabled
    void updateAd() {
    }

    @Test
    void getAuthenticatedUserAdsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("email1", "password", StandardCharsets.UTF_8))
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    @Disabled
    void updateAdImageTest() throws Exception {

        MockMultipartFile image = new MockMultipartFile("image", "", "image/png", "{\"image\": \"test\\computer.png\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH,"/ads/" + savedAdId + "/image")
                        .file(image)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("email1", "password", StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200));
    }
}