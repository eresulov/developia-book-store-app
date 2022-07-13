package com.example.msbookstorewithautentication.controller

import com.example.bookstoreapp.controller.PublisherController
import com.example.bookstoreapp.controller.handler.ErrorHandler
import com.example.bookstoreapp.model.dto.BookDto
import com.example.bookstoreapp.model.dto.PublisherAndBooksListDto
import com.example.bookstoreapp.model.exception.NotFoundException
import com.example.bookstoreapp.service.PublisherService
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class PublisherControllerTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private PublisherController controller
    private PublisherService service
    private MockMvc mockMvc


    void setup() {
        service = Mock()
        controller = new PublisherController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandler()).build()
    }

    def "GetBooksByPublisher success case"() {

        given:
        def id = 1L
        def dto = PublisherAndBooksListDto.builder()
                .name("TEST")
                .bookListDto(List.of(BookDto.builder()
                        .name("TEST")
                        .publishingYear(2022)
                        .pageCount(300)
                        .genre(Genre.STORY)
                        .build())).build()

        def endpoint = "/v1/publishers/" + id
        def expectedResponse = '''
                                     {
                                    "name": "TEST",
                                    "bookListDto": [
                                        {
                                            "name": "TEST",
                                            "publishingYear": 2022,
                                            "pageCount": 300,
                                            "genre": "STORY"
                                        }] }  
'''
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        1 * service.getAllBooksByPublisher(id) >> dto
        def response = result.response
        response.status == 200
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }


    def "GetBooksByPublisher fail case"() {

        given:
        def id = 1L
        def dto = PublisherAndBooksListDto.builder()
                .name("TEST")
                .bookListDto(List.of(BookDto.builder()
                        .name("TEST")
                        .publishingYear(2022)
                        .pageCount(300)
                        .genre(Genre.STORY)
                        .build())).build()

        def endpoint = "/v1/publishers/" + id
        def expectedResponse = '''
                                            {   
                                            "message" : "Publisher with id 1 not found",
                                            "code" : "PUBLISHER_NOT_FOUND"
                                                }
'''
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        1 * service.getAllBooksByPublisher(id) >> {
            throw new NotFoundException("Publisher with id 1 not found", "PUBLISHER_NOT_FOUND")
        }
        def response = result.response
        response.status == 200
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }

    def "AddBook"() {


        given:
        def bookDto = random.nextObject(BookDto)
        def id = 1L
        def requestJson = '''
                                    {
                                    "name" : "SomeName",
                                    "publishingYear" :2000,
                                    "pageCount" : 300,
                                    "genre" : "TRAVEL"
                                    }
'''
        def endpoint = "/v1/publishers/" + id

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andReturn()

        then:
        1 * service.addBook(id, bookDto)
        def response = result.response
        response.status == 404
    }
}
