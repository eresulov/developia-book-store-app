package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dao.entity.PublisherEntity;
import com.example.bookstoreapp.dao.repository.BookRepository;
import com.example.bookstoreapp.dao.repository.PublisherRepository;
import com.example.bookstoreapp.mapper.BookMapper;
import com.example.bookstoreapp.mapper.PublisherMapper;
import com.example.bookstoreapp.model.dto.BookDto;
import com.example.bookstoreapp.model.dto.PublisherAndBooksListDto;
import com.example.bookstoreapp.model.exception.NotFoundException;
import com.example.bookstoreapp.model.exception.UniquenessViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.bookstoreapp.model.constants.ExceptionConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherService {

    
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public void addBook(Long id, BookDto bookDto) {
        log.info("ActionLog.addBook.start id: {}", id);

        var checkBookName = bookRepository.findByName(bookDto.getName());
        if (checkBookName.isEmpty()) {

            var bookEntity = BookMapper.mapDtoToEntity(bookDto);
            bookEntity.setPublisher(fetchPublisherIfExist(id));

            bookRepository.save(bookEntity);

            log.info("ActionLog.addBook.success id: {}", id);
        } else {
            log.error("ActionLog.addBook.error");
            throw new UniquenessViolationException(String.format(VALIDATION_MESSAGE, " book", "", ""), VALIDATION_EXCEPTION_CODE);
        }
    }

    public PublisherAndBooksListDto getAllBooksByPublisher(Long id) {
        log.info("ActionLog.getAllBooksBySpecificPublisher.start id: {}", id);

        var publisherWithHisBooksDto = PublisherMapper
                .mapEntityToPublisherBooksDto(fetchPublisherIfExist(id));

        log.info("ActionLog.getAllBooksBySpecificPublisher.success id: {}", id);
        return publisherWithHisBooksDto;
    }


            private PublisherEntity fetchPublisherIfExist(Long id) {
                return publisherRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("ActionLog.fetchPublisherIfExist.error id: {}", id);
                            return new NotFoundException(String.format(NOT_FOUND_MESSAGE, "Publisher", id),
                                    String.format(NOT_FOUND_CODE, "PUBLISHER"));
                        });
            }





}
