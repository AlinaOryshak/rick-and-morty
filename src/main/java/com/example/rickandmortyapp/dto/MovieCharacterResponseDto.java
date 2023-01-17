package com.example.rickandmortyapp.dto;

import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MovieCharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
