package com.example.bookstoreapp.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PublisherAndBooksListDto {

    private String name;
    private List<BookDto> bookListDto;

}
