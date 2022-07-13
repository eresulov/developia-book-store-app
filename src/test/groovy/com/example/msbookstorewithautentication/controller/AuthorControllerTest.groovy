package com.example.msbookstorewithautentication.controller

import com.example.bookstoreapp.controller.AuthorController
import com.example.bookstoreapp.controller.handler.ErrorHandler
import com.example.bookstoreapp.model.dto.AuthorAndBookDetailsDto
import com.example.bookstoreapp.model.dto.BookDto
import com.example.bookstoreapp.model.exception.NotFoundException
import com.example.bookstoreapp.service.AuthorService
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDate

class AuthorControllerTest extends Specification {


    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private AuthorController controller
    private AuthorService service
    private MockMvc mockMvc

    void setup() {
        service = Mock()
        controller = new AuthorController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandler()).build();
    }

    def "GetAuthorAndDetailsAboutHisBooks test success case"() {

        given:
        def id = 1L
        def dto = AuthorAndBookDetailsDto.builder()
                .firstName("Test")
                .lastName("Test")
                .birthDay(LocalDate.of(2022, 04, 21))
                .authorBooksDto(List.of(BookDto.builder()
                        .name("TestBook")
                        .publishingYear(2004)
                        .pageCount(300)
                        .build())).build();
        def endpoint = "/v1/authors/" + id
        def expectedResponse = '''
                                         {"firstName":"Test",
                                          "lastName":"Test",
                                          "birthDay":[2022,4,21],
                                             "authorBooksDto":
                                         [{"name":"TestBook",
                                         "publishingYear":2004,
                                         "pageCount":300}]}
                                    '''
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()


        then:
        1 * service.getAuthorAndDetailsAboutHisBooks(id) >> dto
        def response = result.response
        response.status == 200
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }

    def "GetAuthorAndDetailsAboutHisBooks test fail case"() {

        given:
        def id = 1L
        def dto = AuthorAndBookDetailsDto.builder()
                .firstName("Test")
                .lastName("Test")
                .birthDay(LocalDate.of(2011, 1, 1))
                .authorBooksDto(List.of(BookDto.builder()
                        .name("TestBook")
                        .publishingYear(2004)
                        .pageCount(300)
                        .build())).build();
        def endpoint = "/v1/authors/" + id
        def expectedResponse = '''
                                          {
                                          
                                          "message" : "Author with id 1 not found",
                                          "code" : "AUTHOR_NOT_FOUND"

                                            }
                                    
                                    '''
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()


        then:
        1 * service.getAuthorAndDetailsAboutHisBooks(id) >> {
            throw new NotFoundException("Author with id 1 not found", "AUTHOR_NOT_FOUND")
            def response = result.response
            response.status == 404
            JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
        }
    }
}
