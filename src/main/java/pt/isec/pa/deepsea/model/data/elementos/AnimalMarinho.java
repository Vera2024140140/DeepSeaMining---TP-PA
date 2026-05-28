package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.TipoComponente;

/**
 * Animal marinho no fosso. Causa dano ao drone ao colidir.
 *
 * @author Vera2024140140
 */
public class AnimalMarinho extends Obstaculo{
    private static final long serialVersionUID = 3L;
    public AnimalMarinho(int linha, int coluna){
        super(linha,coluna);
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.ANIMALMARINHO;
    }
}
