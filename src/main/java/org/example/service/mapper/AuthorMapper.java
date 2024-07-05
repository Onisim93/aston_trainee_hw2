package org.example.service.mapper;


import org.example.model.AuthorEntity;
import org.example.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorEntity toModel(AuthorDto authorDto);
    AuthorDto toDto(AuthorEntity authorEntity);

    List<AuthorDto> toDtoList(List<AuthorEntity> authorEntities);
}
