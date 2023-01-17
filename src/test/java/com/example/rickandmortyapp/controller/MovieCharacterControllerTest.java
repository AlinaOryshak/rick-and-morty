package com.example.rickandmortyapp.controller;

import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.model.Status;
import com.example.rickandmortyapp.service.MovieCharacterService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterService movieCharacterService;
    @Autowired
    private MockMvc mockMvc;
    private MovieCharacter mockedCharacter;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        mockedCharacter = new MovieCharacter();
        mockedCharacter.setId(15L);
        mockedCharacter.setExternalId(12L);
        mockedCharacter.setName("Boobie");
        mockedCharacter.setGender(Gender.UNKNOWN);
        mockedCharacter.setStatus(Status.ALIVE);
    }

    @Test
    public void getRandomCharacter_whenSavedOneObject_ok() {
        Mockito.when(movieCharacterService.getRandomCharacter()).thenReturn(mockedCharacter);
        RestAssuredMockMvc.when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(15))
                .body("externalId", Matchers.equalTo(12))
                .body("name", Matchers.equalTo("Boobie"))
                .body("gender", Matchers.equalTo("UNKNOWN"))
                .body("status", Matchers.equalTo("ALIVE"));
    }

    @Test
    public void findByNamePart_wherePartIsBob_ok() {
        String namePart = "Bob";
        List<MovieCharacter> mockedCharacters = List.of(mockedCharacter);
        Mockito.when(movieCharacterService.findAllByNameContains(namePart))
                .thenReturn(mockedCharacters);
        RestAssuredMockMvc
                .given()
                .queryParam("name", namePart)
                .when()
                .get("/movie-characters/by-name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(15))
                .body("[0].externalId", Matchers.equalTo(12))
                .body("[0].name", Matchers.equalTo("Boobie"))
                .body("[0].gender", Matchers.equalTo("UNKNOWN"))
                .body("[0].status", Matchers.equalTo("ALIVE"));
    }
}