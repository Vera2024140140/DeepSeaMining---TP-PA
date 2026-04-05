package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.Utilidades;
public class Obstaculo extends Componente{
    private int id;

    public Obstaculo(int linha, int coluna){
        super(linha,coluna);
        this.id = Utilidades.proximoIDObstaculos();
    }

    public int getId() {
        return id;
    }
}
