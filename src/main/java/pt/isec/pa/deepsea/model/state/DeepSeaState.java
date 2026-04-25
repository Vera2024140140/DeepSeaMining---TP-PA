package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.states.*;

public enum DeepSeaState {
    SUPERFICIE_STATE,DESCIDA_STATE,FUNDO_STATE,SUBIDA_STATE,PUZZLE_STATE,OFICINA_STATE,ACABOU_STATE;
    public IDeepSeaState getInstance(DeepSeaContext context, Jogo jogo){
        return switch (this){
            case SUPERFICIE_STATE -> new SuperficieState(context,jogo);
            case DESCIDA_STATE -> new DescidaState(context,jogo);
            case FUNDO_STATE -> new FundoState(context,jogo);
            case SUBIDA_STATE -> new SubidaState(context,jogo);
            case PUZZLE_STATE -> new PuzzleState(context,jogo);
            case OFICINA_STATE -> new OficinaState(context,jogo);
            case ACABOU_STATE -> new AcabouState(context,jogo);
        };
    }
}
