package pt.isec.pa.deepsea.model.data.puzzle;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;

public class Puzzle {
    private int grelha [][];
    private int movimentosRestantes;

    public Puzzle() {
        this.grelha = new int [Settings.PUZZLE_GRELHA][Settings.PUZZLE_GRELHA];
        this.movimentosRestantes = Settings.PUZZLE_MAX_MOVIMENTOS;
        inicializarGrelha();
        baralhar();
    }

    private void inicializarGrelha() {
        int numeros = 1;
        for(int i = 0; i < Settings.PUZZLE_GRELHA; i++){
            for (int j = 0; j < Settings.PUZZLE_GRELHA; j++){
                //celula do canto inferior direito
                if (i == Settings.PUZZLE_GRELHA - 1 && j == Settings.PUZZLE_GRELHA - 1){
                    this.grelha[i][j] = 0;
                }else{
                    this.grelha[i][j] = numeros++;
                }
            }
        }
    }

    int [][] getGrelha() {
        return grelha;
    }

    public int getMovimentosRestantes() {
        return movimentosRestantes;
    }

    public void decrementarMovimentos() {
        if (movimentosRestantes > 0) {
            movimentosRestantes--;
        }
    }

    public void resetMovimentos() {
        this.movimentosRestantes = Settings.PUZZLE_MAX_MOVIMENTOS;
    }

    public int getCelula (int l, int c){
        return grelha[l][c];
    }

    private void trocarPosicoes(int l1, int c1, int l2, int c2) {
        int temp = grelha[l1][c1];
        grelha[l1][c1] = grelha[l2][c2];
        grelha[l2][c2] = temp;
    }
    // ===================================================================
    // --- logica de movimentos & estado ---
    // ===================================================================
    //retornar coordenadas l/c da peça vazia
    private int[] encontrarCelulaVazia() {
        for(int l = 0; l < Settings.PUZZLE_GRELHA; l++){
            for(int c = 0; c < Settings.PUZZLE_GRELHA; c++){
                if (grelha[l][c] == 0){
                    return new int[]{l,c};
                }
            }
        }
        return new int[]{Settings.PUZZLE_GRELHA -1, Settings.PUZZLE_GRELHA -1};
    }

    public boolean mover(Direcao dir) {
        int[] posVazio = encontrarCelulaVazia();
        int lVazia = posVazio[0];
        int cVazia = posVazio[1];

        int lAlvo = lVazia;
        int cAlvo = cVazia;

        //calcula a posição com a qual o '0' vai trocar
        switch (dir) {
            case CIMA -> lAlvo--;
            case BAIXO -> lAlvo++;
            case ESQUERDA -> cAlvo--;
            case DIREITA -> cAlvo++;
        }

        //verf mov valido
        if (lAlvo >= 0 && lAlvo < Settings.PUZZLE_GRELHA && cAlvo >= 0 && cAlvo < Settings.PUZZLE_GRELHA) {
            trocarPosicoes(lVazia, cVazia, lAlvo, cAlvo);
            decrementarMovimentos();
            return true;
        }
        return false;
    }


    //verificar se o puzzle já está por ordem
    public  boolean estaResolvido() {
        int esperado = 1;
        for(int l = 0; l < Settings.PUZZLE_GRELHA; l++){
            for(int c = 0; c < Settings.PUZZLE_GRELHA; c++){
                if (l == Settings.PUZZLE_GRELHA - 1 && c == Settings.PUZZLE_GRELHA - 1) {
                    return grelha[l][c] == 0;
                }
                if (grelha[l][c] != esperado) {
                    return false;
                }
                esperado++;
            }
        }
        return true;
    }

    //baralhar o puzzle
    public void baralhar() {
        // vai ao enum buscar as pos (CIMA/BAIXO/DIR/ESQ) -> (0/1/2/3)
        Direcao[] direcoes = Direcao.values();

        //100 mov rand
        for(int i = 0; i < Settings.PUZZLE_BARALHAR_GRELHA; i++) {
            //sortear num 0/3 -> ex2 -> esq
            Direcao dRandom = direcoes[Utilidades.aleatorio(0, direcoes.length - 1)];
            //move a peca para a dir sorteada se possivel
            mover(dRandom);
        }
        resetMovimentos();
    }
}
