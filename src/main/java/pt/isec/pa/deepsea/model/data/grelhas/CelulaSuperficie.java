package pt.isec.pa.deepsea.model.data.grelhas;

import java.io.Serializable;

public class CelulaSuperficie implements Serializable{
    private static final long serialVersionUID = 13L;
    private final FossoMarinho fosso;
    private final FundoMarinho fundo;

    public CelulaSuperficie(){
        this.fosso = new FossoMarinho();
        this.fundo = new FundoMarinho();
    }

    FossoMarinho getFosso(){
        return fosso;
    }

    FundoMarinho getFundo(){
        return fundo;
    }
}
