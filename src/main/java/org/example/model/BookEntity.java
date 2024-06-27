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
public class BookEntity implements BaseEntity<Integer> {
    /**
     * ID - primary key
     */
    private Integer id;

    /**
     * Название книги
     */
    private String title;

    /**
     * Описание книги
     */
    private String description;

    /**
     * Дата выхода книги
     */
    private String publishedDate;

    /**
     * ISBN - International Standard Book Number
     * Муждународный стандартный книжный номер
     * Состоит из 13 цифр
     * Пр. ISBN-13:978-3-16-148410-0
     */
    private String isbn;

    /**
     * Автор, связь ManyToOne
     */
    private AuthorEntity author;
    /**
     * Жанры, связь ManyToMany
     */
    private List<GenreEntity> genres;

    private BookEntity(BookEntityBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.publishedDate = builder.publishedDate;
        this.isbn = builder.isbn;
        this.author = builder.author;
        this.genres = builder.genres;
    }

    public static BookEntityBuilder builder() {
        return new BookEntityBuilder();
    }

    /**
     * Builder
     */
    public static class BookEntityBuilder {
        private Integer id;
        private String title;
        private String description;
        private String publishedDate;
        private String isbn;
        private AuthorEntity author;
        private List<GenreEntity> genres;

        private BookEntityBuilder() {
        }

        public BookEntityBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public BookEntityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookEntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookEntityBuilder publishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public BookEntityBuilder author(AuthorEntity author) {
            this.author = author;
            return this;
        }

        public BookEntityBuilder genres(List<GenreEntity> genres) {
            this.genres = genres;
            return this;
        }

        public BookEntityBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookEntity build() {
            return new BookEntity(this);
        }
    }
}
