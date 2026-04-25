package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;
public abstract class DeepSeaStateAdapter implements IDeepSeaState {

    protected DeepSeaContext context;


    protected Jogo jogo;

    protected DeepSeaStateAdapter(DeepSeaContext context, Jogo jogo){
        this.context = context;
        this.jogo = jogo;
    }

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
