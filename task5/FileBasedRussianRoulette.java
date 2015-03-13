import ru.ifmo.morozov.t05.ColtMk3TrooperLawman;
import ru.ifmo.morozov.t05.RussianRoulette;
import ru.ifmo.morozov.t05.UnholyHellGun;

import java.util.Calendar;


/**
 * Created by vks on 3/18/15.
 */
public class FileBasedRussianRoulette implements RussianRoulette {

    public static void main(String[] args) {
        Gun gun;

        String path = args[0];
        if (args[1].equals("bonus")) {
            gun = new UnholyHellGun(path);
        } else {
            int bullets = (args[1].charAt(0) - '0');
            gun = new ColtMk3TrooperLawman(path, bullets);
        }

        RussianRoulette game = new FileBasedRussianRoulette();
        game.play(gun);
    }

    public void play(Gun gun) {
        gun.fire();
    }
}
