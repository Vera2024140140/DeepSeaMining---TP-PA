package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.state.states.SuperficieState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link SuperficieState}.
 *
 * @author Diogo2024152576
 */
class SuperficieStateTest {
    private DeepSeaContext context;


    /**
     * Inicializa um novo contexto antes de cada teste e garante que o
     * estado inicial é {@link DeepSeaState#SUPERFICIE_STATE}.
     */
    @BeforeEach
    void setUp() {
        context = new DeepSeaContext();
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Confirma que mover o navio numa direção válida devolve {@code true}
     * e que consome combustível ao navio.
     */
    @Test
    void moverNavio() {
        double combAntes = context.getCombustivelNavio();
        assertTrue(context.mover(Direcao.DIREITA));
        assertTrue(context.getCombustivelNavio() < combAntes);
    }

    /**
     * Confirma que abrir a oficina transita para
     * {@link DeepSeaState#OFICINA_STATE}.
     */
    @Test
    void abrirOficina() {
        assertTrue(context.abrirOficina());
        assertEquals(DeepSeaState.OFICINA_STATE, context.getState());
    }

    /**
     * Confirma que, com o drone inicial ativo, a descida é iniciada com
     * sucesso e transita para {@link DeepSeaState#DESCIDA_STATE}.
     */
    @Test
    void iniciarDescida() {
        assertTrue(context.iniciarDescida());
        assertEquals(DeepSeaState.DESCIDA_STATE, context.getState());
    }
}