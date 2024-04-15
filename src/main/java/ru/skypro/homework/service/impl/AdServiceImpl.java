package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdMapper adMapper;
    private final AdRepository adRepository;

    public AdServiceImpl(AdMapper adMapper, AdRepository adRepository) {
        this.adMapper = adMapper;
        this.adRepository = adRepository;
    }

    public Ad add(Ad ad) {
        return adRepository.save(ad);
    }

    public List<Ad> getAll() {
        return adRepository.findAll();
    }

    public Optional<Ad> getById(Integer id) {
        return adRepository.findById(id);
    }

    public List<Ad> getByAuthor(Author author) {
        return adRepository.findByAuthor(author);
    }

    public void deleteById(Integer id){
        adRepository.deleteById(id);
    }

    public Ad updateAd(Ad ad) {
        return adRepository.save(ad);
    }

    public Ad mergeCreateOrUpdateAdDto(CreateOrUpdateAdDto properties, Ad foundAd) {
        Ad updateAd = adMapper.toAd(properties);
        updateAd.setId(foundAd.getId());
        updateAd.setComments(foundAd.getComments());
        updateAd.setAuthor(foundAd.getAuthor());
        updateAd.setAdImage(foundAd.getAdImage());
        return updateAd;
    }
}
