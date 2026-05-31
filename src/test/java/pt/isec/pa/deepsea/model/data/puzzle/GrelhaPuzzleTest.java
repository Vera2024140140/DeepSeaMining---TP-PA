package pt.isec.pa.deepsea.model.data.puzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;

public class GrelhaPuzzleTest {
    private Puzzle puzzle;
    @BeforeEach
    void setUp(){
        puzzle = new Puzzle();
    }
    @Test
    void testaIncializacao(){
        int[][] grelhaP = puzzle.getGrelha();
        Assertions.assertNotNull(grelhaP);
        Assertions.assertEquals(Settings.PUZZLE_GRELHA, grelhaP.length);
        Assertions.assertEquals(Settings.PUZZLE_GRELHA, grelhaP[0].length);
    }
    @Test
    void testaNumerosPuzzle(){
        puzzle.inicializarGrelha();
        int numero = 1;
        for (int i = 0; i < Settings.PUZZLE_GRELHA; i++){
            for (int j = 0; j < Settings.PUZZLE_GRELHA; j++){
                if (i == Settings.PUZZLE_GRELHA - 1 && j == Settings.PUZZLE_GRELHA -1){
                    Assertions.assertEquals(0, puzzle.getCelula(i,j));
                }else{
                    Assertions.assertEquals(numero, puzzle.getCelula(i,j));
                    numero++;
                }
            }
        }
    }

}
