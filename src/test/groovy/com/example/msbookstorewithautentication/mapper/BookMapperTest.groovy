package com.example.msbookstorewithautentication.mapper

import com.example.bookstoreapp.dao.entity.BookEntity
import com.example.bookstoreapp.mapper.BookMapper
import io.github.benas.randombeans.EnhancedRandomBuilder

import spock.lang.Specification

class BookMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "MapEntityToDto test"() {
        given:
        def entity = random.nextObject(BookEntity)

        when:
        def result = BookMapper.mapEntityToDto(entity);

        then:
        result.name == entity.name
        result.publishingYear == entity.publishingYear
        result.pageCount == entity.pageCount
        result.genre == entity.genre
    }
    def "MapEntitiesToListDto test"() {
        given:
        def entities = random.nextObject(List<BookEntity>)

        when:
        def result = BookMapper.mapEntitiesToListDto(entities)

        then:
        for (int i = 0; i < result.size(); i++) {
            result.get(i).name == entities.get(i).name
            result.get(i).pageCount == entities.get(i).pageCount()
            result.get(i).publishingYear == entities.get(i).publishingYear
            result.get(i).genre == entities.get(i).publishingYear
        }


    }

    def "MapDtoToEntity test"() {
        given:
        def dto = random.nextObject(BookDto)

        when:
        def entity = BookMapper.mapDtoToEntity(dto)

        then:
        dto.name == entity.name
        dto.publishingYear == entity.publishingYear
        dto.pageCount == entity.pageCount
        dto.genre == entity.genre

    }
}
