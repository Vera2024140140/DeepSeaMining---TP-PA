package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.data.puzzle.Puzzle;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o estado PuzzleState.
 * Valida as condições de entrada e saída do minijogo de recolha
 * de artefactos.
 *
 * @author Diogo2024152576
 */
public class PuzzleStateTest {
    private Jogo jogo;
    private DeepSeaContext context;

    /**
     * Inicializa a FSM garantindo a descida do drone até ao fundo
     * e forçando a chamada a apanharArtefacto() para que a máquina
     * assuma o estado PuzzleState.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);
        jogo.iniciarPuzzle();


        // Fazer a transição forçada até ao Puzzle
        context.iniciarDescida();
        context.changeState(DeepSeaState.PUZZLE_STATE.getInstance(context, jogo));

        assertEquals(DeepSeaState.PUZZLE_STATE, context.getState());
    }

    /**
     * Valida que uma vitória no minijogo (resolução do puzzle) transita
     * a máquina de estados de volta para a fase de exploração do fundo.
     */
    @Test
    void testFimPuzzlecomVitoria() throws Exception{
        Field field = Jogo.class.getDeclaredField("puzzleAtual");
        field.setAccessible(true);

        Puzzle puzzle = (Puzzle) field.get(jogo);

        Field fgrelha = Puzzle.class.getDeclaredField("grelha");
        fgrelha.setAccessible(true);

        int[][] grelhaQuaseCorreta = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };

        fgrelha.set(puzzle, grelhaQuaseCorreta);
        assertTrue(context.mover(Direcao.DIREITA));
        assertEquals(DeepSeaState.FUNDO_STATE, context.getState());
    }

    @Test
    void testFimPuzzleSemMovimentos() {
        Direcao[] direcoes = {Direcao.CIMA, Direcao.ESQUERDA, Direcao.DIREITA, Direcao.BAIXO};
        int i = 0;

        while (context.getState() == DeepSeaState.PUZZLE_STATE) {
            context.mover(direcoes[i % 4]);
            i++;
        }
        assertEquals(DeepSeaState.SUBIDA_STATE, context.getState());
    }
}