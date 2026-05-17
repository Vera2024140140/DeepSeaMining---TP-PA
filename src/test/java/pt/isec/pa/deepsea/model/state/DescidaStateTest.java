package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
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
    void testMover() {
        // Mover para BAIXO
        assertTrue(context.mover(Direcao.BAIXO));
        assertEquals(DeepSeaState.DESCIDA_STATE, context.getState());
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
        context.mover(Direcao.BAIXO);

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
        context.mover(Direcao.BAIXO);

        // Drone é destruído e estado volta para a Superfície
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }



}