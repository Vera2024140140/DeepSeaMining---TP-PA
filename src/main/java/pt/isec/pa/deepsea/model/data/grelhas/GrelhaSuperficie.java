package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;

import java.util.List;

public class GrelhaSuperficie {

    private CelulaSuperficie[][] grelha;

    private final int linhas;
    private final int colunas;

    public GrelhaSuperficie(){
        this.linhas = Settings.LINHAS_SUPERFICIE;
        this.colunas = Settings.COLUNAS_SUPERFICIE;
        this.grelha = new CelulaSuperficie[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grelha[i][j] = new CelulaSuperficie();
            }
        }

        if (Settings.MODO_DEFESA){
            gerarMundoModoDefesa();
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    private void gerarMundoModoDefesa(){
        int centroL = linhas / 2;
        int centroC = colunas / 2;

        //centro esquerda
        int lSup1 = centroL;
        int cSup1 = centroC - 1;

        //centro direita
        int lSup2 = centroL;
        int cSup2 = centroC + 1;

        FundoMarinho fundo1 = getFundo(lSup1, cSup1);
        FundoMarinho fundo2 = getFundo(lSup2, cSup2);

        for (int i = 0; i < Settings.DEFESA_NUM_ARTEFACTOS_LADO; i++) {
            int[] pos = posicaoFundoLivre(fundo1);
            if (pos != null) {
                fundo1.colocarArtefacto(pos[0], pos[1]);
            }
        }

        for (int i = 0; i < Settings.DEFESA_NUM_ARTEFACTOS_LADO; i++) {
            int[] pos = posicaoFundoLivre(fundo2);
            if (pos != null) {
                fundo2.colocarArtefacto(pos[0], pos[1]);
            }
        }
    }

    public FossoMarinho getFosso(int l, int c) {
        return grelha[l][c].getFosso();
    }

    public FundoMarinho getFundo(int l, int c) {
        return grelha[l][c].getFundo();
    }


    private int[] posicaoFundoLivre(FundoMarinho fundo) {
        List<int[]> livres = fundo.getCelulasLivres();
        if(livres.isEmpty()){
            return null;
        }
        return livres.get(Utilidades.aleatorio(0, livres.size() - 1));
    }
}
