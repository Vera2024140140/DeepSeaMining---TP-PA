package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public abstract class FossoState extends DeepSeaStateAdapter {

    public FossoState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    public boolean moverDroneFosso(Direcao dir) {
        boolean moveu = jogo.moverDroneFosso(dir);

        //verifica se a viagem chregou ao fim
        verificarFimViagem();
        if (!moveu) return false;

        //verifica se o drone morreu
        if ((jogo.getCombustivelDroneAtivo() <= 0) && (jogo.getIntegridadeDroneAtivo() < 0)) {
            //remover drone do hash set
            perderDrone();
            return true;
        }
        return true;
    }

    @Override
    public boolean perderDrone() {
        changeState(DeepSeaState.SUPERFICIE_STATE);
        return true;
    }

    //metodo implementado por Subida/Descida (classes filhas)
    protected  abstract void verificarFimViagem();
}
