package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.TipoComponente;
import java.io.Serializable;

/**
 * Classe abstrata base de todos os elementos que podem
 * existir numa célula (artefactos, minérios, obstáculos, monstros).
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
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
