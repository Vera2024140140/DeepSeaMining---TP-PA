package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para o estado {@link pt.isec.pa.deepsea.model.state.states.FundoState}.
 * <p>
 * Esta classe valida as regras de movimento no fundo marinho, as transições
 * automáticas para o minijogo (ao encontrar artefactos) ou para a subida, e os cenários
 * de destruição do drone por falta de combustível ou integridade.
 * @author VeraRibeiro2024140140
 */
public class FundoStateTest {
    private Jogo jogo;
    private DeepSeaContext context;
    /**
     * Prepara o ambiente de teste antes da execução de cada método.
     * <p>
     * Instancia o modelo e o contexto, e simula a descida completa até que o drone
     * atinja o fundo do mar, garantindo que o estado de partida é o {@code FUNDO_STATE}.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);

        // Fazer a transição forçada até ao fundo
        context.iniciarDescida();
        while(context.getState() == DeepSeaState.DESCIDA_STATE) {
            context.mover(Direcao.BAIXO);
        }

        assertEquals(DeepSeaState.FUNDO_STATE, context.getState());
    }
    /**
     * Valida um movimento normal dentro dos limites do fundo.
     * Verifica se o drone se move
     */
    @Test
    void testMoverDroneFundoNormal() {
        // mover drone para baixo
        while(context.getState() == DeepSeaState.DESCIDA_STATE) {
            context.mover(Direcao.BAIXO);
        }
        assertTrue(context.mover(Direcao.BAIXO));
        assertEquals(DeepSeaState.FUNDO_STATE, context.getState());
    }
    /**
     * Testa a transição automática para o estado de subida.
     * <p>
     * Verifica se, ao estar na linha 0 (topo) e tentar mover para CIMA,
     * occore a transição automatica para {@code SUBIDA_STATE}.
     */
    @Test
    void testMoverCimaNoTopoIniciaSubida() {
        //iniciar subida quando está no fundo
        assertTrue(context.mover(Direcao.CIMA));
        assertEquals(DeepSeaState.SUBIDA_STATE, context.getState());
    }
    /**
     * Valida a ação direta de iniciar subida
     */
    @Test
    void testIniciarSubidaDiretamente() {
        // Testar a ação de iniciar a subida
        assertTrue(context.iniciarSubida());
        assertEquals(DeepSeaState.SUBIDA_STATE, context.getState());
    }
    /**
     * Testa a transição para o estado de Puzzle.
     * Simula a descoberta de um artefacto e a consequente mudança de estado.
     */
    @Test
    void testApanharArtefacto() throws Exception {
        Field f = DeepSeaContext.class.getDeclaredField("atual");
        f.setAccessible(true);
        IDeepSeaState state = (IDeepSeaState) f.get(context);

        state.apanharArtefacto();

        assertEquals(DeepSeaState.PUZZLE_STATE, context.getState());
    }
    /**
     * Valida o cenário de perda de todo o combustível.
     * <p>
     * Simula um drone com combustível residual e verifica se o movimento final
     * resulta na destruição do drone e no regresso  à {@code SUPERFICIE_STATE}.
     */
    @Test
    void testPerderDroneSemCombustivel() {
        // Retirar combustível (fica a 1.0)
        jogo.simularGastoDrone();

        // movimento para baixo gasta combustível para matar o drone
        context.mover(Direcao.BAIXO);

        // Drone é destruído e estado volta para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }
    /**
     * Valida o cenário de perda de toda a integridade do drone.
     * <p>
     * Simula danos sucessivos e verifica se o drone é removido do jogo quando a
     * integridade chega a zero, forçando o regresso à {@code SUPERFICIE_STATE}.
     */
    @Test
    void testPerderDroneSemIntegridade() {
        // Tirar 100 pontos de integridade (20 cada vez)
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();
        jogo.simularDanoDrone();

        // Tentar mover com integridade a 0 ou menos
        context.mover(Direcao.BAIXO);

        // Drone deve ter sido destruído e estado voltou para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

}
