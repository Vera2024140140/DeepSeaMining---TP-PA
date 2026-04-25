package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.TipoComponente;

public class AnimalMarinho extends Obstaculo{

    public AnimalMarinho(int linha, int coluna){
        super(linha,coluna);
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.ANIMALMARINHO;
    }
}
