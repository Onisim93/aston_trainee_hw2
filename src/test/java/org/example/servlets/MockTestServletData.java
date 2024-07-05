package org.example.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MockTestData;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.example.service.mapper.GenreMapper;

public class MockTestServletData {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static final String author1;
    public static final String author2;
    public static final String author3;
    public static final String author4;
    public static final String author5;
    public static final String newAuthorDto;
    public static final String newAuthorCreatedDto;
    public static final String updatedAuthorDto;
    public static final String newAuthorBookDto;
    public static final String newAuthorBookCreatedDto;

    public static final String genre1;
    public static final String genre2;
    public static final String genre3;
    public static final String genre4;
    public static final String genre5;
    public static final String genre6;
    public static final String genre7;
    public static final String genre8;
    public static final String genre9;
    public static final String newGenreDto;
    public static final String newGenreCreatedDto;
    public static final String updatedGenreDto;


    public static final String book1;
    public static final String book2;
    public static final String book3;
    public static final String book4;
    public static final String book5;
    public static final String book6;
    public static final String book7;
    public static final String book8;
    public static final String book9;
    public static final String book10;
    public static final String book11;
    public static final String book12;
    public static final String book13;
    public static final String book14;
    public static final String book15;
    public static final String newBookDto;
    public static final String newBookCreatedDto;
    public static final String updatedBookDto;

    public static final String allAuthors;
    public static final String allGenres;
    public static final String allBooks;
    public static final String booksForGenre3AndAuthor2;
    public static final String booksForAuthor1;

    static {
        try {
            author1 = mapper.writeValueAsString(MockTestData.author1);
            author2 = mapper.writeValueAsString(MockTestData.author2);
            author3 = mapper.writeValueAsString(MockTestData.author3);
            author4 = mapper.writeValueAsString(MockTestData.author4);
            author5 = mapper.writeValueAsString(MockTestData.author5);
            newAuthorDto = mapper.writeValueAsString(MockTestData.newAuthorDto);
            newAuthorCreatedDto = mapper.writeValueAsString(MockTestData.newAuthorCreatedDto);
            updatedAuthorDto = mapper.writeValueAsString(MockTestData.updatedAuthorDto);

            genre1 = mapper.writeValueAsString(MockTestData.genre1);
            genre2 = mapper.writeValueAsString(MockTestData.genre2);
            genre3 = mapper.writeValueAsString(MockTestData.genre3);
            genre4 = mapper.writeValueAsString(MockTestData.genre4);
            genre5 = mapper.writeValueAsString(MockTestData.genre5);
            genre6 = mapper.writeValueAsString(MockTestData.genre6);
            genre7 = mapper.writeValueAsString(MockTestData.genre7);
            genre8 = mapper.writeValueAsString(MockTestData.genre8);
            genre9 = mapper.writeValueAsString(MockTestData.genre9);
            newGenreDto = mapper.writeValueAsString(MockTestData.newGenreDto);
            newGenreCreatedDto = mapper.writeValueAsString(MockTestData.newGenreCreatedDto);
            updatedGenreDto = mapper.writeValueAsString(MockTestData.updatedGenreDto);

            book1 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book1));
            book2 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book2));
            book3 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book3));
            book4 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book4));
            book5 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book5));
            book6 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book6));
            book7 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book7));
            book8 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book8));
            book9 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book9));
            book10 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book10));
            book11 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book11));
            book12 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book12));
            book13 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book13));
            book14 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book14));
            book15 = mapper.writeValueAsString(BookMapper.INSTANCE.toDto(MockTestData.book15));
            newBookDto = mapper.writeValueAsString(MockTestData.newBookDto);
            newBookCreatedDto = mapper.writeValueAsString(MockTestData.newBookCreatedDto);
            updatedBookDto = mapper.writeValueAsString(MockTestData.updatedBookDto);

            allAuthors = mapper.writeValueAsString(AuthorMapper.INSTANCE.toDtoList(MockTestData.allAuthors));
            allGenres = mapper.writeValueAsString(GenreMapper.INSTANCE.toDtoList(MockTestData.allGenres));
            allBooks = mapper.writeValueAsString(BookMapper.INSTANCE.toDtoList(MockTestData.allBooks));
            booksForGenre3AndAuthor2 = mapper.writeValueAsString(BookMapper.INSTANCE.toDtoList(MockTestData.booksForGenre3AndAuthor2));
            booksForAuthor1 = mapper.writeValueAsString(BookMapper.INSTANCE.toDtoList(MockTestData.booksForAuthor1));
            newAuthorBookDto = mapper.writeValueAsString(MockTestData.newAuthorBookDto);
            newAuthorBookCreatedDto = mapper.writeValueAsString(MockTestData.newAuthorBookCreatedDto);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
