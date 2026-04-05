package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class GrelhaSuperficie {

    private final CelulaSuperficie[][] grelha;

    private final int linhas;
    private final int colunas;

    public GrelhaSuperficie(){
        this.linhas = Settings.LINHAS_SUPERFICIE;
        this.colunas = Settings.COLUNAS_SUPERFICIE;
        this.grelha = new CelulaSuperficie[linhas][colunas];

        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                grelha[l][c] = new CelulaSuperficie();
            }
        }

        if (Settings.MODO_DEFESA){
            gerarMundoModoDefesa();
        } else {
            gerarArtefactos();
            gerarMinerios();
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    private void gerarMundoModoDefesa() {
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

    private void gerarArtefactos() {
        //pos0, pos1, pos2, pos3
        //lSup, cSup, lFundo, cFundo
        List<int[]> posicoes = todasPosicoesFundo();

        for (int i = 0; i < Settings.NUM_ARTEFACTOS && !posicoes.isEmpty(); i++) {
            int index = Utilidades.aleatorio(0, posicoes.size() - 1);
            int[] pos = posicoes.remove(index);
            if (!getFundo(pos[0], pos[1]).colocarArtefacto(pos[2], pos[3])){
                i--;
            }
        }
    }

    private void gerarMinerios() {
        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++){
                FundoMarinho fundo = getFundo(l, c);
                int numMinerios = Utilidades.aleatorio(Settings.MINERIO_ZONA_MIN, Settings.MINERIO_ZONA_MAX);
                for (int i = 0; i < numMinerios; i++){
                    int[] pos = posicaoFundoLivre(fundo);
                    if(pos == null){
                        break;
                    }
                    int qtd = Utilidades.aleatorio(Settings.MINERIO_QTD_MIN, Settings.MINERIO_QTD_MAX);
                    if (!fundo.colocarMinerio(pos[0], pos[1], qtd)){
                        i--;
                    }
                }
            }
        }
    }

    public FossoMarinho getFosso(int l, int c) {
        return grelha[l][c].getFosso();
    }

    public FundoMarinho getFundo(int l, int c) {
        return grelha[l][c].getFundo();
    }

    private List<int[]> todasPosicoesFundo() {
        List<int[]> posicoes = new ArrayList<>();
        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++){
                FundoMarinho fundo = getFundo(l, c);
                for (int lf = 0; lf < fundo.getLinhas(); lf++){
                    for (int cf = 0; cf < fundo.getColunas(); cf++){
                        posicoes.add(new int[]{l, c, lf, cf});
                    }
                }
            }
        }
        return posicoes;
    }

    private int[] posicaoFundoLivre(FundoMarinho fundo) {
        List<int[]> livres = fundo.getCelulasLivres();
        if(livres.isEmpty()){
            return null;
        }
        return livres.get(Utilidades.aleatorio(0, livres.size() - 1));
    }
}