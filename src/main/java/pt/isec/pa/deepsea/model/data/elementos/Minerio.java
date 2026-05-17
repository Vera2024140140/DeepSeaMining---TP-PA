package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.TipoComponente;

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
