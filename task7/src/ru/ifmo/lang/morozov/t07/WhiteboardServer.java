package ru.ifmo.lang.morozov.t07;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Created by vks on 22/04/15.
 */
public class WhiteboardServer {

    private static String message = "lorem ipsum";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/post", new PostHandler());
            server.createContext("/get", new GetHandler());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class GetHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, 0);
            OutputStream ostream = exchange.getResponseBody();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ostream));
            writer.write(message);
            writer.close();
            exchange.close();
        }
    }

    private static class PostHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            URI uri = exchange.getRequestURI();
            OutputStream ostream = exchange.getResponseBody();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ostream));
            String query = uri.getQuery();
            if (query.startsWith("message")) {
                message = query.substring(8);
                exchange.sendResponseHeaders(200, 0);
                writer.write("200 Ok. Message changed.");
            } else {
                exchange.sendResponseHeaders(418, 0);
                writer.write("418 I'm a teapot.");
            }
            writer.close();
            exchange.close();
        }
    }
}
