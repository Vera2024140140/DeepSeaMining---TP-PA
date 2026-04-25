package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class DescidaState extends DeepSeaStateAdapter {
    public DescidaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    public DeepSeaState getState() {
        return DeepSeaState.DESCIDA_STATE;
    }
}