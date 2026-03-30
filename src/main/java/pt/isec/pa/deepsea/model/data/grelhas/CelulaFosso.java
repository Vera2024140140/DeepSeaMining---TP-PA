package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.elementos.AnimalMarinho;
import pt.isec.pa.deepsea.model.data.elementos.Corrente;
import pt.isec.pa.deepsea.model.data.elementos.Rocha;

public class CelulaFosso extends Celula{

    public CelulaFosso(){
        super();
    }

    public boolean temRocha(){
        return getComponente() instanceof Rocha;
    }

    public boolean temCorrente(){
        return getComponente() instanceof Corrente;
    }

    public boolean temAnimalMarinho(){
        return getComponente() instanceof AnimalMarinho;
    }
}
