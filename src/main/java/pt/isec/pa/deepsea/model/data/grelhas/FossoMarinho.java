package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Componente;
import pt.isec.pa.deepsea.model.data.elementos.Rocha;

public class FossoMarinho {

    private final CelulaFosso[][] grelha;

    private final int linhas;
    private final int colunas;

    public FossoMarinho() {
        this.linhas = Settings.LINHAS_FOSSO;
        this.colunas = Settings.COLUNAS_FOSSO;
        this.grelha = new CelulaFosso[linhas][colunas];

        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++) {
                grelha[l][c] = new CelulaFosso();
            }
        }
        gerarRochas();
    }

    private void gerarRochas() {
        if(Settings.MODO_DEFESA){
            for (int l = 0; l < linhas; l++) {
                for (int c = 0; c < Settings.DEFESA_NUM_ROCHAS_LADO; c++)
                    grelha[l][c].setComponente(new Rocha(l, c));
                for (int c = 0; c < Settings.DEFESA_NUM_ROCHAS_LADO; c++) {
                    int col = colunas - 1 - c;
                    grelha[l][col].setComponente(new Rocha(l, col));
                }
            }
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    private boolean dentroLimites(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public Componente getComponente(int linha, int coluna) {
        if (dentroLimites(linha, coluna)) {
            return grelha[linha][coluna].getComponente();
        }
        return null;
    }

}