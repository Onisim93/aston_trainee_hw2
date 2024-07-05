package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Author.
 * This class holds information about an author including their ID, name, and biography.
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorDto implements BaseDto<Integer>{

    private Integer id;
    private String name;
    private String bio;

    public AuthorDto(Integer id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    public AuthorDto(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}
