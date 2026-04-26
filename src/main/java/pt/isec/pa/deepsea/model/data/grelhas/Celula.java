package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.elementos.Componente;

import java.io.Serializable;

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
