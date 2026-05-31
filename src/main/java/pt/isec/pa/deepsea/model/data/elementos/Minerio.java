package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.TipoComponente;

/**
 * Recurso que pode ser recolhido no fundo, marinho.
 * Contém uma quantidade variável de minério.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 */
public class Minerio extends Componente{
    private static final long serialVersionUID = 7L;
    private final int qtd;

    public Minerio(int linha, int coluna, int qtd){
        super(linha, coluna);
        this.qtd = qtd;
    }
    public int getQtd(){
        return qtd;
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.MINERIO;
    }
}
