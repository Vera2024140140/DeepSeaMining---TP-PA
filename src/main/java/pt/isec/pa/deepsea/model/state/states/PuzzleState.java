package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

public class PuzzleState extends DeepSeaStateAdapter {

    public PuzzleState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    @Override
    public boolean moverPeca(Direcao dir) {
        boolean moveu = jogo.moverPecaPuzzle(dir);

        if (!moveu) return false; // movimento invalido

        //verifcar condições de fim de jogo do puzzle
        if (jogo.isPuzzleResolvido())
            fimPuzzle(); //ganhou
        else if (jogo.isPuzzleSemMovimentos())
            iniciarSubida(); //ficou sem mov's
        return true;
    }

    //transicao de saida
    @Override
    public boolean fimPuzzle() {
        jogo.recolherArtefactoPuzzle(); //recolher o artefacto e limpa o puzzle da memoria

        changeState(DeepSeaState.FUNDO_STATE);
        return true;
    }

    @Override
    public boolean iniciarSubida() {
        jogo.limparPuzzle(); //quando perde limpa o puzzle da memória
        changeState(DeepSeaState.SUBIDA_STATE);
        return true;
    }

    @Override
    public DeepSeaState getState() {
        return DeepSeaState.PUZZLE_STATE;
    }
}