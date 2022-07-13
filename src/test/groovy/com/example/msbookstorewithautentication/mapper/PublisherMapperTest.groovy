package com.example.msbookstorewithautentication.mapper

import com.example.bookstoreapp.dao.entity.PublisherEntity
import com.example.bookstoreapp.mapper.BookMapper
import com.example.bookstoreapp.mapper.PublisherMapper
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import java.util.stream.Collectors

class PublisherMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "MapEntityToPublisherBooksListDto"() {

        given:
        def entity = random.nextObject(PublisherEntity)

        when:
        def result = PublisherMapper.mapEntityToPublisherBooksDto(entity)

        then:
        result.name == entity.name
        result.bookListDto == entity.books.stream()
                .map(bookEntity -> BookMapper.mapEntityToDto(bookEntity))
                .collect(Collectors.toList())
    }
}
