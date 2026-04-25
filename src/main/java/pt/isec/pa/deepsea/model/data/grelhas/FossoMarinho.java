package pt.isec.pa.deepsea.model.data.grelhas;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
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
            int maxPorLado = Math.max(Settings.MINIMO_ROCHAS_LADO, (int)(colunas * Settings.ROCHAS_PERCENTAGEM_MAX / 2) - 1);

            int esq = Utilidades.aleatorio(Settings.MINIMO_ROCHAS_LADO, maxPorLado);

            for (int l=0; l < linhas; l++){
                if(l > 0){
                    // variar a espessura das rochas de linha para linha
                    esq = variarEspessura(esq, maxPorLado);
                }

                // colocar rochas no lado esquerdo
                for (int c = 0; c < esq; c++) {
                    grelha[l][c].setComponente(new Rocha(l, c));
                }

                // espelhar simetricamente para o lado direito
                for (int c = 0; c < esq; c++) {
                    int col = colunas - 1 - c;
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

    private int variarEspessura(int valor, int max_lado) {
        int espessura;
        // Nao passar maximo
        // Minimo entre nova espessura e o maximo do lado
        espessura = Math.min(max_lado, valor + Utilidades.aleatorio(-1, 1));

        // Nao passar minimo
        // Maximo entre nova espessura e o minimo do lado
        return Math.max(Settings.MINIMO_ROCHAS_LADO, espessura);
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

    TipoComponente getTipoComponente(int lFosso, int cFosso) {
        if (dentroLimites(lFosso, cFosso)) {
            Componente comp = grelha[lFosso][cFosso].getComponente();
            if (comp != null) {
                return comp.getTipo();
            }
        }
        return null;
    }

    //metodos de colisao
    public boolean celulaTemRocha(int l, int c) {
        if (l < 0 || l >= linhas || c < 0 || c >= colunas) return false;

        return getTipoComponente(l, c) == TipoComponente.ROCHA;
    }

    public boolean celulaTemAnimal(int l, int c) {
        if (l < 0 || l >= linhas || c < 0 || c >= colunas) return false;
        return  getTipoComponente(l, c) == TipoComponente.ANIMALMARINHO;
    }

    public boolean celulaTemCorrente(int l, int c) {
        if (l < 0 || l >= linhas || c < 0 || c >= colunas) return false;
        return getTipoComponente(l, c) == TipoComponente.CORRENTE;
    }
}