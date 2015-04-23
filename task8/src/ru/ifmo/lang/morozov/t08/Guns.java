package ru.ifmo.lang.morozov.t08;

/**
 * Created by vks on 23/04/15.
 */
public enum Guns {
    BORING {
        @Override
        public String toString() {
            return "The boring one";
        }
    }, BONUS {
        @Override
        public String toString() {
            return "The bonus one";
        }
    }
}
