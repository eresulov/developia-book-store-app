package com.example.bookstoreapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@AllArgsConstructor
@Builder
public class AuthorAndBookDetailsDto {

    private String firstName;
    private String lastName;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthDay;

    private List<BookDto> authorBooksDto;

}
