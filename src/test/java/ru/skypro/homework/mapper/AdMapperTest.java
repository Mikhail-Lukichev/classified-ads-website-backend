package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

import java.util.List;

class AdMapperTest {

    private final AdMapper adMapper = new AdMapper();

    @Test
    void toAdsDtoTest() {
        Ad ad1 = TestUtils.getAd1();
        Ad ad2 = TestUtils.getAd2();
        List<Ad> ads = List.of(ad1, ad2);
        AdsDto expected = TestUtils.getAdsDto();
        AdsDto actual = adMapper.toAdsDto(ads);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toAdDtoTest() {
        Ad ad1 = TestUtils.getAd1();
        AdDto expected = TestUtils.getAdDto1();
        AdDto actual = adMapper.toAdDto(ad1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toAdTest() {
        CreateOrUpdateAdDto input = TestUtils.getCreateOrUpdateAdDto1();
        Ad ad = TestUtils.getAd1();
        Ad expected = new Ad();
        expected.setTitle(ad.getTitle());
        expected.setPrice(ad.getPrice());
        expected.setDescription(ad.getDescription());

        Ad actual = adMapper.toAd(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toExtendedAdDtoTest() {
        Ad ad = TestUtils.getAd1();
        ExtendedAdDto expected = TestUtils.getExtendedAdDto1();
        ExtendedAdDto actual = adMapper.toExtendedAdDto(ad);
        Assertions.assertEquals(expected,actual);
    }
}