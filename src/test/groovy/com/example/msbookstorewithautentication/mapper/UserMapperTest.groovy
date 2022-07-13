package com.example.msbookstorewithautentication.mapper

import com.example.bookstoreapp.mapper.UserMapper
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class UserMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "MapRegisterDtoToEntity"() {
        given:
        def dto = random.nextObject(UserRegisterDto)

        when:
        def entity = UserMapper.mapRegisterDtoToEntity(dto);

        then:
        dto.firstName == entity.firstName
        dto.lastName == entity.lastName
        dto.age == entity.age
        dto.username == entity.username
        dto.password == entity.password
        dto.email == entity.email
        dto.gender == entity.gender
    }
}
