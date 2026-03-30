package pt.isec.pa.deepsea.model.data;

import java.util.Random;

public class Utilidades {

    private Utilidades(){};

    private static final Random random = new Random();

    private static int contadorID = Settings.ID_OBSTACULO_INICIAL;
    private static int contadorIDArtefactos = Settings.ID_ARTEFACTOS_INICIAL;

    public static int proximoIDObstaculos(){
        return contadorID++;
    }
    public static int proximoIDArtefacto(){
        return contadorIDArtefactos++;
    }
    public static void reiniciarContadores(){
        contadorID = Settings.ID_OBSTACULO_INICIAL;
        contadorIDArtefactos = Settings.ID_ARTEFACTOS_INICIAL;
    }

    public static int aleatorio (int min, int max){
        if (min >= max) return min;
        return min + random.nextInt(max - min + 1);
    }

    public static Random getRandom() {
        return random;
    }
    public static double probAleatoria () {
        return  random.nextDouble();
    }
}
