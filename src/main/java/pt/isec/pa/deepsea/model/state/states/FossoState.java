package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

/**
 * Contém a lógica comum de movimento e perda de ‘drone’,
 * partilhada entre {@link DescidaState} e {@link SubidaState}.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public abstract class FossoState extends DeepSeaStateAdapter {
    private static final long serialVersionUID = 50L;
    public FossoState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /** Larga itens do drone, remove-o da frota e transita de estado. */
    @Override
    public boolean perderDrone() {
        jogo.largarItensDroneNoFundo();
        if (!jogo.removerDroneAtivo()){
            return false;
        }
        DeepSeaLog.getInstance().log("Perdeu drone");
        if(!avaliarFimJogo()){
            changeState(DeepSeaState.SUPERFICIE_STATE);
            return true;
        }
        changeState(DeepSeaState.ACABOU_STATE);
        return true;
    }

    /** Move o drone no fosso e verifica se foi destruído. */
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
