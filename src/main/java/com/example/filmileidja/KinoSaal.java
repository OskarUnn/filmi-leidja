package com.example.filmileidja;

import lombok.Data;

import java.util.Arrays;
import java.util.Random;

@Data
public class KinoSaal {
    private static int ridadeArv = 12;
    private static int istmeidReas = 20;

    public static boolean[][] genereeriSaal() {
        boolean[][] saal = new boolean[ridadeArv][istmeidReas];
        int hõivatudIstmeid = randRange(istmeidReas/2, istmeidKokku()/4);

        for (int i = 0; i < hõivatudIstmeid; i++) {
            int suvalineRida;
            int suvalineIste;
            do {
                suvalineRida = randRange(0, ridadeArv-1);
                suvalineIste = randRange(0, istmeidReas-1);
            } while (saal[suvalineRida][suvalineIste]);

            saal[suvalineRida][suvalineIste] = true;
        }

        return saal;
    }

    public static int istmeidKokku() {
        return ridadeArv * istmeidReas;
    }

    private static int randRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
