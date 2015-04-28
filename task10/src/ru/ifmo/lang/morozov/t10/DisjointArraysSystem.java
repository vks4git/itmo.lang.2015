package ru.ifmo.lang.morozov.t10;

/**
 * Created by vks on 28/04/15.
 */
public class DisjointArraysSystem {
    private int[] roots;
    private int[] weights;
    private int width;

    public DisjointArraysSystem(int width, int height) {
        this.width = width;
        roots = new int[width * height];
        weights = new int[width * height];

        for (int i = 0; i < width * height; i++) {
            roots[i] = i;
            weights[i] = 1;
        }
    }

    private int root(int p) {
        while (p != roots[p]) {
            p = roots[p];
            roots[p] = roots[roots[p]];
        }
        return p;
    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int x, int y) {
        int p = x + y * width;
        return root(p);
    }

    public void connect(int x1, int y1, int x2, int y2) {
        int p = x1 + y1 * width;
        int q = x2 + y2 * width;
        if (!connected(p, q)) {
            int rootP = root(p);
            int rootQ = root(q);
            if (weights[rootP] > weights[rootQ]) {
                roots[rootQ] = rootP;
                weights[rootP] += weights[rootQ];
            } else {
                roots[rootP] = rootQ;
                weights[rootQ] += weights[rootP];
            }
        }
    }
}
