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
public class AuthorEntity implements BaseEntity<Integer> {
    /**
     * ID - primary key
     */
    private Integer id;
    /**
     * ФИО автора
     */
    private String name;
    /**
     * Краткая биография автора, макс. 2048 символов
     */
    private String bio;
    /**
     * Список книг автора
     */
    private List<BookEntity> books;

    private AuthorEntity(AuthorEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.bio = builder.bio;
        this.books = builder.books;
    }

    public static AuthorEntityBuilder builder() {
        return new AuthorEntityBuilder();
    }

    /**
     * Builder
     */
    public static class AuthorEntityBuilder {
        private Integer id;
        private String name;
        private String bio;
        private List<BookEntity> books;

        public AuthorEntityBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        public AuthorEntityBuilder name(String name) {
            this.name = name;
            return this;
        }
        public AuthorEntityBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }
        public AuthorEntityBuilder books(List<BookEntity> books) {
            this.books = books;
            return this;
        }
        public AuthorEntity build() {
            return new AuthorEntity(this);
        }
    }



}
