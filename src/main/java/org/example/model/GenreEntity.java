package org.example.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a genre entity.
 * This class holds information about a genre, including his id, name and description.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class GenreEntity implements BaseEntity<Integer> {
    /**
     * The unique identifier of the genre.
     */
    private Integer id;
    /**
     * The name of the genre
     */
    private String name;
    /**
     * The description of the genre. Max length 1024.
     */
    private String description;

    private GenreEntity(GenreEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }

    public static GenreEntityBuilder builder() {
        return new GenreEntityBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity genre = (GenreEntity) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name) && Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    /**
     * Builder
     */
    public static class GenreEntityBuilder {
        private Integer id;
        private String name;
        private String description;

        public GenreEntityBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        public GenreEntityBuilder name(String name) {
            this.name = name;
            return this;
        }
        public GenreEntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GenreEntity build() {
            return new GenreEntity(this);
        }
    }

}
