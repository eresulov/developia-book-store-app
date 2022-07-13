package com.example.msbookstorewithautentication.service

import com.example.bookstoreapp.dao.entity.BookEntity
import com.example.bookstoreapp.dao.entity.PublisherEntity
import com.example.bookstoreapp.dao.repository.BookRepository
import com.example.bookstoreapp.dao.repository.PublisherRepository
import com.example.bookstoreapp.mapper.BookMapper
import com.example.bookstoreapp.model.exception.NotFoundException
import com.example.bookstoreapp.service.PublisherService
import io.github.benas.randombeans.EnhancedRandomBuilder

import spock.lang.Specification

class PublisherServiceTest extends Specification {


    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private PublisherService service
    private PublisherRepository publisherRepository
    private BookRepository bookRepository

    def setup() {
        publisherRepository = Mock()
        bookRepository = Mock()
        service = new PublisherService(publisherRepository, bookRepository)
    }


    def "AddBook success case"() {
        given:
        def id = 1L
        def dto = random.nextObject(BookDto)
        def publisherEntity = random.nextObject(PublisherEntity)
        def bookEntity = random.nextObject(BookEntity)

        when:
        service.addBook(id, dto)

        then:
        1 * publisherRepository.findById(id) >> Optional.of(publisherEntity)
        1 * bookRepository.save(BookMapper.mapDtoToEntity(dto))
    }

    def "AddBook fail case"() {
        given:
        def id = 1L
        def dto = random.nextObject(BookDto)

        when:
        service.addBook(id, dto)

        then:
        1 * publisherRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == String.format("%s with id %s not found", "Publisher", id)
        ex.code == String.format("%s_NOT_FOUND", "PUBLISHER")
        0 * bookRepository.save(BookMapper.mapDtoToEntity(dto))

    }

    def "getAllBooksByPublisher when publisher is present"() {

        given:
        def id = 1L
        def entity = random.nextObject(PublisherEntity)

        when:
        def result = service.getAllBooksByPublisher(id)

        then:
        1 * publisherRepository.findById(id) >> Optional.of(entity)
        result != null
    }



    def "getAllBooksByPublisher when publisher is not present"() {

        given:
        def id = 1l

        when:
        def result = service.getAllBooksByPublisher(id)

        then:
        1 * publisherRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == String.format("%s with id %s not found", "Publisher", id)
        ex.code == String.format("%s_NOT_FOUND", "PUBLISHER")
        result == null
    }
}
