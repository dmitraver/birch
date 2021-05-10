package com.github.dmitraver.birch.protocol;

import com.github.dmitraver.birch.protocol.requests.GetRequest;
import com.github.dmitraver.birch.protocol.requests.PutRequest;
import com.github.dmitraver.birch.protocol.requests.QuitRequest;
import com.github.dmitraver.birch.protocol.requests.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    private RequestParser parser = new RequestParser();

    @Test
    public void parse_empty_or_null_request_throws_parsing_exception() {
        assertThrows(RequestParsingException.class, () -> {
           parser.parse(null);
        });

        assertThrows(RequestParsingException.class, () -> {
            parser.parse("");
        });
    }

    @Test
    public void parse_put_request() throws RequestParsingException {
        assertThrows(RequestParsingException.class, () -> {
            parser.parse("PUT");
        });

        assertThrows(RequestParsingException.class, () -> {
            parser.parse("PUT key value something");
        });

        Request request = parser.parse("PUT key value");
        assertTrue(request instanceof PutRequest);

        PutRequest putRequest = (PutRequest) request;

        assertEquals(putRequest.getKey(), "key");
        assertEquals(putRequest.getValue(), "value");
    }

    @Test
    public void parse_get_request() throws RequestParsingException {
        assertThrows(RequestParsingException.class, () -> {
            parser.parse("GET");
        });

        assertThrows(RequestParsingException.class, () -> {
            parser.parse("GET key1 key2");
        });

        Request request = parser.parse("GET key");
        assertTrue(request instanceof GetRequest);

        GetRequest getRequest = (GetRequest) request;

        assertEquals(getRequest.getKey(), "key");
    }

    @Test
    public void parse_quit_request() throws RequestParsingException {
        assertThrows(RequestParsingException.class, () -> {
            parser.parse("QUIT key1");
        });

        Request request = parser.parse("QUIT");
        assertTrue(request instanceof QuitRequest);
    }
}