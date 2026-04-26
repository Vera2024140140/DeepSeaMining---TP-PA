package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import java.io.Serializable;

public abstract class Componente implements Serializable {
    private static final long serialVersionUID = 1L;
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
