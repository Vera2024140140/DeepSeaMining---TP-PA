package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.elementos.Componente;

public abstract class Celula {
    private Componente componente;

    public Celula(){
        this.componente = null;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public boolean isEmpty(){
        return componente == null;
    }


}
