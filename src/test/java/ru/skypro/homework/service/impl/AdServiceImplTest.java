package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdMapper adMapper;

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdServiceImpl out;

    @Test
    void addTest() {
        Ad expected = TestUtils.getAd1();
        when(adRepository.save(any())).thenReturn(expected);
        Ad actual = out.add(expected);
        assertEquals(expected,actual);
    }

    @Test
    void getAllTest() {
        Ad ad1 = TestUtils.getAd1();
        Ad ad2 = TestUtils.getAd2();
        List<Ad> expected = List.of(ad1,ad2);
        when(adRepository.findAll()).thenReturn(expected);
        List<Ad> actual = out.getAll();
        assertEquals(expected,actual);
    }

    @Test
    void getByIdTest() {
        Optional<Ad> expected = Optional.of(TestUtils.getAd1());
        when(adRepository.findById(anyInt())).thenReturn(expected);
        Optional<Ad> actual = out.getById(1);
        assertEquals(expected,actual);
    }

    @Test
    void getByAuthorTest() {
        Author author = TestUtils.getAuthor1();
        Ad ad1 = TestUtils.getAd1();
        ad1.setAuthor(author);
        Ad ad2 = TestUtils.getAd2();
        ad2.setAuthor(author);
        List<Ad> expected = List.of(ad1,ad2);
        when(adRepository.findByAuthor(any())).thenReturn(expected);
        List<Ad> actual = out.getByAuthor(author);
        assertEquals(expected,actual);
    }

    @Test
    void deleteByIdTest() {
        out.deleteById(1);
        verify(adRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void updateAdTest() {
        Ad expected = TestUtils.getAd1();
        when(adRepository.save(any())).thenReturn(expected);
        Ad actual = out.updateAd(expected);
        assertEquals(expected,actual);
    }

    @Test
    void mergeCreateOrUpdateAdDtoTest() {
        CreateOrUpdateAdDto properties = new CreateOrUpdateAdDto();
        properties.setTitle("title");
        properties.setPrice(111);
        properties.setDescription("description");
        Ad foundAd = TestUtils.getAd1();

        Ad mapperReturn = new Ad();
        mapperReturn.setTitle(properties.getTitle());
        mapperReturn.setPrice(properties.getPrice());
        mapperReturn.setDescription(properties.getDescription());

        Ad expected = new Ad();
        expected.setTitle(properties.getTitle());
        expected.setPrice(properties.getPrice());
        expected.setDescription(properties.getDescription());
        expected.setId(foundAd.getId());
        expected.setComments(foundAd.getComments());
        expected.setAuthor(foundAd.getAuthor());
        expected.setAdImage(foundAd.getAdImage());

        when(adMapper.toAd(any())).thenReturn(mapperReturn);

        Ad actual = out.mergeCreateOrUpdateAdDto(properties,foundAd);
        assertEquals(expected,actual);
    }
}