package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.Settings;
public abstract class Obstaculo extends Componente{
    private static int contadorID = Settings.ID_OBSTACULO_INICIAL;
    private final int id;

    public Obstaculo(int linha, int coluna){
        super(linha,coluna);
        this.id = contadorID;
        contadorID++;

    }

    public int getId() {
        return id;
    }
    public static void resetContadorObstaculos() {
        contadorID = Settings.ID_OBSTACULO_INICIAL;
    }
}
