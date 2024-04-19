package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdServiceImpl implements AdService {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    private final AdMapper adMapper;
    private final AdRepository adRepository;

    public AdServiceImpl(AdMapper adMapper, AdRepository adRepository) {
        this.adMapper = adMapper;
        this.adRepository = adRepository;
    }

    public Ad add(Ad ad) {
        logger.debug("AdServiceImpl add()");
        return adRepository.save(ad);
    }

    public List<Ad> getAll() {
        logger.debug("AdServiceImpl getAll()");
        return adRepository.findAll();
    }

    public Optional<Ad> getById(Integer id) {
        logger.debug("AdServiceImpl getById()");
        return adRepository.findById(id);
    }

    public List<Ad> getByAuthor(Author author) {
        logger.debug("AdServiceImpl getByAuthor()");
        return adRepository.findByAuthor(author);
    }

    public void deleteById(Integer id){
        logger.debug("AdServiceImpl deleteById()");
        adRepository.deleteById(id);
    }

    public Ad updateAd(Ad ad) {
        logger.debug("AdServiceImpl updateAd()");
        return adRepository.save(ad);
    }

    public Ad mergeCreateOrUpdateAdDto(CreateOrUpdateAdDto properties, Ad foundAd) {
        logger.debug("AdServiceImpl mergeCreateOrUpdateAdDto()");
        Ad updateAd = adMapper.toAd(properties);
        updateAd.setId(foundAd.getId());
        updateAd.setComments(foundAd.getComments());
        updateAd.setAuthor(foundAd.getAuthor());
        updateAd.setAdImage(foundAd.getAdImage());
        return updateAd;
    }
}
