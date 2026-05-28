package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.TipoComponente;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static pt.isec.pa.deepsea.model.state.DeepSeaState.SUPERFICIE_STATE;

/**
 * Contexto da máquina de estados (State Pattern).
 * Mantém o estado atual e delega todas as ações
 * para o {@link IDeepSeaState} corrente. É serializável
 * para suportar gravar/carregar jogo.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class DeepSeaContext implements Serializable {
    private static final long serialVersionUID = 40L;
    private Jogo jogo;
    private IDeepSeaState atual;

    public DeepSeaContext() {
        jogo = new Jogo(); //instancia de dados
        atual = SUPERFICIE_STATE.getInstance(this, jogo);
    }

    //package private -> testes
    DeepSeaContext(Jogo jogo) {
        this.jogo = jogo;
        atual = SUPERFICIE_STATE.getInstance(this, jogo);
    }

    /**
     * Altera o estado atual da máquina de estados.
     */
    void changeState(IDeepSeaState novoEstado) {
        DeepSeaLog.getInstance().log(
                String.format("Change State: %s => %s", atual.getState() ,novoEstado.getState())
        );
        this.atual = novoEstado;
    }

    /** Delega iniciarDescida ao estado atual. */
    public boolean iniciarDescida() {
        DeepSeaLog.getInstance().log("iniciarDescida");
        return atual.iniciarDescida();
    }

    /** Delega recolherMinerio ao estado atual. */
    public boolean recolherMinerio(){
        DeepSeaLog.getInstance().log("recolherMinerio");
        return atual.recolherMinerio();
    }

    /** Delega recolherArtefacto ao estado atual. */
    public boolean recolherArtefacto() {
        DeepSeaLog.getInstance().log("recolherArtefacto");
        return atual.recolherArtefacto();
    }

    /** Delega iniciarSubida ao estado atual. */
    public boolean iniciarSubida() {
        DeepSeaLog.getInstance().log("iniciarSubida");
        return atual.iniciarSubida();
    }

    /** Delega abrirOficina ao estado atual. */
    public boolean abrirOficina() {
        DeepSeaLog.getInstance().log("abrirOficina");
        return atual.abrirOficina();
    }

    /** Delega fecharOficina ao estado atual. */
    public boolean fecharOficina() {
        DeepSeaLog.getInstance().log("fecharOficina");
        return atual.fecharOficina();
    }

    /** Delega o movimento ao estado atual. */
    public boolean mover(Direcao dir) {
        DeepSeaLog.getInstance().log("mover (" + dir + ")");
        return atual.mover(dir);
    }

    /** Delega a seleção do drone ao estado atual. */
    public boolean selecionarDrone(int idDrone) {
        DeepSeaLog.getInstance().log("selecionarDrone (" + idDrone + ")");
        return atual.selecionarDrone(idDrone);
    }

    /** Delega o abastecimento do drone ao estado atual. */
    public boolean abastecerDrone(double litros) {
        DeepSeaLog.getInstance().log("abastecerDrone (" + litros + ")");
        return atual.abastecerDrone(litros);
    }

    /** Delega a reparação do drone ao estado atual. */
    public boolean repararDrone(int pontos) {
        DeepSeaLog.getInstance().log("repararDrone (" + pontos + ")");
        return atual.repararDrone(pontos);
    }

    /** Delega a melhoria do tanque ao estado atual. */
    public boolean melhorarTanqueDrone(){
        DeepSeaLog.getInstance().log("melhorarTanqueDrone ()");
        return atual.melhorarTanqueDrone();
    }

    /** Delega a melhoria da integridade ao estado atual. */
    public boolean melhorarIntegridadeDrone(){
        DeepSeaLog.getInstance().log("melhorarIntegridadeDrone ()");
        return atual.melhorarIntegridadeDrone();
    }

    public double getIntegridadeMaxDrone() {
        return jogo.getIntegridadeMaxDrone();
    }

    public List<String> getInfoDrones() {
        return jogo.getInfoDrones();
    }

    public int getArtefactosNavio() {
        return jogo.getArtefactosNavio();
    }

    public int getIdDroneAtivo() {
        return jogo.getIdDroneAtivo();
    }

    public int[][] getMatrizPuzzle() {
        return jogo.getMatrizPuzzle();
    }

    public int getMineriosDroneAtivo() {
        return jogo.getMineriosDroneAtivo();
    }

    public double getMaxCombustivelDroneAtivo() {
        return jogo.getMaxCombustivelDroneAtivo();
    }

    public int getMovimentosRestantesPuzzle()  {
        return jogo.getMovimentosRestantesPuzzle();
    }

    public int getLinhaDroneAtivo() {
        return jogo.getLinhaDroneAtivo();
    }
    public int getColunaDroneAtivo() {
        return jogo.getColunaDroneAtivo();
    }

    public int getArtefactosDroneAtivo() {
        return jogo.getArtefactosDroneAtivo();
    }

    // ===================================================================
    // --- MÉTODOS DE ACESSO AOS DADOS ---
    // ===================================================================

    public DeepSeaState getState() {
        if (atual == null)
            return null;
        return atual.getState();
    }

    public double getCombustivelNavio() {
        return jogo.getCombustivelNavio();
    }

    public int getMineriosNavio() {
        return jogo.getMineriosNavio();
    }

    public List<Integer> getIdsArtefactosNavio() {
        return jogo.getIdsArtefactosNavio();
    }

    public Set<Integer> getIdsDronesNavio() {
        return jogo.getIdsDronesNavio();
    }

    public double getCombustivelDroneAtivo() {
        return jogo.getCombustivelDroneAtivo();
    }

    public int getIntegridadeDroneAtivo() {
        return jogo.getIntegridadeDroneAtivo();
    }

    // ===================================================================
    // ---  Grelhas Fosso/Fundo
    // ===================================================================

    public TipoComponente[][] getMapaFosso(int lSup, int cSup) {
        return jogo.getMapaFosso(lSup, cSup);
    }

    public TipoComponente[][] getMapaFundo(int lSup, int cSup) {
        return jogo.getMapaFundo(lSup, cSup);
    }

    public boolean posicaoComMinerio() {
        return jogo.posicaoComMinerio();
    }

    public boolean posicaoComArtefacto() {return jogo.posicaoComArtefacto(); }

    public int getLinhaNavioSuperficie() {
        return jogo.getLinhaAtualNavio();
    }
    public int getColunaNavioSuperficie() {
        return jogo.getColunaAtualNavio();
    }

    public int[][] getMapaPistasArtefactos() {
        return jogo.getMapaPistasArtefactos();
    }

    public boolean isCelulaFundoRevelada(int lsup, int csup, int lF, int cF) {
        return jogo.isCelulaFundoRevelada(lsup,csup,lF,cF);
    }

    public void resetContadores() {
        jogo.resetContadores();
    }
}