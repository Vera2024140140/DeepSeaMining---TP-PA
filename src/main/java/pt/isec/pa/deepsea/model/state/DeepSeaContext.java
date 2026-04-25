package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
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
        this.atual = novoEstado;
    }

    public boolean iniciarDescida() {
        return atual.iniciarDescida();
    }

    public boolean moverDroneFosso(Direcao dir) {
        return atual.moverDroneFosso(dir);
    }

    public boolean moverDroneFundo(Direcao dir) {
        return atual.moverDroneFundo(dir);
    }

    public boolean chegarFundo() {
        return atual.chegarFundo();
    }

    public boolean apanharArtefacto() {
        return atual.apanharArtefacto();
    }

    public boolean recolherMinerio(){
        return atual.recolherMinerio();
    }

    public boolean iniciarSubida() {
        return atual.iniciarSubida();
    }

    public boolean fimPuzzle() {
        return atual.fimPuzzle();
    }

    public boolean subirSuperficie() {
        return atual.subirSuperficie();
    }

    public boolean perderDrone() {
        return atual.perderDrone();
    }

    public boolean avaliarFimJogo() {
        return atual.avaliarFimJogo();
    }

    public boolean abrirOficina() {
        return atual.abrirOficina();
    }

    public boolean fecharOficina() {
        return atual.fecharOficina();
    }

    public boolean moverNavio(Direcao dir) {
        return atual.moverNavio(dir);
    }

    public boolean selecionarDrone(int idDrone) {
        return atual.selecionarDrone(idDrone);
    }

    public boolean abastecerDrone(double litros) {
        return atual.abastecerDrone(litros);
    }

    public boolean repararDrone(int pontos) {
        return atual.repararDrone(pontos);
    }

    public boolean melhorarTanqueDrone(){
        return atual.melhorarTanqueDrone();
    }
    public boolean melhorarIntegridadeDrone(){
        return atual.melhorarIntegridadeDrone();
    }

    public boolean moverPeca(Direcao dir) {
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