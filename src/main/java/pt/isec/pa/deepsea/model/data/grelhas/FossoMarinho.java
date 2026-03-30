package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.AnimalMarinho;
import pt.isec.pa.deepsea.model.data.elementos.Componente;
import pt.isec.pa.deepsea.model.data.elementos.Corrente;
import pt.isec.pa.deepsea.model.data.elementos.Rocha;

import java.util.ArrayList;
import java.util.List;

public class FossoMarinho {

    private final CelulaFosso[][] grelha;

    private final int linhas;
    private final int colunas;

    public FossoMarinho() {
        this.linhas = Settings.LINHAS_FOSSO;
        this.colunas = Settings.COLUNAS_FOSSO;
        this.grelha = new CelulaFosso[linhas][colunas];

        for (int l = 0; l < linhas; l++)
            for (int c = 0; c < colunas; c++) {
                grelha[l][c] = new CelulaFosso();
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
        } else {

            // maximo de colunas com rocha por lado (menos de metade - 1)
            int maxPorLado = Math.max(1, (int)(colunas * Settings.ROCHAS_PERCENTAGEM_MAX / 2) - 1);

            int esq = Utilidades.aleatorio(1, maxPorLado);
            int dir = Utilidades.aleatorio(1, maxPorLado);

            for (int l=0; l < linhas; l++){
                esq = variarEspessura(esq, 1, maxPorLado);
                dir = variarEspessura(dir, 1, maxPorLado);

                // a soma dos dois lados não pode ser maior que 50% para o centro ser navegavel
                while(esq + dir > (int)(colunas * Settings.ROCHAS_PERCENTAGEM_MAX)){
                    // igualar os lados
                    if (esq > dir){
                        esq--;
                    } else {
                        dir--;
                    }

                    // tem de ter pelo menos 1 coluna
                    if (esq == 0){
                        esq = 1;
                    }
                    if (dir == 0){
                        dir = 1;
                    }
                }

                //meter componentes até ao valor gerado de cada lado
                for (int c = 0; c < dir; c++){
                    grelha[l][c].setComponente(new Rocha(l, c));
                }
                //dir -> esq
                for (int c = 0; c < dir; c++){
                    int col = colunas - c - 1;
                    grelha[l][col].setComponente(new Rocha(l, col));
                }
            }
        }
    }
    public void gerarObstaculos() {
        if (Settings.MODO_DEFESA){
            int centrolinha = Settings.LINHAS_FOSSO / 2;
            int centrocoluna = Settings.COLUNAS_FOSSO / 2;

            grelha[centrolinha][centrocoluna].setComponente (new Corrente(centrolinha,centrocoluna));
            grelha[centrolinha + 1][centrocoluna].setComponente(new AnimalMarinho(centrolinha + 1, centrocoluna));

        } else {
            int qtdObstaculos = Utilidades.aleatorio(Settings.OBSTACULOS_FOSSO_MIN,Settings.OBSTACULOS_FOSSO_MAX);
            for (int i = 0 ; i < qtdObstaculos; i++){
                int [] pos = posicaoFossoLivre();
                if (pos != null){
                    if (Utilidades.probAleatoria() < 0.5){
                        grelha[pos[0]][pos[1]].setComponente(new AnimalMarinho(pos[0],pos[1]));
                    } else {
                        grelha[pos[0]][pos[1]].setComponente(new Corrente(pos[0],pos[1]));
                    }
                } else {
                    break;
                }
            }
        }
    }
    private int variarEspessura(int valor, int min, int max) {
        return Math.max(min, Math.min(max, valor + Utilidades.aleatorio(-1, 1)));
    }
    private int[] posicaoFossoLivre() {
        List<int[]> livres =  new ArrayList<>();
        for (int i  = 0; i < linhas; i++ ){
            for (int j = 0; j < colunas; j++){
                if (grelha[i][j].isEmpty()){
                    livres.add(new int [] {i,j});
                }
            }
        }
        if(livres.isEmpty()){
            return null;
        }
        return livres.get(Utilidades.aleatorio(0, livres.size() - 1));
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