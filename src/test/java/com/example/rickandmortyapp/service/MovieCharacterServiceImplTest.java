package com.example.rickandmortyapp.service;

import com.example.rickandmortyapp.dto.external.ApiMovieCharacterDto;
import com.example.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.model.Status;
import com.example.rickandmortyapp.repository.MovieCharacterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository movieCharacterRepository;
    @Mock
    private MovieCharacterMapper movieCharacterMapper;
    private ApiMovieCharacterDto[] characterDtos;
    private Map<Long, MovieCharacter> existingIdsAndCharacters;

    @BeforeEach
    public void setUp() {
        ApiMovieCharacterDto characterDto1 = new ApiMovieCharacterDto();
        characterDto1.setId(1L);
        characterDto1.setName("Bob Bobby");
        characterDto1.setGender(Gender.MALE.name());
        characterDto1.setStatus(Status.ALIVE.name());

        ApiMovieCharacterDto characterDto2 = new ApiMovieCharacterDto();
        characterDto2.setId(2L);
        characterDto2.setName("Alice Allian");
        characterDto2.setGender(Gender.FEMALE.name());
        characterDto2.setStatus(Status.DEAD.name());

        ApiMovieCharacterDto characterDto3 = new ApiMovieCharacterDto();
        characterDto3.setId(3L);
        characterDto3.setName("Nicky Nikel");
        characterDto3.setGender(Gender.GENDERLESS.name());
        characterDto3.setStatus(Status.DEAD.name());

        characterDtos = new ApiMovieCharacterDto[]{characterDto1, characterDto2, characterDto3};

        MovieCharacter movieCharacter1 = new MovieCharacter();
        movieCharacter1.setId(1L);
        movieCharacter1.setExternalId(1L);
        movieCharacter1.setName("Bob Bobby");
        movieCharacter1.setGender(Gender.MALE);
        movieCharacter1.setStatus(Status.ALIVE);

        MovieCharacter movieCharacter2 = new MovieCharacter();
        movieCharacter2.setId(2L);
        movieCharacter2.setExternalId(2L);
        movieCharacter2.setName("Alice Allian");
        movieCharacter2.setGender(Gender.FEMALE);
        movieCharacter2.setStatus(Status.DEAD);

        existingIdsAndCharacters = Map.of(
                movieCharacter1.getExternalId(), movieCharacter1,
                movieCharacter2.getExternalId(), movieCharacter2);
    }

    @Test
    public void updateInternalDB_saveOneObject_ok() {
        Mockito.doReturn(new ArrayList<>(existingIdsAndCharacters.values()))
                .when(movieCharacterRepository).findAllByExternalIdIn(Mockito.anySet());
        MovieCharacter characterToSave = new MovieCharacter();
        characterToSave.setExternalId(3L);
        characterToSave.setName("Nicky Nikel");
        characterToSave.setGender(Gender.GENDERLESS);
        characterToSave.setStatus(Status.DEAD);
        Mockito.doReturn(characterToSave)
                .when(movieCharacterMapper).parseApiMovieCharacterDto(characterDtos[2]);
        Mockito.doReturn(List.of(characterToSave))
                .when(movieCharacterRepository).saveAll(Mockito.any());
        List<MovieCharacter> actual = movieCharacterService.updateInternalDB(characterDtos);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(List.of(characterToSave), actual);
    }
}