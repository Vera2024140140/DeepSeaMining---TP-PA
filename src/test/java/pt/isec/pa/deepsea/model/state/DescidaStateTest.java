package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o estado DescidaState.
 * Verifica o comportamento da FSM durante a fase de descida do drone pelo Fosso,
 * testando o consumo de recursos, colisões e a transição para o FundoMarinho.
 *
 * @author Diogo2024152576
 */
public class DescidaStateTest {
    private Jogo jogo;
    private DeepSeaContext context;

    /**
     * Inicializa o contexto e força a máquina de estados a entrar
     * na fase de descida (DescidaState) para garantir que todos os testes
     * partem do mesmo estado.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);

        // Fazer a transição forçada até à descida
        context.iniciarDescida();

        assertEquals(DeepSeaState.DESCIDA_STATE, context.getState());
    }

    /**
     * Valida que um movimento válido dentro do fosso não altera o estado,
     * mantendo a FSM na descida.
     */
    @Test
    void testMoverDroneFossoNormal() {
        // Mover para BAIXO
        assertTrue(context.moverDroneFosso(Direcao.BAIXO));
        assertEquals(DeepSeaState.DESCIDA_STATE, context.getState());
    }

    /**
     * Testa a transição manual explícita para o fundo. Quando o drone
     * invoca chegarFundo(), a máquina deve mudar para o FUNDO_STATE.
     */
    @Test
    void testChegarFundoDiretamente() {
        // Testar a transição manual de chegar ao fundo
        assertTrue(context.chegarFundo());
        assertEquals(DeepSeaState.FUNDO_STATE, context.getState());
    }

    /**
     * Verifica se o drone é corretamente considerado destruído ao ficar sem
     * combustível, obrigando a máquina de estados a abortar a expedição e
     * a voltar para a Superfície.
     */
    @Test
    void testPerderDroneSemCombustivel() {
        // Retirar combustível (fica a 1.0)
        jogo.simularGastoDrone();

        // Um movimento gasta combustível suficiente para matar o drone
        context.moverDroneFosso(Direcao.BAIXO);

        // Drone é destruído e estado volta para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Verifica se o drone é destruído ao perder toda a sua integridade
     * devido a colisões, forçando o regresso do jogo à Superfície.
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
        context.moverDroneFosso(Direcao.BAIXO);

        // Drone é destruído e estado volta para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }

    /**
     * Valida que forçar a perda do drone manualmente através do método perderDrone()
     * aciona corretamente a transição para o estado Superfície.
     */
    @Test
    void testPerderDroneDiretamente() {
        // Forçar a perda do drone manualmente para verificar transição de estado
        assertTrue(context.perderDrone());
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }


}