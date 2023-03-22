package com.superhero.services;

import com.superhero.exception.ExResponse;
import com.superhero.model.Hero;
import com.superhero.repositories.HeroesRepository;
import com.superhero.shared.HeroDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroServicesImplTest {

    @Mock
    private HeroesRepository mockHeroRepository;
    private ModelMapper mockModelMapper;

    private HeroServicesImpl heroServicesImplUnderTest;

    @BeforeEach
    void setUp() {

        mockModelMapper=new ModelMapper();

        heroServicesImplUnderTest = new HeroServicesImpl(mockHeroRepository, mockModelMapper);
    }

    @Test
    void testGetAllhero() {
        // Setup
        final List<HeroDTO> expectedResult = List.of(new HeroDTO(0L, "name"));
        // Configure HeroesRepository.findAll(...).
        final Hero hero = new Hero();
        hero.setId(0L);
        hero.setName("name");
        final Iterable<Hero> heroes = List.of(hero);
        when(mockHeroRepository.findAll()).thenReturn(heroes);

        // Run the test
        final List<HeroDTO> result = heroServicesImplUnderTest.getAllhero();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllhero_HeroesRepositoryReturnsNoItems() {
        // Setup
        when(mockHeroRepository.findAll()).thenReturn(Collections.emptyList());
        // Run the test
        final List<HeroDTO> result = heroServicesImplUnderTest.getAllhero();
        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetHeroById() {
        // Setup
        final HeroDTO expectedResult = new HeroDTO(0L, "name");

        // Configure HeroesRepository.findById(...).
        final Hero hero1 = new Hero();
        hero1.setId(0L);
        hero1.setName("name");
        final Optional<Hero> hero = Optional.of(hero1);
        when(mockHeroRepository.findById(0L)).thenReturn(hero);

        // Run the test
        final HeroDTO result = heroServicesImplUnderTest.getHeroById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetHeroById_HeroesRepositoryReturnsAbsent() {
        // Setup
        final HeroDTO expectedResult = new HeroDTO(0L, "name");
        when(mockHeroRepository.findById(0L)).thenReturn(Optional.empty());
        // Run the test
        final HeroDTO result = heroServicesImplUnderTest.getHeroById(0L);
        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testDeleteHeroByID() {
        // Setup
        // Configure HeroesRepository.findById(...).
        final Hero hero1 = new Hero();
        hero1.setId(0L);
        hero1.setName("name");
        final Optional<Hero> hero = Optional.of(hero1);
        when(mockHeroRepository.findById(0L)).thenReturn(hero);

        // Run the test
        heroServicesImplUnderTest.deleteHeroByID(0L);

        // Verify the results
        verify(mockHeroRepository).deleteById(0L);
    }

    @Test
    void testDeleteHeroByID_HeroesRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockHeroRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> heroServicesImplUnderTest.deleteHeroByID(0L))
                .isInstanceOf(ExResponse.class);
    }

    @Test
    void testUpdate() {
        // Setup
        final HeroDTO data = new HeroDTO(0L, "name");
        final HeroDTO expectedResult = new HeroDTO(0L, "name");

        // Configure HeroesRepository.findById(...).
        final Hero hero1 = new Hero();
        hero1.setId(0L);
        hero1.setName("name");
        final Optional<Hero> hero = Optional.of(hero1);
        when(mockHeroRepository.findById(0L)).thenReturn(hero);

        // Configure HeroesRepository.save(...).
        final Hero hero2 = new Hero();
        hero2.setId(0L);
        hero2.setName("NameNuevo");
        when(mockHeroRepository.save(Mockito.any())).thenReturn(hero2);

        // Run the test
        final HeroDTO result = heroServicesImplUnderTest.update(data);

        // Verify the results

        assertThat(result.getName()).isEqualTo("NameNuevo");
    }

    @Test
    void testUpdate_HeroesRepositoryFindByIdReturnsAbsent() {
        // Setup
        final HeroDTO data = new HeroDTO(0L, "name");
        when(mockHeroRepository.findById(0L)).thenReturn(Optional.empty());
        // Run the test
        assertThatThrownBy(() -> heroServicesImplUnderTest.update(data)).isInstanceOf(ExResponse.class);
    }

    @Test
    void testCreate() {
        // Setup
        final HeroDTO data = new HeroDTO(0L, "name");
        final HeroDTO expectedResult = new HeroDTO(0L, "name");

        // Configure HeroesRepository.findByNameIgnoreCase(...).
        final Hero hero1 = new Hero();
        hero1.setId(0L);
        hero1.setName("name");
        final Optional<Hero> hero = Optional.of(hero1);
        when(mockHeroRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        // Configure ModelMapper.map(...).
        final Hero hero2 = new Hero();
        hero2.setId(0L);
        hero2.setName("name");

        // Configure HeroesRepository.save(...).
        final Hero hero3 = new Hero();
        hero3.setId(0L);
        hero3.setName("name");
        when(mockHeroRepository.save(Mockito.any())).thenReturn(hero3);

        // Run the test

        final HeroDTO result = heroServicesImplUnderTest.create(data);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreate_HeroesRepositoryFindByNameIgnoreCaseReturnsAbsent() {
        // Setup
        final HeroDTO data = new HeroDTO(0L, "name");
        final HeroDTO expectedResult = new HeroDTO(0L, "name");
        when(mockHeroRepository.findByNameIgnoreCase("name")).thenReturn(Optional.empty());

        // Configure ModelMapper.map(...).
        final Hero hero = new Hero();
        hero.setId(0L);
        hero.setName("name");

        // Configure HeroesRepository.save(...).
        final Hero hero1 = new Hero();
        hero1.setId(0L);
        hero1.setName("name");
        when(mockHeroRepository.save(Mockito.any())).thenReturn(hero1);

        // Run the test
        final HeroDTO result = heroServicesImplUnderTest.create(data);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testListHeroLike() {
        // Setup
        final List<HeroDTO> expectedResult = List.of(new HeroDTO(0L, "name"));

        // Configure HeroesRepository.findByNameContainingIgnoreCase(...).
        final Hero hero = new Hero();
        hero.setId(0L);
        hero.setName("name");
        final List<Hero> heroes = List.of(hero);
        when(mockHeroRepository.findByNameContainingIgnoreCase("name")).thenReturn(heroes);

        // Run the test
        final List<HeroDTO> result = heroServicesImplUnderTest.listHeroLike("name");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testListHeroLike_HeroesRepositoryReturnsNoItems() {
        // Setup
        when(mockHeroRepository.findByNameContainingIgnoreCase("name")).thenReturn(Collections.emptyList());

        // Run the test
        final List<HeroDTO> result = heroServicesImplUnderTest.listHeroLike("name");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
