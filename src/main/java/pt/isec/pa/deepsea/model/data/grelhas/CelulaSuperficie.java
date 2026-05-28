package pt.isec.pa.deepsea.model.data.grelhas;

import java.io.Serializable;

/**
 * Célula da grelha de superfície. Contém um
 * {@link FossoMarinho} e um {@link FundoMarinho} associados.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
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
