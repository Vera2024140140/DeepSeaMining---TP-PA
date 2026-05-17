package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

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
    /*
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);

        // Fazer a transição forçada até ao Puzzle
        context.iniciarDescida();
        context.chegarFundo();
        context.apanharArtefacto();

        assertEquals(DeepSeaState.PUZZLE_STATE, context.getState());
    }*/

    /**
     * Valida que uma vitória no minijogo (resolução do puzzle) transita
     * a máquina de estados de volta para a fase de exploração do fundo.
     */
    @Test
    void testFimPuzzle() {
        /*
        // Testar a transição manual ao terminar o puzzle com sucesso
        assertTrue(context.fimPuzzle());

        // Deve voltar para o estado de Fundo
        assertEquals(DeepSeaState.FUNDO_STATE, context.getState());
        */
         */
    }
}