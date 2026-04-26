
package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
/**
 * Representa o estado de subida do 'drone' em direção ao navio.
 *
 * <p>
 * Para além de monitorizar a chega à superfície por parte do 'drone', este
 * estado é também responsável por garantir que todos os itens transportados
 * pelo 'drone' ('minérios' & 'artefacts') são descarregados no navio,
 * quando o mesmo atingir a superfície.
 * </p>
 * @author Rafael2024143044
 */
public class SubidaState extends FossoState {

    /**
     * Construtor
     *
     * @param context Referência para o contexto da Máquina de Estados
     * @param jogo Referência para o modelo de dados central
     */
    public SubidaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /**
     * Verifica se a viagem terminou ('drone' chegou à última linha do fosso)
     * Caso tenha chegado, transita para o estado da superfície.
     */

    @Override
    public boolean moverDroneFosso(Direcao dir) {
        if (dir == Direcao.CIMA && jogo.droneNoTopo()) {
            return subirSuperficie();
        }
        return super.moverDroneFosso(dir);
    }

    /**
     * Descarrega os recursos recolhidos no navio e faz a transição de
     * estado para a superfície.
     *
     * @return true, confirmando a transição de estado, após descarregar os itens.
     */
    @Override


    /**
     * Devolve o identificador do estado
     *
     * @return {@link DeepSeaState#SUBIDA_STATE}
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.SUBIDA_STATE;
    }
}