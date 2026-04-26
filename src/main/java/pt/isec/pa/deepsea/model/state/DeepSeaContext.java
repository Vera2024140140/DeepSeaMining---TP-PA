package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

import java.util.List;
import java.util.Set;

import static pt.isec.pa.deepsea.model.state.DeepSeaState.SUPERFICIE_STATE;

public class DeepSeaContext {
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

    void changeState(IDeepSeaState novoEstado) {
        DeepSeaLog.getInstance().log(
                String.format("Change State: %s => %s", atual.getState() ,novoEstado.getState())
        );
        this.atual = novoEstado;
        if (novoEstado.getState() == DeepSeaState.SUPERFICIE_STATE) {
            novoEstado.avaliarFimJogo();
        }
    }

    public boolean iniciarDescida() {
        DeepSeaLog.getInstance().log("iniciarDescida");
        return atual.iniciarDescida();
    }

    public boolean moverDroneFosso(Direcao dir) {
        DeepSeaLog.getInstance().log("moverDroneFosso (" + dir + ")");
        return atual.moverDroneFosso(dir);
    }

    public boolean moverDroneFundo(Direcao dir) {
        DeepSeaLog.getInstance().log("moverDroneFundo (" + dir + ")");
        return atual.moverDroneFundo(dir);
    }

    public boolean chegarFundo() {
        DeepSeaLog.getInstance().log("chegarFundo");
        return atual.chegarFundo();
    }

    public boolean apanharArtefacto() {
        DeepSeaLog.getInstance().log("apanharArtefacto");
        return atual.apanharArtefacto();
    }

    public boolean recolherMinerio(){
        DeepSeaLog.getInstance().log("recolherMinerio");
        return atual.recolherMinerio();
    }

    public boolean iniciarSubida() {
        DeepSeaLog.getInstance().log("iniciarSubida");
        return atual.iniciarSubida();
    }

    public boolean fimPuzzle() {
        DeepSeaLog.getInstance().log("fimPuzzle");
        return atual.fimPuzzle();
    }

    public boolean subirSuperficie() {
        DeepSeaLog.getInstance().log("subirSuperficie");
        return atual.subirSuperficie();
    }

    public boolean perderDrone() {
        DeepSeaLog.getInstance().log("perderDrone");
        return atual.perderDrone();
    }

    public boolean avaliarFimJogo() {
        DeepSeaLog.getInstance().log("avaliarFimJogo");
        return atual.avaliarFimJogo();
    }

    public boolean abrirOficina() {
        DeepSeaLog.getInstance().log("abrirOficina");
        return atual.abrirOficina();
    }

    public boolean fecharOficina() {
        DeepSeaLog.getInstance().log("fecharOficina");
        return atual.fecharOficina();
    }

    public boolean moverNavio(Direcao dir) {
        DeepSeaLog.getInstance().log("moverNavio (" + dir + ")");
        return atual.moverNavio(dir);
    }

    public boolean selecionarDrone(int idDrone) {
        DeepSeaLog.getInstance().log("selecionarDrone (" + idDrone + ")");
        return atual.selecionarDrone(idDrone);
    }

    public boolean abastecerDrone(double litros) {
        DeepSeaLog.getInstance().log("abastecerDrone (" + litros + ")");
        return atual.abastecerDrone(litros);
    }

    public boolean repararDrone(int pontos) {
        DeepSeaLog.getInstance().log("repararDrone (" + pontos + ")");
        return atual.repararDrone(pontos);
    }

    public boolean melhorarTanqueDrone(){
        DeepSeaLog.getInstance().log("melhorarTanqueDrone ()");
        return atual.melhorarTanqueDrone();
    }

    public boolean melhorarIntegridadeDrone(){
        DeepSeaLog.getInstance().log("melhorarIntegridadeDrone ()");
        return atual.melhorarIntegridadeDrone();
    }

    public boolean moverPeca(Direcao dir) {
        DeepSeaLog.getInstance().log("moverPeca (" + dir + ")");
        return atual.moverPeca(dir);
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
}