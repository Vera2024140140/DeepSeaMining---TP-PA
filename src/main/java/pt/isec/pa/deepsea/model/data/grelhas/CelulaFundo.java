package pt.isec.pa.deepsea.model.data.grelhas;

/**
 * Célula do fundo marinho. Estende {@link Celula}
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class CelulaFundo extends Celula{
    private static final long serialVersionUID = 12L;
    private boolean revelada;

    public CelulaFundo(){
        super();
        this.revelada = false;
    }

    boolean isRevelada(){
        return revelada;
    }

    void revelar(){
        this.revelada = true;
    }

    void esconder(){
        this.revelada = false;
    }
}
