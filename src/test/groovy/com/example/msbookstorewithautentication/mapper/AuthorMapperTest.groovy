package com.example.msbookstorewithautentication.mapper

import com.example.bookstoreapp.dao.entity.AuthorEntity
import com.example.bookstoreapp.mapper.AuthorMapper
import com.example.bookstoreapp.mapper.BookMapper
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class AuthorMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "mapAuthorBooksEntitiesToListDto test"() {

        given:
        def entity = random.nextObject(AuthorEntity)

        when:
        def result = AuthorMapper.mapAuthorBooksEntitiesToDtos(entity)

        then:
        result.firstName == entity.firstName
        result.lastName == entity.lastName
        result.birthDay == entity.birthDay
        result.authorBooksDto == BookMapper.mapEntitiesToListDto(entity.books)


    }
}
