package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

/**
 * Representa o estado do minijogo (Puzzle) para a recolha de artefactos.
 *
 * <p>
 * Este estado faz a gestão do desafio, interceta comandos inválidos de peças,
 * avalia de forma contínua condições de vitória ('puzzle' ordenado)
 * ou de derrota, caso se esgotem os movimentos.
 * </p>
 * @author Rafael2024143044
 * @author Diogo2024152576
 */
public class PuzzleState extends DeepSeaStateAdapter {

    /**
     * Construtor do PuzzleState
     *
     * @param context Referência para o contexto da Máquina de Estados
     * @param jogo Referência para o modelo de dados central
     */
    public PuzzleState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /**
     * Tenta mover a peça ''vazia'' do 'puzzle' na direção dada pelo utilizador.
     * Caso o movimento seja válido, avalia de imediato se o utilizador já possui
     * o 'puzzle' ordenado, ou se perdeu o desafio (ficou sem movimentos),
     * acionando as transições de estado.
     *
     * @param dir Direção para a qual o utilizador quer mover a peça
     * @return
     * {@code true} se o movimento for válido e processado
     * {@code false} se vai contra os limites da grelha
     */
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

    /**
     * Acionado quando o jogador finaliza o 'puzzle' com sucesso
     * Ordena ao modelo para recolher o 'artefacto' e regressa à
     * exploração do fundo marinho.
     *
     * @return true, confirma a transição para o fundo marinho
     */
    //transicao de saida
    @Override
    public boolean fimPuzzle() {
        jogo.recolherArtefactoPuzzle(); //recolher o artefacto e limpa o puzzle da memoria

        changeState(DeepSeaState.FUNDO_STATE);
        return true;
    }

    /**
     * Acionado quando o jogador esgota os movimentos possíveis (Derrota).
     * Força o drone a abandonar o artefacto e iniciar a subida automática
     *
     * @return true, confirmando a transição 'forçada' para a subida
     */
    @Override
    public boolean iniciarSubida() {
        jogo.limparPuzzle(); //quando perde limpa o puzzle da memória
        changeState(DeepSeaState.SUBIDA_STATE);
        return true;
    }

    /**
     * Devolve o identificador do estado
     *
     * @return  {@link DeepSeaState#PUZZLE_STATE}
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.PUZZLE_STATE;
    }
}