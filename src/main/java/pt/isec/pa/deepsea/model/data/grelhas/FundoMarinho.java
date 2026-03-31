package pt.isec.pa.deepsea.model.data.grelhas;


import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.elementos.Componente;
import pt.isec.pa.deepsea.model.data.elementos.Minerio;
import pt.isec.pa.deepsea.model.data.elementos.Monstro;

import java.util.ArrayList;
import java.util.List;

public class FundoMarinho {

    private final CelulaFundo[][] grelha;

    private final int linhas;
    private final int colunas;

    public FundoMarinho() {
        this.linhas = Settings.LINHAS_FUNDO;
        this.colunas = Settings.COLUNAS_FUNDO;
        this.grelha = new CelulaFundo[linhas][colunas];

        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++){
                grelha[l][c] = new CelulaFundo();
            }
        }

        if (Settings.MODO_DEFESA){
            revelarTudo();
            colocarMineriosModoDefesa();
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    private void revelarTudo() {
        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++){
                grelha[l][c].revelar();
            }
        }
    }
    public void gerarMonstros() {
        int qtdMonstros;
        List<int[]> livres = getCelulasLivres();
        List<int[]> posPossiveis = new ArrayList<>();
        int indice;
        int [] pos;
        if (Settings.MODO_DEFESA){
            qtdMonstros = Settings.DEFESA_MONSTROS;
            for (int i = 0; i < qtdMonstros; i++){
                if(livres.isEmpty()){
                    break;
                }
                indice = Utilidades.aleatorio(0,livres.size() - 1);
                pos =livres.get(indice);
                grelha[pos[0]][pos[1]].setComponente(new Monstro(pos[0],pos[1]));
                livres.remove(indice);
            }
        }else {
            qtdMonstros = Utilidades.aleatorio(Settings.MONSTROS_FUNDO_MIN,Settings.MONSTROS_FUNDO_MAX);
            for (int [] p : livres ){
                if(!grelha[p[0]][p[1]].isRevelada()){
                    posPossiveis.add(p);
                }
            }

            for (int i = 0; i < qtdMonstros; i++){
                if(posPossiveis.isEmpty()){
                    break;
                }
                indice = Utilidades.aleatorio(0,posPossiveis.size() - 1);
                pos = posPossiveis.get(indice);
                grelha[pos[0]][pos[1]].setComponente(new Monstro(pos[0],pos[1]));
                posPossiveis.remove(indice);
            }
        }

    }
    public List<int[]> getCelulasLivres() {
        List<int[]> lista = new ArrayList<>();
        for (int l = 0; l < linhas; l++){
            for (int c = 0; c < colunas; c++){
                if (grelha[l][c].isEmpty()){
                    lista.add(new int[]{l, c});
                }
            }
        }
        return lista;
    }

    private boolean dentroLimites(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    private void colocarMineriosModoDefesa() {
        List<int[]> livres = getCelulasLivres();
        for (int i = 0; i < Settings.DEFESA_MINERIOS && !livres.isEmpty(); i++){
            int index = Utilidades.aleatorio(0, livres.size() - 1);
            int[] pos = livres.remove(index);
            int qtd = (i==0) ? 1 : Settings.DEFESA_MINERIO_QTD_MAX;
            if (dentroLimites(pos[0], pos[1]) && grelha[pos[0]][pos[1]].isEmpty()){
                grelha[pos[0]][pos[1]].setComponente(new Minerio(pos[0], pos[1], qtd));
            } else {
                i--;
            }
        }
    }

    public boolean colocarMinerio(int linha, int coluna, int qtd) {
        if (dentroLimites(linha, coluna) && grelha[linha][coluna].isEmpty()){
            grelha[linha][coluna].setComponente(new Minerio(linha, coluna, qtd));
            return true;
        }
        return false;
    }

    public boolean colocarArtefacto(int linha, int coluna) {
        if (dentroLimites(linha, coluna) && grelha[linha][coluna].isEmpty()) {
            grelha[linha][coluna].setComponente(new Artefacto(linha, coluna));
            return true;
        }
        return false;
    }

    public Componente getComponente(int linha, int coluna) {
        if (dentroLimites(linha, coluna)) {
            return grelha[linha][coluna].getComponente();
        }
        return null;
    }

    public boolean isRevelada(int l, int c) {
        return grelha[l][c].isRevelada();
    }
    public void setRevelada(int l, int c){
        grelha[l][c].revelar();
    }
}

