package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.TipoComponente;

public class Corrente extends Obstaculo{
    private static final long serialVersionUID = 4L;
    public Corrente (int linha, int coluna){
        super(linha,coluna);
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.CORRENTE;
    }
}
