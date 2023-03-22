package com.superhero.services;

import com.superhero.shared.HeroDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HeroServices {

    List<HeroDTO> getAllhero();

    HeroDTO getHeroById(long id);

    void deleteHeroByID(Long id);

    HeroDTO update(HeroDTO data);

    HeroDTO create(HeroDTO data);

    List<HeroDTO> listHeroLike(String name);
}
