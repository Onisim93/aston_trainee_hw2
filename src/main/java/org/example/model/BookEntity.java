package org.example.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

/**
 * Represents a book entity.
 * This class holds information about a book, including his id, title, description, published_date, isbn, author and genres.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = {"author", "genres"})
public class BookEntity implements BaseEntity<Integer> {
    /**
     * The unique identifier of the book.
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
     * * Пр. 18 июня 1991 / 18.06.1991
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, publishedDate, isbn);
    }

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
