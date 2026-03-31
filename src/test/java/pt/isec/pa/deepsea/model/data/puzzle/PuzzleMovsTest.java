package pt.isec.pa.deepsea.model.data.puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PuzzleMovsTest {
    private Puzzle puzzle;
    @BeforeEach
    void setUp(){
        puzzle = new Puzzle();
    }
    @Test
    void testaMovimentosIniciais(){
        int movimentosIniciais = puzzle.getMovimentosRestantes();
        assertEquals(Settings.PUZZLE_MAX_MOVIMENTOS,movimentosIniciais);
    }
    @Test
    void testeGestaoMovimentos(){
        int movimentosIniciais = puzzle.getMovimentosRestantes();
        puzzle.decrementarMovimentos();
        assertEquals(movimentosIniciais - 1, puzzle.getMovimentosRestantes());

        for (int i  = 0; i < movimentosIniciais + 2; i++){
            puzzle.decrementarMovimentos();
        }
        assertEquals(0,puzzle.getMovimentosRestantes());
    }
    @Test
    void testeReset(){
        for (int i = puzzle.getMovimentosRestantes(); i > 0; i--){
            puzzle.decrementarMovimentos();
        }
        assertEquals(0,puzzle.getMovimentosRestantes());
        puzzle.resetMovimentos();
        assertEquals(Settings.PUZZLE_MAX_MOVIMENTOS,puzzle.getMovimentosRestantes());
    }
}
