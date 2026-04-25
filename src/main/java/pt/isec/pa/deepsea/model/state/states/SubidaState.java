
package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class SubidaState extends FossoState {
    public SubidaState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    protected void verificarFimViagem() {
        if (jogo.droneNoTopo()) {
            subirSuperficie();
        }
    }

    @Override
    public boolean subirSuperficie() {
        jogo.descarregarDroneNavio();

        changeState(DeepSeaState.SUPERFICIE_STATE);
        return true;
    }

    @Override
    public DeepSeaState getState() {
        return DeepSeaState.SUBIDA_STATE;
    }
}