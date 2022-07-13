package com.example.msbookstorewithautentication.service

import com.example.bookstoreapp.dao.entity.AuthorityEntity
import com.example.bookstoreapp.dao.repository.AuthorityRepository
import com.example.bookstoreapp.dao.repository.UserRepository
import com.example.bookstoreapp.model.dto.UserRegisterDto
import com.example.bookstoreapp.service.UserService
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private UserService userService
    private UserRepository userRepository
    private AuthorityRepository authorityRepository
    private BCryptPasswordEncoder encoder

    def setup() {
        userRepository = Mock()
        authorityRepository = Mock()
        encoder = Mock()
        userService = new UserService(userRepository, authorityRepository, encoder)
    }


    def "RegisterUser test"() {

        given:
        def userDto = random.nextObject(UserRegisterDto.class)
        def authority = "USER"
        def authorityEntity = random.nextObject(AuthorityEntity)
        when:
        userService.registerUser(userDto)

        then:
        1 * authorityRepository.findByAuthority(authority) >> authorityEntity
        1 * userRepository.save(_)
    }
}
