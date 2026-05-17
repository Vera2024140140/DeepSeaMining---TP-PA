package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

/**
 * Estado de descida do drone no fosso marinho.
 * <p>
 * Esta classe herda a lógica de movimentação da do {@link FossoState} e
 * verifica quando a que o drone chega ao fundo do mar.
 * </p>
 * @author Rafael2024143044
 */
public class DescidaState extends FossoState {
    private static final long serialVersionUID = 51L;
    /**
     *
     * Construtor do estado de descida.
     *
     * @param context Referência para o contexto da Máquina de estados
     * @param jogo Referêmncia ao modelo de central de dados
     */
    public DescidaState(DeepSeaContext context, Jogo jogo) {

        super(context, jogo);
        jogo.gerarObstaculosFosso();
    }

    /**
     * Verifica se a viagem já terminou, isto é se o ‘drone’ já chegou
     * ao fundo (ultima linha da grelha do fosso) e caso tenha chegado
     * faz uma transição de estado.
     */
    @Override
    public boolean mover(Direcao dir) {
        if (dir == Direcao.BAIXO && jogo.droneNoFundoFosso()) {
            return chegarFundo();
        }
        return super.mover(dir);
    }

    /**
     * Transição de estado, quando o drone chega com sucesso ao fundo marinho.
     *
     * @return true, confirmando a transição de estado.
     */
    private  boolean chegarFundo() {
        if(!jogo.meteDroneNoFundo())
            return false;
        jogo.gerarMonstros();
        changeState(DeepSeaState.FUNDO_STATE);
        return true;
    }

    /**
     * Devolve o identificador deste estado
     *
     * @return {@link DeepSeaState#DESCIDA_STATE}
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.DESCIDA_STATE;
    }
}