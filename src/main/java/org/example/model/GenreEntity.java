package org.example.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class GenreEntity implements BaseEntity<Integer> {
    /**
     * ID - primary key
     */
    private Integer id;
    /**
     * Название жанра
     */
    private String name;
    /**
     * Краткое описание жанра, макс. 1024 символов
     */
    private String description;
    /**
     * Список книг данного жанра
     */
    private List<BookEntity> books;

    private GenreEntity(GenreEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.books = builder.books;
    }

    public static GenreEntityBuilder builder() {
        return new GenreEntityBuilder();
    }

    /**
     * Builder
     */
    public static class GenreEntityBuilder {
        private Integer id;
        private String name;
        private String description;
        private List<BookEntity> books;
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
        public GenreEntityBuilder books(List<BookEntity> books) {
            this.books = books;
            return this;
        }
        public GenreEntity build() {
            return new GenreEntity(this);
        }
    }

}
