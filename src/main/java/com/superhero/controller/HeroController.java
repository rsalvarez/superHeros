package com.superhero.controller;


import com.superhero.services.HeroServices;
import com.superhero.shared.HeroDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/heros")
public class HeroController {

    private final HeroServices heroServices;

    public HeroController(HeroServices heroServices) {
        this.heroServices = heroServices;
    }

    @Operation(summary = "list All heros in database", description = "getAllheros")
    @GetMapping("/all")
    public List<HeroDTO> getAllheros() {
        return heroServices.getAllhero();
    }


    @Operation(summary = "hero list with id : {id}", description = "getHero")
    @GetMapping("/{id}")
    public HeroDTO getHero(@PathVariable Long id) {
        return heroServices.getHeroById(id);
    }

    @Operation(summary = "Add a hero", description = "addhero")
    @PutMapping("/add")
    public HeroDTO addhero(@RequestBody HeroDTO data ) {
        return heroServices.create(data);
    }

    @Operation(summary = "Edit a hero", description = "edithero")
    @PostMapping("/edit")
    public HeroDTO edithero(@RequestBody HeroDTO data ) {
        return heroServices.update(data);
    }

    @DeleteMapping("/{id}")
    public void delhero(@PathVariable Long id) {
        heroServices.deleteHeroByID(id);
    }
    @Operation(summary = "list the heroes that contain \"name\"", description = "getHeroLike")
    @GetMapping("/filter/{name}")
    public List<HeroDTO> getHeroLike(@PathVariable String name) {

        return heroServices.listHeroLike(name);
    }

}
