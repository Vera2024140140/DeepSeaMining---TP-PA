package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.Utilidades;
public class Monstro extends Componente{
    private int id;
    public Monstro(int linha, int coluna){
        super(linha,coluna);
        id = Utilidades.proximoIDObstaculos();
    }

    public int getId() {
        return id;
    }
}
