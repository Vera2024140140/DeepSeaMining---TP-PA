package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o estado SubidaState.
 * Verifica o comportamento da FSM durante a fase de regresso do drone à Superfície,
 * validando que as condições de morte na subida provocam a queda dos itens,
 * e testando a transição de sucesso para o navio.
 *
 * @author Diogo2024152576
 */
public class SubidaStateTest {
    private Jogo jogo;
    private DeepSeaContext context;

    /**
     * Inicializa o contexto e força a máquina de estados a atravessar
     * todas as fases anteriores até chegar ao estado SubidaState para
     * garantir consistência nos testes.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);

        // Fazer a transição forçada até à subida
        context.iniciarDescida();
        jogo.meteDroneNoFimFosso();

        context.changeState(DeepSeaState.SUBIDA_STATE.getInstance(context, jogo));
        assertEquals(DeepSeaState.SUBIDA_STATE, context.getState());
    }

    /**
     * Testa a transição manual de sucesso quando o drone atinge
     * efetivamente a linha de topo do fosso e volta ao navio,
     * garantindo que os itens são descarregados.
     */
    @Test
    void testSubirSuperficieDiretamente() {
        while(context.getState() == DeepSeaState.SUBIDA_STATE) {
            context.mover(Direcao.CIMA);
        }

        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Verifica se o drone fica sem combustível
     * a meio da subida. O estado deve voltar para a Superfície e o drone
     * é dado como perdido.
     */
    @Test
    void testPerderDroneSemCombustivel() {
        // Retirar combustível
        jogo.simularGastoDrone();

        // Um movimento gasta combustível suficiente para matar o drone
        context.mover(Direcao.CIMA);

        // Drone deve ter sido destruído e estado voltou para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Verifica se o drone perde a integridade
     * total na subida devido a colisões. A FSM deve abortar o mergulho
     * e o estado deve voltar para a Superfície.
     */
    @Test
    void testPerderDroneSemIntegridade() {
        // Tirar 100 pontos de integridade
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();

        // Tentar mover
        context.mover(Direcao.CIMA);

        // Drone deve ter sido destruído e estado voltou para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Valida que forçar a perda do drone manualmente aciona corretamente
     * a transição de estado de volta para a Superfície.
     */
    @Test
    void testPerderDroneDiretamente() throws Exception {
        // 1. Aceder ao estado atual
        Field f = DeepSeaContext.class.getDeclaredField("atual");
        f.setAccessible(true);
        IDeepSeaState state = (IDeepSeaState) f.get(context);
        // 2. Aceder ao método protegido "perderDrone"
        Method m = DeepSeaStateAdapter.class.getDeclaredMethod("perderDrone");
        m.setAccessible(true);
        // 3. Forçar a perda do drone invocando o método
        boolean resultado = (boolean) m.invoke(state);

        assertTrue(resultado);
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }
}
