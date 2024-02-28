package com.bookshop.orderservice.clients;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.bookshop.orderservice.dtos.Book;
import com.bookshop.orderservice.webClients.BookClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BookClientTests {
    private MockWebServer mockWebServer;
    private BookClient bookClient;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        var webClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
        this.bookClient = new BookClient(webClient);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void whenBookExistsThenReturnBook() {

        var isbn = "1234567890";

        // Define the response to be returned by the mock server
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                        "isbn": %s,
                        "title": "Title",
                        "author": "Author",
                        "price": 9.90,
                        "publisher": "Moran"
                        }
                        """.formatted(isbn));

        // Add response to the queue to be processed by the mock server
        mockWebServer.enqueue(mockResponse);

        Mono<Book> book = bookClient.getBookByIsbn(isbn);

        // Initializes a StepVerifier object with the object returned by BookClient
        StepVerifier.create(book).expectNextMatches(
                // Assert that the Book returned has the ISBN requested
                b -> b.isbn().equals(isbn))

                // Verify that the reactive stream completed successfully
                .verifyComplete();
    }
}