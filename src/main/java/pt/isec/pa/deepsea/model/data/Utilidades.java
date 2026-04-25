package pt.isec.pa.deepsea.model.data;

import java.util.Random;

public class Utilidades {

    private Utilidades(){}

    private static final Random random = new Random();


    public static int aleatorio (int min, int max){
        if (min >= max) return min;
        return min + random.nextInt(max - min + 1);
    }

    public static double probAleatoria () {
        return  random.nextDouble();
    }
}
