package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.elementos.Minerio;

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
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public TipoComponente getTipoNoFundo(int lSup, int cSup, int lFundo, int cFundo) {
        return getFundo(lSup, cSup).getTipoComponente(lFundo, cFundo);
    }

    public TipoComponente getTipoNoFosso(int lSup, int cSup, int lFosso, int cFosso) {
        return getFosso(lSup, cSup).getTipoComponente(lFosso, cFosso);
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

    FossoMarinho getFosso(int l, int c) {
        return grelha[l][c].getFosso();
    }

    FundoMarinho getFundo(int l, int c) {
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

    public int getLinhasFundo(int l, int c) {
        return getFundo(l, c).getLinhas();
    }

    public int getColunasFundo(int l, int c) {
        return getFundo(l, c).getColunas();
    }

    public int getLinhasFosso(int l, int c) {
        return getFosso(l, c).getLinhas();
    }

    public int getColunasFosso(int l, int c) {
        return getFosso(l, c).getColunas();
    }

    //===================================================================
    // --- metodos facade (fosso) ---
    //===================================================================

    public boolean fossoTemRocha(int lSup, int cSup, int lF, int cF) {
        return grelha[lSup][cSup].getFosso().celulaTemRocha(lF, cF);
    }
    public boolean fossoTemAnimal(int lSup, int cSup, int lF, int cF) {
        return grelha[lSup][cSup].getFosso().celulaTemAnimal(lF, cF);
    }
    public boolean fossoTemCorrente(int lSup, int cSup, int lF, int cF) {
        return grelha[lSup][cSup].getFosso().celulaTemCorrente(lF, cF);
    }

    //===================================================================
    // --- metodos facade (fundo) ---
    //===================================================================
    // --- FACHADA FUNDO (adicionar à GrelhaSuperficie) ---

    public boolean fundoIsRevelada(int lSup, int cSup, int lF, int cF) {
        return grelha[lSup][cSup].getFundo().isRevelada(lF, cF);
    }

    public void fundoRevelar(int lSup, int cSup, int lF, int cF) {
        grelha[lSup][cSup].getFundo().setRevelada(lF, cF);
    }

    public TipoComponente fundoGetTipo(int lSup, int cSup, int lF, int cF) {
        return grelha[lSup][cSup].getFundo().getTipoComponente(lF, cF);
    }

    public Artefacto fundoRecolherArtefacto(int lSup, int cSup, int lF, int cF) {
        FundoMarinho fundo = grelha[lSup][cSup].getFundo();
        Artefacto art = fundo.getArtefacto(lF, cF);
        if (art != null) {
            fundo.removerComponente(lF, cF);
        }
        return art;
    }

    public int fundoRecolherMinerio(int lSup, int cSup, int lF, int cF) {
        FundoMarinho fundo = grelha[lSup][cSup].getFundo();
        Minerio min = fundo.getMinerio(lF, cF);
        if (min != null) {
            fundo.removerComponente(lF, cF);
            return min.getQtd();
        }
        return 0;
    }

    public void gerarMonstros(int lSup, int cSup) {
        grelha[lSup][cSup].getFundo().gerarMonstros();
    }

    public void fundoLargarItens(int lSup, int cSup, List<Artefacto> artefactos, int minerios) {
        grelha[lSup][cSup].getFundo().largarItens(artefactos, minerios);
    }
}