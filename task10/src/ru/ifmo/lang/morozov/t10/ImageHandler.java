package ru.ifmo.lang.morozov.t10;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * Created by vks on 26/04/15.
 */
class ImageHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        String path = System.getProperty("user.dir") + exchange.getRequestURI();
        File imgFile = Paths.get(path).toFile();
        try {
            BufferedImage image = ImageIO.read(imgFile);
            exchange.sendResponseHeaders(200, 0);
            OutputStream ostream = exchange.getResponseBody();
            ImageIO.write(image, "png", ostream);
            ostream.close();
            exchange.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}