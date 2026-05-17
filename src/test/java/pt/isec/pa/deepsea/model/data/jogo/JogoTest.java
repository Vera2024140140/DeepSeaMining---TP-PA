package pt.isec.pa.deepsea.model.data.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a lógica central do modelo de dados implementada na classe {@link Jogo}.
 * O foco destes testes recai sobre as mecanicas,
 * incluindo a gestão global da expedição (navegação, controlo de drones,
 * operações de descida/subida, mecânicas da oficina e as verificações do mini-jogo do puzzle).
 *
 * @author Diogo2024152576
 */
class JogoTest {

    private Jogo jogo;

    /**
     * Instancia um novo Jogo antes de cada teste, garantindo
     * um ambiente limpo e isolado de alterações.
     */
    @BeforeEach
    void setUp() {
        jogo = new Jogo();
    }

    // ===================================================================
    // --- INICIALIZAÇÃO ---
    // ===================================================================

    /**
     * Verifica o combustível e frota inicializados.
     */
    @Test
    void testeInicializacao() {
        assertEquals(Settings.NAVIO_COMBUSTIVEL_INICIAL, jogo.getCombustivelNavio());
        assertEquals(0, jogo.getMineriosNavio());
        assertEquals(Settings.NUM_DRONES_INICIAIS, jogo.getIdsDronesNavio().size());
        assertTrue(jogo.getIdsArtefactosNavio().isEmpty());
    }

    // ===================================================================
    // --- MOVIMENTO DO NAVIO ---
    // ===================================================================

    /**
     * Valida que a deslocação do navio à superfície debita corretamente
     * a quantidade de combustível estipulada nas Settings.
     */
    @Test
    void moverNavioValidoConsomeCombustivel() {
        double combAntes = jogo.getCombustivelNavio();
        assertTrue(jogo.moverNavio(Direcao.DIREITA));
        assertEquals(combAntes - Settings.COMBUSTIVEL_MOV_NAVIO, jogo.getCombustivelNavio(), 0.0001);
    }

    /**
     * Confirma que o modelo impede ativamente a deslocação do navio
     * se este já não possuir combustível.
     */
    @Test
    void moverNavioSemCombustivel() {
        jogo.simularNavioSemCombustivel();
        assertFalse(jogo.moverNavio(Direcao.DIREITA));
    }

    // ===================================================================
    // --- DRONES ---
    // ===================================================================

    /**
     * Garante que as verificações de pré-condições da descida validam o estado
     * do drone (tem de possuir combustível e integridade).
     */
    @Test
    void podeIniciarDescidaNoJogoNovo() {
        assertTrue(jogo.podeIniciarDescida(),
                "Um drone novo tem combustível e integridade, logo pode descer");
    }

    /**
     * Verifica se a destruição/remoção de um drone deduz o seu ID da
     * frota do navio adequadamente.
     */
    @Test
    void removerDroneAtivoReduzDrones() {
        int antes = jogo.getIdsDronesNavio().size();
        assertTrue(jogo.removerDroneAtivo());
        assertEquals(antes - 1, jogo.getIdsDronesNavio().size());
    }

    /**
     * Valida o débito do consumo de combustível inerente
     * à movimentação física do drone no fundo marinho.
     */
    @Test
    void moverDroneFundoConsomeCombustivel() {
        jogo.meteDroneNoFundo();
        double combAntes = jogo.getCombustivelDroneAtivo();
        assertTrue(jogo.moverDroneFundo(Direcao.BAIXO));
        assertTrue(jogo.getCombustivelDroneAtivo() < combAntes);
    }

    // ===================================================================
    // --- DESCARREGAR DRONE NO NAVIO ---
    // ===================================================================

    /**
     * Valida o processo de transferência de minérios e artefactos
     * recolhidos pelo drone para a "carga" (inventário) do Navio.
     */
    @Test
    void descarregarDroneNavioComDroneAtivo() {
        assertTrue(jogo.descarregarDroneNavio());
    }

    // ===================================================================
    // --- PUZZLE ---
    // ===================================================================

    /**
     * Garante que invocar o método de limpeza do puzzle zera todas as
     * informações de estado de resolução.
     */
    @Test
    void limparPuzzleInvalidaConsultasAoPuzzle() {
        jogo.iniciarPuzzle();
        jogo.limparPuzzle();
        assertFalse(jogo.isPuzzleResolvido());
        assertFalse(jogo.isPuzzleSemMovimentos());
        assertFalse(jogo.moverPecaPuzzle(Direcao.CIMA));
    }

    /**
     * Confirma que, com o puzzle inicializado, comandos de
     * movimentação não fazem o crasham o programa.
     */
    @Test
    void moverPecaPuzzleComPuzzleAtivo() {
        jogo.iniciarPuzzle();
        // qualquer direção é aceitável, pode ou não ser válida consoante a posição do espaço em branco
        assertDoesNotThrow(() -> jogo.moverPecaPuzzle(Direcao.CIMA));
        assertDoesNotThrow(() -> jogo.moverPecaPuzzle(Direcao.BAIXO));
        assertDoesNotThrow(() -> jogo.moverPecaPuzzle(Direcao.ESQUERDA));
        assertDoesNotThrow(() -> jogo.moverPecaPuzzle(Direcao.DIREITA));
    }

}