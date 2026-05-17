
package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

/**
 * Estado do jogo, em que o ‘drone’ está a navegar no Fundo Marinho.
 * <p>
 * Neste estado, o jogador pode mover o ‘drone’ pela grelha do fundo, recolher minérios
 * manualmente, acionar a transição para o minijogo ao encontrar um artefacto, ou
 * iniciar a subida de regresso. Este estado também é responsável por verificar as
 * condições de destruição do ‘drone’: falta de integridade ou combustível.
 *
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class FundoState extends DeepSeaStateAdapter {
    private static final long serialVersionUID = 53L;
    /**
     * Cria uma instância do estado de exploração do Fundo.
     * @param context contexto da máquina de estados
     * @param jogo    modelo de dados do jogo
     */
    public FundoState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /**
     * @return o identificador deste estado ({@link DeepSeaState#FUNDO_STATE})
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.FUNDO_STATE;
    }
    /**
     * Move o drone na grelha do fundo processando todas as consequências do movimento.
     * <p>
     * O fluxo de verificações ocorre na seguinte ordem:
     * <ol>
     * <li>Verifica se o jogador pretende iniciar a subida (mover para CIMA na linha 0).</li>
     * <li>Efetua o movimento e gasta o combustível/aplica dano de monstros.</li>
     * <li>Verifica se o drone foi destruído no processo. Se sim, transita de estado.</li>
     * <li>Verifica se ficou posicionado num artefacto, transitando para o puzzle de imediato.</li>
     * </ol>
     *
     * @param dir A direção pretendida para o movimento.
     * @return {@code true} se o movimento (ou transição resultante) ocorreu com sucesso;
     * {@code false} caso o movimento seja inválido
     */
    @Override
    public boolean mover(Direcao dir){
        boolean moveu = jogo.moverDroneFundo(dir);
        if (!moveu) return false;

        //verifica se o drone morreu (sem combustivel OU sem integridade)
        if (jogo.getCombustivelDroneAtivo() <= 0 || jogo.getIntegridadeDroneAtivo() <= 0) {
            perderDrone();
            return true;
        }

        if (jogo.verificarArtefacto())
            apanharArtefacto();

        return true;
    }
    /**
     * Inicia a de recolha do artefacto.
     * Gera a matriz do minijogo e transita para o estado do Puzzle.
     *
     * @return Sempre {@code true}, pois a transição é forçada e garantida.
     */
    @Override
    public boolean apanharArtefacto() {
        jogo.iniciarPuzzle(); // criar a matriz do puzzle
        changeState(DeepSeaState.PUZZLE_STATE);
        return true;
    }
    /**
     * Tenta recolher o minério presente na célula atual do drone.
     * É uma ação do jogador que consome combustível adicional.
     *
     * @return {@code true} se o minério foi recolhido com sucesso;
     * {@code false} se não houver minério ou se o combustível for insuficiente.
     */
    @Override
    public boolean recolherMinerio() {
        return jogo.recolherMinerio();
    }

    /**
     * Transita para o estado de subida
     *
     * @return Sempre {@code true}, confirmando o início da subida.
     */
    @Override
    public boolean iniciarSubida(){
        if(!jogo.meteDroneNoFimFosso())
            return false;
        changeState(DeepSeaState.SUBIDA_STATE);
        return true;
    }
    /**
     * Processa a destruição do drone ativo.
     * Larga os itens do inventário do drone no fundo do mar, remove o drone da lista de drones,
     * e transita para a superfície.
     *
     * @return {@code true} se o drone foi removido e o estado alterado com sucesso;
     * {@code false} caso ocorra uma falha na remoção do drone ativo.
     */
    @Override
    public boolean perderDrone() {
        jogo.largarItensDroneNoFundo();
        if (!jogo.removerDroneAtivo()){
            return false;
        }
        DeepSeaLog.getInstance().log("Perdeu drone");
        if(!avaliarFimJogo()){
            changeState(DeepSeaState.SUPERFICIE_STATE);
            return true;
        }
        changeState(DeepSeaState.ACABOU_STATE);
        return true;
    }
}