package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
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

    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }

    @Test
    void postAdTest() {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("testUsername@mail.com", "password", StandardCharsets.UTF_8))
                        .content(adJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test Title1"));

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
    void updateAdImage() {
    }
}