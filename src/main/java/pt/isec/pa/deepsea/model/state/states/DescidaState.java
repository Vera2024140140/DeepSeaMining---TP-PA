package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class DescidaState extends FossoState {

    public DescidaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    protected void verificarFimViagem() {
        if (jogo.droneChegouFundo()) {
            chegarFundo();
        }
    }

    @Override
    public boolean chegarFundo() {
        changeState(DeepSeaState.FUNDO_STATE);
        return true;
    }

    @Override
    public DeepSeaState getState() {
        return DeepSeaState.DESCIDA_STATE;
    }
}