package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import java.io.Serializable;


/**
 * Classe Abstrata que fornece uma implementação por omissão para a
 * interface {@link IDeepSeaState}.
 * <p>
 * Aplica padrão de desenho 'Adapter' no contexto do padrão 'State' (FSM).
 * O objetivo é evitar que estados concretos como (FundoState, SubidaState),
 * tenham de implementar métodos de transição que não lhes dizem respeito.
 * Por omissão todos os métodos retornam {@code false}, indicando que a operação
 * é inválida no estado atual, bloqueando assim comportamentos indesejados.
 * </p>
 * @author Vera2024140140
 * @author Rafael2024143044
 */
public abstract class DeepSeaStateAdapter implements IDeepSeaState, Serializable {
    private static final long serialVersionUID = 41L;
    /**
     * Referência para o contacto da máquina de estados, utilizada para acionar transições
     */
    protected DeepSeaContext context;
    /**
     * Referência para o modelo de dados central do jogo
     */
    protected Jogo jogo;

    /**
     * Construtor base para os estados da máquina
     *
     * @param context O contexto da FSM que gere o estado atual
     * @param jogo Modelo de dados sobre o qual o estado opera
     */
    protected DeepSeaStateAdapter(DeepSeaContext context, Jogo jogo){
        this.context = context;
        this.jogo = jogo;
    }

    /**
     * Solicita o contexto por uma transição de estado.
     * Utiliza o FM do enum para instanciar o estado correto.
     *
     * @param newState O identificador (ENUM) do novo estado pretendido
     */
    protected void changeState(DeepSeaState newState){
        context.changeState(newState.getInstance(this.context, this.jogo));
    }

    //=======================================================
    // --- implementações por omissão ---
    //=======================================================
    @Override
    public boolean iniciarDescida() {
        return false;
    }

    @Override
    public boolean chegarFundo() {
        return false;
    }

    @Override
    public boolean iniciarSubida() {
        return false;
    }

    @Override
    public boolean apanharArtefacto() {
        return false;
    }
    @Override
    public boolean recolherMinerio() { return false; }

    @Override
    public boolean fimPuzzle() {
        return false;
    }

    @Override
    public boolean subirSuperficie() {
        return false;
    }

    @Override
    public boolean perderDrone() {
        return false;
    }

    @Override
    public boolean avaliarFimJogo() {
        return false;
    }

    @Override
    public boolean abrirOficina() {
        return false;
    }

    @Override
    public boolean fecharOficina() {
        return false;
    }

    @Override
    public boolean moverNavio(Direcao dir) {
        return false;
    }

    @Override
    public boolean moverDroneFosso(Direcao dir) {
        return false;
    }

    @Override
    public boolean moverDroneFundo(Direcao dir){return false;}
    @Override
    public boolean selecionarDrone(int idDrone) {
        return false;
    }

    @Override
    public boolean abastecerDrone(double litros) {
        return false;
    }

    @Override
    public boolean repararDrone(int integridadde) {
        return false;
    }

    @Override
    public boolean melhorarTanqueDrone() {
        return false;
    }
    @Override
    public boolean melhorarIntegridadeDrone(){
        return false;
    }

    @Override
    public boolean moverPeca(Direcao dir) {
        return false;
    }

}
