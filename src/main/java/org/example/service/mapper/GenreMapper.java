package org.example.service.mapper;

import org.example.model.GenreEntity;
import org.example.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreEntity toModel(GenreDto genreDto);

    GenreDto toDto(GenreEntity genreEntity);

    List<GenreDto> toDtoList(List<GenreEntity> genreEntities);
}
