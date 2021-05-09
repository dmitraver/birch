package com.github.dmitraver.birch.protocol;

import com.github.dmitraver.birch.protocol.requests.GetRequest;
import com.github.dmitraver.birch.protocol.requests.PutRequest;
import com.github.dmitraver.birch.protocol.requests.QuitRequest;
import com.github.dmitraver.birch.protocol.requests.Request;

import java.util.Optional;

public final class RequestParser {

    // TODO: differentiate between different part of errors like unknown commands, wrong number of params etc...
    public Optional<Request> parse(String requestStr) {
        if (requestStr == null || requestStr.isEmpty()) {
            return Optional.empty();
        }

        String[] args = requestStr.split(" ");
        String requestType = args[0];
        if (requestType.equals(Requests.GET) && args.length == 2) {
            return Optional.of(new GetRequest(args[1]));
        } else if (requestType.equals(Requests.PUT) && args.length == 3) {
            return Optional.of(new PutRequest(args[1], args[2]));
        } else if (requestType.equals(Requests.QUIT)) {
            return Optional.of(new QuitRequest());
        } else {
            return Optional.empty();
        }
    }
}

