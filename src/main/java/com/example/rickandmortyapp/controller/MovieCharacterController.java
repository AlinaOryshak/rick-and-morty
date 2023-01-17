package com.example.rickandmortyapp.controller;

import com.example.rickandmortyapp.dto.MovieCharacterResponseDto;
import com.example.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.service.MovieCharacterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @ApiOperation(value = "Get random character wiki")
    @GetMapping("/random")
    public MovieCharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toResponseDto(randomCharacter);
    }

    @ApiOperation(value = "Get all characters whose name contains input word")
    @GetMapping("/by-name")
    public List<MovieCharacterResponseDto> findByNamePart(@ApiParam(value = "Part of name")
                                                              @RequestParam("name")
                                                              String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(movieCharacterMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
