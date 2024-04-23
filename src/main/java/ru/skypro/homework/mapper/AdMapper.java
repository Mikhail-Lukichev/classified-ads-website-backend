package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    private String adImagesPath = "/image/adImage/";

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
        result.setImage(adImagesPath + ad.getId());
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

    public ExtendedAdDto toExtendedAdDto(Ad ad) {
        ExtendedAdDto result = new ExtendedAdDto();
        result.setPk(ad.getId());
        result.setAuthorFirstName(ad.getAuthor().getFirstName());
        result.setAuthorLastName(ad.getAuthor().getLastName());
        result.setDescription(ad.getDescription());
        result.setEmail(ad.getAuthor().getEmail());
        result.setImage(adImagesPath + ad.getId());
        result.setPhone(ad.getAuthor().getPhone());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }
}
