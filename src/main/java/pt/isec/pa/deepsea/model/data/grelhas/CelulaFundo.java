package pt.isec.pa.deepsea.model.data.grelhas;

public class CelulaFundo extends Celula{

    private boolean revelada;

    public CelulaFundo(){
        super();
        this.revelada = false;
    }

    public boolean isRevelada(){
        return revelada;
    }

    public void revelar(){
        this.revelada = true;
    }

    void reset(){
        setComponente(null);
        this.revelada = false;
    }
}
