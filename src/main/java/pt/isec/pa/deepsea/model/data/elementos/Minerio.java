package pt.isec.pa.deepsea.model.data.elementos;

public class Minerio extends Componente{
    private int qtd;

    public Minerio(int linha, int coluna, int qtd){
        super(linha, coluna);
        this.qtd = qtd;
    }
    public int getQtd(){
        return qtd;
    }
}
