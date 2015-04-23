package ru.ifmo.lang.morozov.t08.gun;

/**
 * Created by vks on 3/18/15.
 */
public interface Gun {

    boolean fire();

    default void reload(int bullets) {

    }
}
