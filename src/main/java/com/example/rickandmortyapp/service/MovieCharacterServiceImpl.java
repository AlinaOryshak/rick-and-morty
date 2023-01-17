package com.example.rickandmortyapp.service;

import com.example.rickandmortyapp.dto.external.ApiMovieCharacterDto;
import com.example.rickandmortyapp.dto.external.ApiResponseDto;
import com.example.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.repository.MovieCharacterRepository;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final String GET_ALL_CHARACTERS_URL =
            "https://rickandmortyapi.com/api/character";

    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper movieCharacterMapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient
                .get(GET_ALL_CHARACTERS_URL, ApiResponseDto.class);
        updateInternalDB(apiResponseDto.getResults());
        String urlToNextPage = apiResponseDto.getInfo().getNext();
        while (urlToNextPage != null) {
            apiResponseDto = httpClient.get(urlToNextPage, ApiResponseDto.class);
            updateInternalDB(apiResponseDto.getResults());
            urlToNextPage = apiResponseDto.getInfo().getNext();
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long charactersCount = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * charactersCount);
        return movieCharacterRepository.findById(randomId)
                .orElseThrow(() ->
                        new RuntimeException("No movie character in DB with id " + randomId));
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    List<MovieCharacter> updateInternalDB(ApiMovieCharacterDto[] characterDtos) {
        Map<Long, ApiMovieCharacterDto> externalIdsAndDtos = Arrays.stream(characterDtos)
                .collect(Collectors.toMap(ApiMovieCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalIdsAndDtos.keySet();

        List<MovieCharacter> existingCharactersByExternalId = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingIdsAndCharacters = existingCharactersByExternalId
                .stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingIdsAndCharacters.keySet();

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .filter(i -> !existingIds.contains(i))
                .map(i -> movieCharacterMapper
                        .parseApiMovieCharacterDto(externalIdsAndDtos.get(i)))
                .toList();
        movieCharacterRepository.saveAll(charactersToSave);
        return charactersToSave;
    }
}
