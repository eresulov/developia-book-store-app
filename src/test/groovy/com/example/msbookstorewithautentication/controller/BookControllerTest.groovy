package com.example.msbookstorewithautentication.controller

import com.example.bookstoreapp.controller.BookController
import com.example.bookstoreapp.controller.handler.ErrorHandler
import com.example.bookstoreapp.model.dto.BookDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class BookControllerTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private BookController controller
    private com.example.bookstoreapp.service.BookService service

    private MockMvc mockMvc

    void setup() {
        service = Mock()
        controller = new BookController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandler()).build();
    }



    def "UpdateBook test"() {

        given:
        def bookId = 1L
        def publisherId = 1L
        def dto = random.nextObject(BookDto)
        def endpoint = "/v1/books/" + bookId + "publishers" + publisherId
        def requestJson = '''
                                    {
                                      "genre": "PROGRAMMING",
                                      "name": "bookName",
                                      "pageCount": 150,   +
                                      "publishingYear": 2002
                                    }

                                    '''
        when:
        controller.updateBook(bookId, publisherId, dto)
        def result = mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andReturn()
        then:

        1 * service.updateBook(bookId, publisherId, dto)
        def response = result.response
        response.status == 404
    }


}