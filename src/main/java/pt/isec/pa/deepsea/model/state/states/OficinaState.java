package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;
/**
 * Representa o estado da Oficina no jogo.
 * <p>
 * Neste estado, o navio encontra-se na superfície e o jogador pode fazer
 * de manutenção e melhoria dos seus drones. É possível selecionar diferentes drones,
 * abastecê-los com combustível, reparar os seus cascos
 * e aplicar melhorias de capacidade máxima do tanque e da integridade do casco.
 * Todas as ações logísticas delegam as operações para a classe {@link Jogo}.
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class OficinaState extends DeepSeaStateAdapter {
    private static final long serialVersionUID = 55L;
    /**
     * Cria uma instância do estado da Oficina.
     * @param context contexto da máquina de estados
     * @param jogo    modelo de dados do jogo
     */
    public OficinaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }
    /**
     * @return o identificador deste estado ({@link DeepSeaState#OFICINA_STATE})
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.OFICINA_STATE;
    }
    /**
     * Fecha a oficina.
     * Transita de volta para a superfície.
     *
     * @return Sempre {@code true}, confirmando que a transição foi efetuada.
     */
    @Override
    public boolean fecharOficina() {
        changeState(DeepSeaState.SUPERFICIE_STATE);
        return true;
    }
    /**
     * Seleciona um drone específico da lista de drones para ser o alvo das próximas
     * operações de manutenção.
     *
     * @param idDrone O identificador único do drone a selecionar.
     * @return {@code true} se o drone existir e for selecionado com sucesso;
     * {@code false} caso o identificador não seja encontrado.
     */
    @Override
    public boolean selecionarDrone(int idDrone) {
        return jogo.selecionarDrone(idDrone);
    }

    /**
     * Transfere combustível dos depósitos do navio para o drone atualmente ativo.
     *
     * @param litros A quantidade de combustível a transferir.
     * @return {@code true} se o abastecimento foi bem-sucedido;
     * {@code false} se não houver combustível suficiente no navio,
     * se nenhum drone estiver ativo, ou se o tanque do drone já estiver cheio.
     */
    @Override
    public boolean abastecerDrone(double litros){
        boolean abasteceu = jogo.abastecerDroneAtivo(litros);

        if (abasteceu) {
            avaliarFimJogo();
        }
        return abasteceu;
    }

    /**
     * Gasta combustível do navio para reparar a integridade do casco do drone ativo.
     *
     * @param integridade o valor de integridade a recuperar.
     * @return {@code true} se a reparação for bem-sucedida;
     * {@code false} caso o navio não tenha recursos suficientes ou a integridade já esteja no máximo.
     */
    @Override
    public boolean repararDrone(int integridade){
        boolean integridadeNavio = jogo.repararIntegridadeDroneAtivo(integridade);
        if (integridadeNavio) {
            avaliarFimJogo();
        }
        return  integridadeNavio;
    }

    /**
     * Gasta minérios do navio para aumentar a capacidade máxima
     * do tanque de combustível do drone ativo.
     *
     * @return {@code true} se a melhoria foi aplicada;
     * {@code false} se o jogador não tiver minérios suficientes.
     */
    @Override
    public boolean melhorarTanqueDrone(){
        return jogo.melhorarTanqueDroneAtivo();
    }
    /**
     * Gasta minérios do navio para aumentar permanentemente a integridade máxima
     * do casco do drone ativo (restaurando também esse mesmo valor na integridade atual).
     *
     * @return {@code true} se a melhoria foi aplicada;
     * {@code false} se o jogador não tiver minérios suficientes.
     */
    @Override
    public boolean melhorarIntegridadeDrone(){
        return jogo.melhorarIntegridadeDroneAtivo();
    }
}