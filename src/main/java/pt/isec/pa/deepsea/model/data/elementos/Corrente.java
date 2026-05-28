package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.TipoComponente;

/**
 * Corrente marinha no fosso. Causa dano ao drone ao colidir.
 *
 * @author Vera2024140140
 */
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
