package com.github.dmitraver.birch.protocol;

import com.github.dmitraver.birch.protocol.requests.GetRequest;
import com.github.dmitraver.birch.protocol.requests.PutRequest;
import com.github.dmitraver.birch.protocol.requests.QuitRequest;
import com.github.dmitraver.birch.protocol.requests.Request;

import java.util.Optional;

public final class RequestParser {

    // TODO: differentiate between different part of errors like unknown commands, wrong number of params etc...
    public Request parse(String requestStr) throws RequestParsingException {
        if (requestStr == null || requestStr.isEmpty()) {
            throw new RequestParsingException("Can't parse empty or null request");
        }

        String[] args = requestStr.split(" ");
        String requestType = args[0];
        if (requestType.equals(Requests.GET) && args.length == 2) {
            return new GetRequest(args[1]);
        } else if (requestType.equals(Requests.PUT) && args.length == 3) {
            return new PutRequest(args[1], args[2]);
        } else if (requestType.equals(Requests.QUIT) && args.length == 1) {
            return new QuitRequest();
        } else {
            throw new RequestParsingException("Can't parse request, unknown request type or wrong number of params");
        }
    }
}

