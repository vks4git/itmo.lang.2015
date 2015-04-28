package ru.ifmo.lang.morozov.t10;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

/**
 * Created by vks on 25/04/15.
 */
public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/clear_background", new RequestHandler());
            server.createContext("/img.png", new ImageHandler());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
