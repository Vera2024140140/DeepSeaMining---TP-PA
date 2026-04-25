package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.TipoComponente;

public class Rocha extends Componente{
    public Rocha (int linha, int coluna){
        super(linha,coluna);
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.ROCHA;
    }
}
