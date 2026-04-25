package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

/**
 * Estado final do jogo, atingido após vitória (recolha de todos os artefactos)
 * ou derrota (perda de todos os drones ou navio fica sem combustivel).
 * <p>
 * Não permite qualquer transição adicional: todas as operações herdadas do
 * {@link DeepSeaStateAdapter} devolvem {@code false}.
 *
 * @author Diogo2024152576
 */
public class AcabouState extends DeepSeaStateAdapter {

    /**
     * Cria uma nova instância do estado Acabou.
     *
     * @param context contexto da máquina de estados
     * @param jogo    modelo de dados do jogo
     */
    public AcabouState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /**
     * @return o identificador deste estado ({@link DeepSeaState#ACABOU_STATE})
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.ACABOU_STATE;
    }

    /**
     * Confirma que o jogo já terminou.
     *
     * @return sempre {@code true} porque este é o estado final do jogo
     */
    @Override
    public boolean avaliarFimJogo() {
        return true;
    }
}
