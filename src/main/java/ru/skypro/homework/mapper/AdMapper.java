package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class AdMapper {

    public AdsDto toAdsDto(List<Ad> ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(
                ads.stream()
                        .map(this::toAdDto)
                        .collect(Collectors.toList())
        );
        return adsDto;
    }

    public AdDto toAdDto(Ad ad) {
        AdDto result = new AdDto();
        result.setAuthor(ad.getAuthor().getId());
        result.setImage("to be refactored");
        result.setPk(ad.getId());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }

    public Ad toAd(CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad result = new Ad();
        result.setDescription(createOrUpdateAdDto.getDescription());
        result.setTitle(createOrUpdateAdDto.getTitle());
        result.setPrice(createOrUpdateAdDto.getPrice());
        return result;
    }

    public ExtendedAdDto toExtendedAdDto(Ad ad, Author author, AdImage adImage) {
        ExtendedAdDto result = new ExtendedAdDto();
        result.setPk(ad.getId());
        result.setAuthorFirstName(author.getFirstName());
        result.setAuthorLastName(author.getLastName());
        result.setDescription(ad.getDescription());
        result.setEmail(author.getEmail());
        result.setImage(adImage.getFilePath());
        result.setPhone(author.getPhone());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }
}
