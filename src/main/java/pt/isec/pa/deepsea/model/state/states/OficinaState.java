package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class OficinaState extends DeepSeaStateAdapter {
    public OficinaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    public DeepSeaState getState() {
        return DeepSeaState.OFICINA_STATE;
    }
}