package pt.isec.pa.deepsea.model.data.grelhas;

public class CelulaSuperficie {

    private final FossoMarinho fosso;
    private final FundoMarinho fundo;

    public CelulaSuperficie(){
        this.fosso = new FossoMarinho();
        this.fundo = new FundoMarinho();
    }

    public FossoMarinho getFosso(){
        return fosso;
    }

    public FundoMarinho getFundo(){
        return fundo;
    }
}
