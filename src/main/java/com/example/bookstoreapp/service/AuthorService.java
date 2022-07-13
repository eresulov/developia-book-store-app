package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dao.entity.AuthorEntity;
import com.example.bookstoreapp.dao.repository.AuthorRepository;
import com.example.bookstoreapp.mapper.AuthorMapper;
import com.example.bookstoreapp.model.dto.AuthorAndBookDetailsDto;
import com.example.bookstoreapp.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.bookstoreapp.model.constants.ExceptionConstants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorAndBookDetailsDto getAuthorAndDetailsAboutHisBooks(Long id) {
        log.info("ActionLog.getAuthorAndDetailsAboutHisBooks.start id: {}", id);

        var authorAndDetailsAboutHisBooks = AuthorMapper
                .mapAuthorBooksEntitiesToDtos(fetchAuthorIfExist(id));

        log.info("ActionLog.getAuthorAndDetailsAboutHisBooks.success id: {}", id);
        return authorAndDetailsAboutHisBooks;
    }


            private AuthorEntity fetchAuthorIfExist(Long id) {
                return authorRepository.findById(id).orElseThrow(() -> {
                    log.error("ActionLog.fetchAuthorIfExist.error: id: {}", id);
                    return new NotFoundException(String.format(NOT_FOUND_MESSAGE, "Author", id),
                            String.format(NOT_FOUND_CODE, "AUTHOR"));
                });
            }

}
