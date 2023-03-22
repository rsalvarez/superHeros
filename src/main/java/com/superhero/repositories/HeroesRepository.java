package com.superhero.repositories;

import com.superhero.model.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroesRepository extends CrudRepository<Hero, Long> {

    public List<Hero> findByNameContainingIgnoreCase(String name);
    public Optional<Hero> findByNameIgnoreCase(String name);

}
