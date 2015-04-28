package ru.ifmo.lang.morozov.t10;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by vks on 26/04/15.
 */
class RequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();
        OutputStream ostream = exchange.getResponseBody();
        try {
            if (query.startsWith("source=")) {
                QueryParameters parameters = new QueryParameters(query);
                URL data = new URL(parameters.getAddress());
                URLConnection connection = data.openConnection();
                BufferedImage image = ImageIO.read(connection.getInputStream());
                BufferedImage pngImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                pngImage.createGraphics().drawImage(image, 0, 0, null);
                File imgFile = Paths.get("img.png").toFile();
                ImageIO.write(clearBackground(pngImage, parameters), "PNG", imgFile);
                exchange.sendResponseHeaders(200, 0);
                ostream.write(buildOkResponse(pngImage.getWidth(), pngImage.getHeight(), "img.png").getBytes());
                ostream.close();
                exchange.close();
            } else {
                exchange.sendResponseHeaders(418, 0);
                ostream.write(buildErrorResponse().getBytes());
                ostream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage clearBackground(BufferedImage image, QueryParameters parameters) {
        switch (parameters.getMode()) {
            case USER_DEFINED_COLOUR:
                return clearColor(image, parameters.getColor());
            case USER_DEFINED_PIXEL:
                return clearPixels(image, parameters.getPixel());
            default:
                return defaultProcess(image);
        }
    }

    private BufferedImage clearColor(BufferedImage image, Color color) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) == color.getRGB()) {
                    image.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }
        return image;
    }

    private BufferedImage clearPixels(BufferedImage image, int[] pixel) {
        if ((pixel[0] <= image.getWidth()) && (pixel[0] > 0)) {
            if ((pixel[1] <= image.getHeight()) && (pixel[1] > 0)) {
                Color color = new Color(image.getRGB(pixel[0] - 1, pixel[1] - 1));
                return clearColor(image, color);
            }
        }
        return image;
    }

    private BufferedImage defaultProcess(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double min = 765;
        double max = 0;

        /*
        Вычисляется разброс цветов в изображении. Чем он больше, тем выше будет порог, при котором два
        соседних пикселя будут считаться одной частью изображения.
         */

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(image.getRGB(i, j));
                int sum = color.getRed() + color.getGreen() + color.getBlue();
                if (sum > max) {
                    max = sum;
                }
                if (sum < min) {
                    min = sum;
                }
            }
        }

        int threshold = (int) (6 * (max - min) / 765);

        /*
        Строится система непересекающихся множеств, затем определяются все пиксели, находящиеся в одном
        множестве с крайними. Предполагается, что объект находится в центре изображения, всё, что вокруг него -- фон.
         */

        DisjointArraysSystem das = new DisjointArraysSystem(width, height);
        for (int i = 0; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {

                        if ((i + k >= 0) && (i + k < width) && (j + l >= 0) && (j + l < height)) {
                            if (areSimilar(image.getRGB(i, j), image.getRGB(i + k, j + l), threshold)) {
                                das.connect(i, j, i + k, j + l);
                            }
                        }

                    }
                }

            }
        }

        int[] roots = new int[width + height << 2];
        for (int i = 0; i < width; i++) {
            roots[i] = das.root(i, 0);
        }

        for (int i = 0; i < height; i++) {
            roots[i + width] = das.root(0, i);
            roots[i + width + height] = das.root(width - 1, i);
        }

        Arrays.sort(roots);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Arrays.binarySearch(roots, das.root(i, j)) > 0) {
                    image.setRGB(i, j, 0);
                }
            }
        }

        return image;
    }

    /*
    Функция определяет, являются ли два пикселя одной частью картинки.
     */
    private boolean areSimilar(int pixel1, int pixel2, int threshold) {
        Color color1 = new Color(pixel1);
        Color color2 = new Color(pixel2);
        return ((Math.abs(color1.getRed() - color2.getRed()) <= threshold) &&
                (Math.abs(color1.getGreen() - color2.getGreen()) <= threshold) &&
                (Math.abs(color1.getBlue() - color2.getBlue()) <= threshold));
    }

    private String buildErrorResponse() {
        return "<!DOCTYPE HTML> \n" +
                "<html> \n" +
                "<body background=\"http://weblabfon.com/_ld/0/50_07.jpg\"> \n" +
                "<center>418 I'm a teapot. Check your query.</center> \n" +
                "<hr><p align=\"right\">Cool image background cleaning server</p></hr>\n" +
                "</body> \n" +
                "</html>";
    }

    public String buildOkResponse(int width, int height, String name) {
        return "<!DOCTYPE HTML> \n" +
                "<html> \n" +
                "<body background=\"http://weblabfon.com/_ld/0/50_07.jpg\"> \n" +
                "<center>200 Ok. Done.</center> \n" +
                "<center><img src=\"" + name + "\" width=\"" + width + "\" height=\"" + height + "\"></center>\n" +
                "<br> \n" +
                "<hr><p align=\"right\">Cool image background cleaning server</p></hr>\n" +
                "</body> \n" +
                "</html>";
    }

}
