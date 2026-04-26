// Este teste está neste package (e não em state.states junto do SuperficieStateTest)
// para conseguir usar o construtor package-private DeepSeaContext(Jogo).
// Assim evitamos ter 3 metodos a simular no DeepSeaContext os métodos
// de simulação ficam só no Jogo.
package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link pt.isec.pa.deepsea.model.state.states.AcabouState}.
 *
 * @author Diogo2024152576
 */
class AcabouStateTest {
    private Jogo jogo;
    private DeepSeaContext context;

    /**
     * Inicializa um novo contexto antes de cada teste, partilhando o mesmo
     * {@link Jogo} com o contexto para que os métodos de simulação afetem
     * o estado observado pelo FSM.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Simula a recolha de todos os artefactos e confirma que o jogo termina
     * transitando para {@link DeepSeaState#ACABOU_STATE}.
     */
    @Test
    void acabaPorVitoria() {
        jogo.simularVitoria();
        assertTrue(context.avaliarFimJogo());
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }

    /**
     * Simula o navio a ficar sem combustível e confirma que o jogo termina
     * transitando para {@link DeepSeaState#ACABOU_STATE}.
     */
    @Test
    void acabaPorSemCombustivel() {
        jogo.simularNavioSemCombustivel();
        assertTrue(context.avaliarFimJogo());
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }

    /**
     * Simula a perda de todos os drones e confirma que o jogo termina
     * transitando para {@link DeepSeaState#ACABOU_STATE}.
     */
    @Test
    void acabaPorPerderDrones() {
        jogo.simularNavioSemDrones();
        assertTrue(context.avaliarFimJogo());
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }
}
