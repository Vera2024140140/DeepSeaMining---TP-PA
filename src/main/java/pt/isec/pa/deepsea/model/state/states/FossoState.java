package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;


public abstract class FossoState extends DeepSeaStateAdapter {
    private static final long serialVersionUID = 50L;
    public FossoState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    public boolean perderDrone() {
        jogo.largarItensDroneNoFundo();
        if (!jogo.removerDroneAtivo()){
            return false;
        }
        if(!avaliarFimJogo()){
            changeState(DeepSeaState.SUPERFICIE_STATE);
            return true;
        }
        changeState(DeepSeaState.ACABOU_STATE);
        return true;
    }

    @Override
    public boolean mover(Direcao dir) {
        boolean moveu = jogo.moverDroneFosso(dir);

        if (!moveu) return false;

        //verifica se o drone morreu
        if ((jogo.getCombustivelDroneAtivo() <= 0) || (jogo.getIntegridadeDroneAtivo() <= 0)) {
            //remover drone do hash set
            perderDrone();
            return true;
        }
        return true;
    }

}
