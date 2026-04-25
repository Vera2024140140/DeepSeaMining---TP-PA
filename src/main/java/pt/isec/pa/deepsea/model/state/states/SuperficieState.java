package pt.isec.pa.deepsea.model.state.states;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.state.DeepSeaStateAdapter;

/**
 * Estado inicial do jogo, em que o navio está a navegar na superfície.
 * <p>
 * Neste estado é possível mover o navio pela grelha da superfície, abrir a
 * oficina para preparar drones, iniciar a descida de um drone para o fosso
 * e avaliar se o jogo chegou ao fim.
 *
 * @author Diogo2024152576
 */
public class SuperficieState extends DeepSeaStateAdapter {

    /**
     * Cria uma nova instância do estado Superfície.
     *
     * @param context contexto da máquina de estados
     * @param jogo    modelo de dados do jogo
     */
    public SuperficieState(DeepSeaContext context, Jogo jogo) {
        super(context, jogo);
    }

    /**
     * @return o identificador deste estado ({@link DeepSeaState#SUPERFICIE_STATE})
     */
    @Override
    public DeepSeaState getState() {
        return DeepSeaState.SUPERFICIE_STATE;
    }

    /**
     * Move o navio na grelha da superfície, consumindo combustível.
     *
     * @param dir direção do movimento
     * @return {@code true} se o movimento foi efetuado; {@code false} se a
     *         direção é inválida, se não há combustível suficiente ou se a
     *         posição resultante está fora da grelha
     */
    @Override
    public boolean moverNavio(Direcao dir) {
        return jogo.moverNavio(dir);
    }

    /**
     * Abre a oficina, transitando para o estado {@link DeepSeaState#OFICINA_STATE}.
     *
     * @return {@code true} — a transição é sempre permitida a partir da superfície
     */
    @Override
    public boolean abrirOficina() {
        changeState(DeepSeaState.OFICINA_STATE);
        return true;
    }

    /**
     * Inicia a descida do drone ativo para o fosso.
     * <p>
     * Só tem efeito se existir um drone ativo com combustível e integridade
     * suficientes. Se possível, posiciona o drone no topo do fosso e transita
     * para {@link DeepSeaState#DESCIDA_STATE}.
     *
     * @return {@code true} se a descida foi iniciada; {@code false} caso contrário
     */
    @Override
    public boolean iniciarDescida() {
        if (!jogo.podeIniciarDescida()) {
            return false;
        }
        if (!jogo.meteDroneNoFosso()){
            return false;
        }
        changeState(DeepSeaState.DESCIDA_STATE);
        return true;
    }

    /**
     * Avalia se o jogo deve terminar, transitando para
     * {@link DeepSeaState#ACABOU_STATE} caso o mesmo termine.
     * <p>
     * O jogo acaba quando deixam de existir drones / navio fica sem combustivel (derrota) ou
     * quando foram recolhidos todos os artefactos (vitória).
     *
     * @return {@code true} se o jogo terminou e houve transição de estado;
     *         {@code false} se o jogo continua
     */
    @Override
    public boolean avaliarFimJogo() {
        if (jogo.derrota() || jogo.vitoria()) {
            changeState(DeepSeaState.ACABOU_STATE);
            return true;
        }
        return false;
    }
}
