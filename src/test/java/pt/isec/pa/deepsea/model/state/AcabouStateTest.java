package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
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
        context.mover(Direcao.DIREITA);
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }

    /**
     * Simula o navio a ficar sem combustível e confirma que o jogo termina
     * transitando para {@link DeepSeaState#ACABOU_STATE}.
     */
    @Test
    void acabaPorSemCombustivel() {
        while (context.getState() == DeepSeaState.SUPERFICIE_STATE) {
            context.mover(Direcao.DIREITA);
            context.mover(Direcao.ESQUERDA);
        }
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }

    /**
     * Simula a perda de todos os drones e confirma que o jogo termina
     * transitando para {@link DeepSeaState#ACABOU_STATE}.
     */
    @Test
    void acabaPorPerderDrones() {
        jogo.simularNavioSemDrones();
        context.mover(Direcao.DIREITA);
        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());
    }

    @Test
    void testRestricoes() {
        //forcar outro estad
        jogo.simularVitoria();

        context.changeState(DeepSeaState.ACABOU_STATE.getInstance(context, jogo));

        assertEquals(DeepSeaState.ACABOU_STATE, context.getState());

        //verf que nao pode hvr ações dps do jogo estar terminado
        assertFalse(context.mover(Direcao.ESQUERDA));
        assertFalse(context.iniciarDescida());
        assertFalse(context.abrirOficina());
        assertFalse(context.recolherMinerio());
    }
}
