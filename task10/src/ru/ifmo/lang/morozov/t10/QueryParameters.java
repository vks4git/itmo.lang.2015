package ru.ifmo.lang.morozov.t10;

import java.awt.*;

/**
 * Created by vks on 26/04/15.
 */
public class QueryParameters {

    private String query;
    private ProcessMode mode;
    private Color color;
    private int[] pixel;

    public QueryParameters(final String query) {
        pixel = new int[2];
        String[] list = query.split("&");
        this.query = list[0].substring(7);
        if (list.length > 1) {
            if (list[1].startsWith("color")) {
                String[] ints = list[1].substring(6).split(",");
                color = new Color(Integer.valueOf(ints[0]), Integer.valueOf(ints[1]),
                        Integer.valueOf(ints[2]), Integer.valueOf(ints[3]));
                mode = ProcessMode.USER_DEFINED_COLOUR;
            } else if (list[1].startsWith("pixel")) {
                String[] ints = list[1].substring(6).split(",");
                pixel[0] = Integer.valueOf(ints[0]);
                pixel[1] = Integer.valueOf(ints[1]);
                mode = ProcessMode.USER_DEFINED_PIXEL;
            }
        } else {
            mode = ProcessMode.DEFAULT;
        }
    }

    public ProcessMode getMode() {
        return mode;
    }

    public final Color getColor() {
        return color;
    }

    public final int[] getPixel() {
        return pixel;
    }

    public final String getAddress() {
        return query;
    }

}
