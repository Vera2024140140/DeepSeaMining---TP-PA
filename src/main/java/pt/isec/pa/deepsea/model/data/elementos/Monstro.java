package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
public class Monstro extends Componente{
    private static int contadorID = Settings.ID_MONSTRO_INICIAL;
    private final int id;
    public Monstro(int linha, int coluna){
        super(linha,coluna);
        id = contadorID;
        contadorID++;
    }

    public int getId() {
        return id;
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.MONSTRO;
    }
    public static void resetContadorMonstros() {
        contadorID = Settings.ID_OBSTACULO_INICIAL;
    }
}
