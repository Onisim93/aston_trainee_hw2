package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Book.
 * This class holds information about a book including its ID, title, description, published date, ISBN, author, and genres.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = {"genres", "author", "authorId", "genreIds"})
@EqualsAndHashCode(exclude = {"genres", "author", "authorId", "genreIds"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookDto implements BaseDto<Integer>{

    private Integer id;
    private String title;
    private String description;
    private String publishedDate;
    private String isbn;

    private AuthorDto author;
    private Integer authorId;
    private List<GenreDto> genres;
    private List<Integer> genreIds;


    public BookDto(String title, String description, String publishedDate, String isbn, Integer authorId, List<Integer> genreIds) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }


    public BookDto(Integer id, String title, String description, String publishedDate, String isbn, Integer authorId, List<Integer> genreIds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }

    public BookDto(String title, String description, String publishedDate, String isbn, List<Integer> genreIds) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.genreIds = genreIds;
    }
}
