
package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class FundoState extends DeepSeaStateAdapter {
    public FundoState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }


    @Override
    public DeepSeaState getState() {
        return DeepSeaState.FUNDO_STATE;
    }
}