package pt.isec.pa.deepsea.model.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de testes unitários responsável por validar o comportamento e as
 * transições do estado {@link pt.isec.pa.deepsea.model.state.states.OficinaState}.
 * <p>
 * Garante que as operações (abastecer, reparar, melhorar) respeitam
 * as regras de recursos do jogo e que as transições de saída ocorrem corretamente.
 * @author VeraRibeiro2024140140
 */
public class OficinaStateTest {
    private Jogo jogo;
    private DeepSeaContext context;

    /**
     * Prepara o ambiente de teste antes da execução de cada método.
     * Inicializa uma nova instância do jogo e do contexto, e transita para a Oficina,
     * garantindo um ponto de partida limpo.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        context = new DeepSeaContext(jogo);
        context.abrirOficina(); //para todos os testes começarem na oficina
    }
    /**
     * Verifica se é identificado corretamente o estado atual.
     */
    @Test
    void testGetState() {
        assertEquals(DeepSeaState.OFICINA_STATE, context.getState());
    }
    /**
     * Testa a transição de saída da oficina.
     * Garante que a ação é permitida e que ocorre a transição para o estado da Superfície.
     */
    @Test
    void testFecharOficina() {
        assertTrue(context.fecharOficina());
        assertEquals(DeepSeaState.SUPERFICIE_STATE, context.getState());
    }
    /**
     * Testa a validação na escolha do drone para manutenção.
     * Testa o cenário de sucesso (ID existente) e de falha (ID inexistente).
     */
    @Test
    void testSelecionarDrone() {
        assertTrue(context.selecionarDrone(context.getIdsDronesNavio().iterator().next()));
        assertFalse(context.selecionarDrone(999));
    }
    /**
     * Valida as regras  associadas ao abastecimento de combustível do drone.
     * Testa três cenários distintos:
     * 1. Drone já com a capacidade no máximo.
     * 2. Abastecimento com sucesso (após consumo artificial).
     * 3. Falha por falta de recursos (navio sem combustível).
     */
    @Test
    void testAbastecerDrone() {
        context.selecionarDrone(context.getIdsDronesNavio().iterator().next());
        // Cenário 1: Falha - Drone já está cheio (por defeito começa cheio)
        assertFalse(context.abastecerDrone(20.0));

        // Cenário 2: Sucesso - Gasta algum combustível e abastece
        jogo.simularGastoDrone(); // Retira combustível artificialmente para testes
        assertTrue(context.abastecerDrone(20.0));

        // Cenário 3: Navio não tem combustível para passar ao drone
        jogo.simularGastoDrone();
        jogo.simularNavioSemCombustivel();
        assertFalse(context.abastecerDrone(20.0));
    }
    /**
     * Valida as regras associadas à reparação do casco do drone.
     * Testa quatro cenários:
     * 1. Integridade já no máximo.
     * 2. Tentativa de reparação com valores inválidos (negativos).
     * 3. Reparação com sucesso.
     * 4. Falha por falta de combustível para pagar a taxa de reparação.
     */
    @Test
    void testRepararDrone() {
        context.selecionarDrone(context.getIdsDronesNavio().iterator().next());
        // Cenário 1: Drone está com a integridade no máximo
        assertFalse(context.repararDrone(10));

        // Cenário 2: Valores negativos ou zero
        jogo.simularDanoDrone(); // Retira integridade artificialmente para testes
        assertFalse(context.repararDrone(-5));

        // Cenário 3: Repara o drone danificado
        assertTrue(context.repararDrone(10));

        // Cenário 4: Navio não tem combustível para pagar a reparação
        jogo.simularDanoDrone();
        jogo.simularNavioSemCombustivel();
        assertFalse(context.repararDrone(10));
    }
    /**
     * Testa a operação de melhoria da capacidade máxima do tanque do drone.
     */
    @Test
    void testMelhorarTanqueDrone(){
        context.selecionarDrone(context.getIdsDronesNavio().iterator().next());

        // Cenário 1: Navio não tem minérios suficientes
        assertFalse(context.melhorarTanqueDrone());

        // Cenário 2: Navio tem minérios suficientes
        jogo.simularMinerios(50); // Adiciona 50 minérios ao navio artificialmente
        assertTrue(context.melhorarTanqueDrone());
    }
    /**
     * Testa a operação de melhoria da integridade estrutural máxima do drone.
     */
    @Test
    void testMelhorarIntegridadeDrone(){
        context.selecionarDrone(context.getIdsDronesNavio().iterator().next());

        // Cenário 1: Navio não tem minérios suficientes
        assertFalse(context.melhorarIntegridadeDrone());

        // Cenário 2: Navio tem minérios suficientes
        jogo.simularMinerios(50); // Adiciona 50 minérios ao navio artificialmente
        assertTrue(context.melhorarIntegridadeDrone());
    }
}
