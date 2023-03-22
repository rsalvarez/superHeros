package com.superhero.services;


import com.superhero.exception.ExResponse;
import com.superhero.model.Hero;
import com.superhero.repositories.HeroesRepository;
import com.superhero.shared.HeroDTO;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HeroServicesImpl implements HeroServices {

    private final HeroesRepository heroRepository;
    private final ModelMapper modelMapper;

    public HeroServicesImpl(HeroesRepository heroRepository, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "heroes")
    public List<HeroDTO> getAllhero() {
        try {
            return StreamSupport.stream(heroRepository.findAll().spliterator(), false)
                    .map(hero -> modelMapper.map(hero, HeroDTO.class))
                    .collect(Collectors.toList());
        } catch (EmptyResultDataAccessException e) {
            throw new ExResponse("Database empty ", HttpStatus.BAD_REQUEST.value(), null);
        }

    }

    @Override
    public HeroDTO getHeroById(long id) {
        return modelMapper.map(heroRepository.findById(id), HeroDTO.class);
    }

    @Caching(evict = {@CacheEvict(value = "heroes", allEntries = true)})
    @Override
    public void deleteHeroByID(Long id) {
        var hero = heroRepository.findById(id);
        hero.orElseThrow(() -> {
            throw new ExResponse("Error, the hero to delete does not exist", HttpStatus.BAD_REQUEST.value(), null);
        });
        heroRepository.deleteById(id);
    }

    @Caching(evict = {@CacheEvict(value = "heroes", allEntries = true)})
    @Override
    public HeroDTO update(HeroDTO data) {
        Hero updateable = null;
        try {
            updateable = heroRepository.findById(data.getId()).get();
        } catch (NoSuchElementException e) {
            throw new ExResponse("Error Hero not found", HttpStatus.NOT_FOUND.value(), e);
        }
        updateable.setName(data.getName());
        return modelMapper.map(heroRepository.save(updateable), HeroDTO.class);
    }

    @Caching(evict = {@CacheEvict(value = "heroes", allEntries = true)})
    @Override
    public HeroDTO create(HeroDTO data) {
        data.setId(null);
        var control = heroRepository.findByNameIgnoreCase(data.getName());
        control.ifPresent(x -> {
            throw new ExResponse(String.format("Hero %s found in the database", data.getName()), HttpStatus.BAD_REQUEST.value(), null);
        });
        Hero newHero = modelMapper.map(data, Hero.class);
        return modelMapper.map(heroRepository.save(newHero), HeroDTO.class);
    }

    @Cacheable(value = "heroes")
    @Override
    public List<HeroDTO> listHeroLike(String name) {
        var control = heroRepository.findByNameContainingIgnoreCase(name);
        return control.stream().map(x -> modelMapper.map(x, HeroDTO.class)).collect(Collectors.toList());


    }

}
