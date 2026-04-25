package pt.isec.pa.deepsea.model.data.puzzle;

import pt.isec.pa.deepsea.model.data.Settings;

public class Puzzle {
    private int grelha [][];
    private int movimentosRestantes;

    public Puzzle() {
        this.grelha = new int [Settings.PUZZLE_GRELHA][Settings.PUZZLE_GRELHA];
        this.movimentosRestantes = Settings.PUZZLE_MAX_MOVIMENTOS;
        inicializarGrelha();
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
}
