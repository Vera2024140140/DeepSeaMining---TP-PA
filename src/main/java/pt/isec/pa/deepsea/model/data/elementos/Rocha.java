package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.TipoComponente;

public class Rocha extends Componente{
    private static final long serialVersionUID = 8L;
    public Rocha (int linha, int coluna){
        super(linha,coluna);
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.ROCHA;
    }
}
