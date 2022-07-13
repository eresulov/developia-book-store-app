package com.example.msbookstorewithautentication.service

import com.example.bookstoreapp.dao.entity.BookEntity
import com.example.bookstoreapp.dao.repository.BookRepository
import com.example.bookstoreapp.model.criteria.PageCriteria
import com.example.bookstoreapp.model.exception.UpdateBookException
import com.example.bookstoreapp.service.BookService
import io.github.benas.randombeans.EnhancedRandomBuilder

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookServiceTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private BookService service
    private BookRepository repository

    def setup() {
        repository = Mock()
        service = new BookService(repository)
    }


    def "UpdateBook success case"() {

        given:
        def publisherId = 1L
        def bookId = 1L
        def dto = random.nextObject(BookDto)
        def entity = random.nextObject(BookEntity)

        when:
        service.updateBook(bookId, publisherId, dto)
        entity.name = dto.name
        entity.publishingYear = dto.publishingYear
        entity.pageCount = dto.pageCount

        then:
        1 * repository.findByBookIdAndPublisherId(bookId, publisherId) >> Optional.of(entity)
        1 * repository.save(entity)

    }

    def "UpdateBook fail case"() {

        given:
        def publisherId = 1L
        def bookId = 1L
        def dto = random.nextObject(BookDto)
        def entity = random.nextObject(BookEntity)

        when:
        service.updateBook(bookId, publisherId, dto)
        entity.name = dto.name
        entity.publishingYear = dto.publishingYear
        entity.pageCount = dto.pageCount

        then:
        1 * repository.findByBookIdAndPublisherId(bookId, publisherId) >> Optional.empty()
        UpdateBookException ex = thrown()
        ex.message == "You can't update this because you are not the publisher of this book"
        ex.code == "UPDATE_BOOK_EXCEPTION"
        0 * repository.save(entity)

    }



    def "getBooksWhereNameLike  test"() {
        given:
        def entity = random.nextObject(BookEntity)
        def pageRequest = PageRequest.of(0, 100)
        def pageInfo = Mock(Page)
        PageCriteria pageCriteria = new PageCriteria()
        pageCriteria.count = 1
        pageCriteria.page = 1

        when:
        def result = service.getBooksWhereNameLike(null, pageCriteria)
        then:
        1 * repository.findAll(pageRequest) >> pageInfo
        result != null
    }
}
