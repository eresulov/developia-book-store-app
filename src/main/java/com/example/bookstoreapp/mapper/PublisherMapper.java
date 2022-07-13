package com.example.bookstoreapp.mapper;


import com.example.bookstoreapp.dao.entity.PublisherEntity;
import com.example.bookstoreapp.model.dto.PublisherAndBooksListDto;

import java.util.stream.Collectors;


public class PublisherMapper {


    public static PublisherAndBooksListDto mapEntityToPublisherBooksDto(PublisherEntity entity){
        return PublisherAndBooksListDto.builder()
                .name(entity.getName())
                .bookListDto(entity.getBooks().stream()
                        .map(BookMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
