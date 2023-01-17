package com.example.rickandmortyapp.dto.mapper;

import com.example.rickandmortyapp.dto.MovieCharacterResponseDto;
import com.example.rickandmortyapp.dto.external.ApiMovieCharacterDto;
import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieCharacterMapperTest {
    private final static Long ID = 8L;
    private final static Long EXTERNAL_ID = 5L;
    private final static String NAME = "Bob Bobs";
    private final static String NOT_VALID_NAME = "Ami Annoys";
    private final static Gender MAlE_GENDER = Gender.MALE;
    private final static Status AlIVE_STATUS = Status.ALIVE;

    private final MovieCharacterMapper movieCharacterMapper = new MovieCharacterMapper();
    private ApiMovieCharacterDto apiMovieCharacterDto;
    private MovieCharacter movieCharacter;
    private MovieCharacterResponseDto characterResponseDto;

    @BeforeEach
    public void setUp() {
        apiMovieCharacterDto = new ApiMovieCharacterDto();
        apiMovieCharacterDto.setId(EXTERNAL_ID);
        apiMovieCharacterDto.setName(NAME);
        apiMovieCharacterDto.setGender(MAlE_GENDER.name());
        apiMovieCharacterDto.setStatus(AlIVE_STATUS.name());

        movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(EXTERNAL_ID);
        movieCharacter.setName(NAME);
        movieCharacter.setGender(MAlE_GENDER);
        movieCharacter.setStatus(AlIVE_STATUS);

        characterResponseDto = new MovieCharacterResponseDto();
        characterResponseDto.setId(ID);
        characterResponseDto.setExternalId(EXTERNAL_ID);
        characterResponseDto.setName(NAME);
        characterResponseDto.setGender(MAlE_GENDER);
        characterResponseDto.setStatus(AlIVE_STATUS);
    }

    @Test
    public void parse_apiMovieCharacterDtoToMovieCharacter_ok() {
        MovieCharacter expected = movieCharacter;
        MovieCharacter actual = movieCharacterMapper
                .parseApiMovieCharacterDto(apiMovieCharacterDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void parse_apiMovieCharacterDtoToMovieCharacter_notOk() {
        MovieCharacter expected = movieCharacter;
        MovieCharacter actual = movieCharacterMapper
                .parseApiMovieCharacterDto(apiMovieCharacterDto);
        expected.setName(NOT_VALID_NAME);
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void parse_MovieCharacterToResponseDto_ok() {
        movieCharacter.setId(ID);
        MovieCharacterResponseDto expected = characterResponseDto;
        MovieCharacterResponseDto actual = movieCharacterMapper.toResponseDto(movieCharacter);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void parse_MovieCharacterToResponseDto_notOk() {
        movieCharacter.setId(ID);
        MovieCharacterResponseDto expected = characterResponseDto;
        MovieCharacterResponseDto actual = movieCharacterMapper.toResponseDto(movieCharacter);
        expected.setName(NOT_VALID_NAME);
        Assertions.assertNotEquals(expected, actual);
    }
}
