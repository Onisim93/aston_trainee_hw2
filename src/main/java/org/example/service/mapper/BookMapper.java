package org.example.service.mapper;

import org.example.dto.BookDto;
import org.example.model.AuthorEntity;
import org.example.model.BookEntity;
import org.example.model.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "author", source = "authorId")
    BookEntity toModel(BookDto bookDto);

    @Mapping(target = "genreIds", source = "genres")
    @Mapping(target = "authorId", source = "author")
    BookDto toDto(BookEntity book);

    List<BookDto> toDtoList(List<BookEntity> books);

    default List<GenreEntity> mapGenreIdsToGenreEntities(List<Integer> genreIds) {
        return genreIds.stream().map(g -> GenreEntity.builder().id(g).build()).toList();
    }

    default AuthorEntity mapAuthorIdToAuthorEntity(Integer authorId) {
        return AuthorEntity.builder().id(authorId).build();
    }

    default Integer mapAuthorEntityToAuthorId(AuthorEntity author) {
        return author.getId();
    }

    default List<Integer> mapGenresToGenreIds(List<GenreEntity> genreIds) {
        return genreIds.stream().map(GenreEntity::getId).toList();
    }
}
