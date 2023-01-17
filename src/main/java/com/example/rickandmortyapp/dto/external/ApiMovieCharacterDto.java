package com.example.rickandmortyapp.dto.external;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ApiMovieCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
