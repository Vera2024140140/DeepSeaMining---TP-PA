package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.elementos.Componente;

import java.io.Serializable;

/**
 * Classe abstrata que representa uma célula numa grelha.
 * Pode conter no máximo um {@link Componente}.
 * Subclasses: {@link CelulaFundo} e {@link CelulaFosso}.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public abstract class Celula implements Serializable {
    private static final long serialVersionUID = 10L;
    private Componente componente;

    public Celula(){
        this.componente = null;
    }

    Componente getComponente() {
        return componente;
    }

    void setComponente(Componente componente) {
        this.componente = componente;
    }

    boolean isEmpty(){
        return componente == null;
    }


}
