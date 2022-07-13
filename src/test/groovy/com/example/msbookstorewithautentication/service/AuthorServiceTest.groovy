package com.example.msbookstorewithautentication.service

import com.example.bookstoreapp.dao.entity.AuthorEntity
import com.example.bookstoreapp.dao.repository.AuthorRepository
import com.example.bookstoreapp.model.exception.NotFoundException
import com.example.bookstoreapp.service.AuthorService
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class AuthorServiceTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private AuthorService service
    private AuthorRepository repository

    void setup() {
        repository = Mock()
        service = new AuthorService(repository)
    }

    def "GetAuthorAndDetailsAboutHisBooks test when author is present"() {
        given:
        def id = 1L
        def entity = random.nextObject(AuthorEntity)

        when:
        def result = service.getAuthorAndDetailsAboutHisBooks(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        result != null
    }

    def "GetAuthorAndDetailsAboutHisBooks test when author is not present"() {
        given:
        def id = 1L
        when:
        def result = service.getAuthorAndDetailsAboutHisBooks(id)

        then:
        1 * repository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == String.format("%s with id %s not found", "Author", id)
        ex.code == String.format("%s_NOT_FOUND", "AUTHOR")
    }
}
