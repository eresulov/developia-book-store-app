package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.model.dto.AuthorAndBookDetailsDto;
import com.example.bookstoreapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorController {


    private final AuthorService authorService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','PUBLISHER')")
    public AuthorAndBookDetailsDto getAuthorAndDetailsAboutHisBooks(@PathVariable Long id) {
        return authorService.getAuthorAndDetailsAboutHisBooks(id);
    }



}
