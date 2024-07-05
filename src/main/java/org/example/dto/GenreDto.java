package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Data Transfer Object (DTO) for Genre.
 * This class holds information about a genre including its ID, name, and description.
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenreDto implements BaseDto<Integer>{

    private Integer id;
    private String name;
    private String description;

    public GenreDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public GenreDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
