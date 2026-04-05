package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.Utilidades;

public class Artefacto extends Componente{
    private int id;
    public Artefacto(int linha, int coluna){
        super(linha,coluna);
        this.id = Utilidades.proximoIDArtefacto();
    }

    public int getId() {
        return id;
    }
}
