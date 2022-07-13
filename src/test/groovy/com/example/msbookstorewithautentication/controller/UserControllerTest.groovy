package com.example.msbookstorewithautentication.controller

import com.example.bookstoreapp.controller.UserController
import com.example.bookstoreapp.controller.handler.ErrorHandler
import com.example.bookstoreapp.model.dto.UserRegisterDto
import com.example.bookstoreapp.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserControllerTest extends Specification {


    private MockMvc mockMvc
    private UserController controller
    private UserService service

    void setup() {
        service = Mock()
        controller = new UserController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandler()).build()
    }

    def "RegisterUser test"() {

        given:
        def userRegisterDto = UserRegisterDto.builder()
                .firstName("Somebody")
                .lastName("Someone")
                .username("test1337")
                .password("test1337")
                .age(20)
                .email("test1337@gmail.com")
                .build()

        def requestJson = '''
                   {
                        "firstname": "Somebody",
                        "lastname: "Someone",
                        "username": "test1337",
                        "password": "test1337",
                        "age" : 20,
                        "email" :"test1337@gmail.com"
                       }
                '''
        def endpoint = "/v1/users"

        when:
        def result = mockMvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn()

        then:
        1 * service.registerUser(userRegisterDto)
        def response = result.response
        response.status == 201
    }


}
