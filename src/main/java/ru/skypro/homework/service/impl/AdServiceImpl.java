package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl {

    private final AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository) {
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
}
