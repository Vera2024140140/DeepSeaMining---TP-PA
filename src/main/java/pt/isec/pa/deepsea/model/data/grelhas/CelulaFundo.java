package pt.isec.pa.deepsea.model.data.grelhas;

public class CelulaFundo extends Celula{

    private boolean revelada;

    public CelulaFundo(){
        super();
        this.revelada = false;
    }

    boolean isRevelada(){
        return revelada;
    }

    void revelar(){
        this.revelada = true;
    }
}
