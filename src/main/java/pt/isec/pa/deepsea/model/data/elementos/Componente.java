package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.TipoComponente;

public abstract class Componente {
    private final int linha;
    private final int coluna;

    public Componente (int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }
    public int getColuna(){
        return coluna;
    }
    public abstract TipoComponente getTipo();
}
