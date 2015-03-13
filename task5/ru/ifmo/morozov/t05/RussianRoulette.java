package ru.ifmo.morozov.t05;

/**
 * Created by vks on 3/18/15.
 */
public interface RussianRoulette {

    /**
     * Начать игру (зарядить пистолет, выбрать цель, нажать на крючок)
     * @param gun Пистолет, которым будем играть
     */
    void play(Gun gun);

    /**
     * Пистолет для игры в русскую рулетку
     */
    interface Gun {
        /**
         * Выстрелить из пистолета
         * @return {@code true} - если пистолет выстрелил, иначе {@code false}
         */
        boolean fire();
    }

}
