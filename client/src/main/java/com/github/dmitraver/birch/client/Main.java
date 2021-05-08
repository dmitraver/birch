package com.github.dmitraver.birch.client;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 9999;

        try(Socket socket = new Socket(host, port)) {
            PrintWriter writer  = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));

            String line;
            while((line = stdInReader.readLine()) != null) {
                writer.println(line);

                String serverReply = reader.readLine();
                System.out.println("Server: " + serverReply);

                if (line.equals("QUIT")) {
                    System.out.println("Disconnected...");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Opps, exception: " + e.getMessage());
        }
    }
}
