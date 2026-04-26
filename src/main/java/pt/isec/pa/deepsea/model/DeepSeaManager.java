package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

import java.io.*;
import java.util.List;
import java.util.Set;

public class DeepSeaManager {
    private DeepSeaContext context;

    public DeepSeaManager() {
        this.context = new DeepSeaContext();
    }

    // delegação de ações
    public boolean iniciarDescida() {
        return context.iniciarDescida();
    }
    public boolean moverDroneFosso(Direcao dir) {
        return context.moverDroneFosso(dir);
    }
    public boolean moverDroneFundo(Direcao dir) {
        return context.moverDroneFundo(dir);
    }
    public boolean chegarFundo() {
        return context.chegarFundo();
    }
    public boolean apanharArtefacto() {
        return context.apanharArtefacto();
    }
    public boolean recolherMinerio(){
        return context.recolherMinerio();
    }
    public boolean iniciarSubida() {
        return context.iniciarSubida();
    }
    public boolean fimPuzzle() {
        return context.fimPuzzle();
    }
    public boolean subirSuperficie() {
        return context.subirSuperficie();
    }
    public boolean perderDrone() {
        return context.perderDrone();
    }
    public boolean avaliarFimJogo() {
        return context.avaliarFimJogo();
    }
    public boolean abrirOficina() {
        return context.abrirOficina();
    }
    public boolean fecharOficina() {
        return context.fecharOficina();
    }
    public boolean moverNavio(Direcao dir) { return context.moverNavio(dir);}
    public boolean selecionarDrone(int idDrone) {
        return context.selecionarDrone(idDrone);
    }
    public boolean abastecerDrone(double litros) {
        return context.abastecerDrone(litros);
    }
    public boolean repararDrone(int pontos) {
        return context.repararDrone(pontos);
    }
    public boolean melhorarTanqueDrone(){
        return context.melhorarTanqueDrone();
    }
    public boolean melhorarIntegridadeDrone(){
        return context.melhorarIntegridadeDrone();
    }
    public boolean moverPeca(Direcao dir) {
        return context.moverPeca(dir);
    }

    //===========================================================
    //consulta de dados (getter's seguros de dados para a UI)
    //===========================================================

    public DeepSeaState getState() {
        return context.getState();
    }

    public double getCombustivelNavio() {
        return context.getCombustivelNavio();
    }

    public int getMineriosNavio() {
        return context.getMineriosNavio();
    }

    public List<Integer> getIdsArtefactosNavio() {
        return List.copyOf(context.getIdsArtefactosNavio());
    }

    public Set<Integer> getIdsDronesNavio() {
        return Set.copyOf(context.getIdsDronesNavio());
    }

    public double getCombustivelDroneAtivo() {
        return context.getCombustivelDroneAtivo();
    }

    public int getIntegridadeDroneAtivo() {
        return context.getIntegridadeDroneAtivo();
    }

    public TipoComponente[][] getMapaFosso(int lSup, int cSup) {
        return  context.getMapaFosso(lSup, cSup);
    }

    public TipoComponente[][] getMapaFundo(int lSup, int cSup) {
        return  context.getMapaFundo(lSup, cSup);
    }

    //===========================================================
    // --- log ---
    //===========================================================
    public List<String> getLog() {
        return DeepSeaLog.getInstance().getLog();
    }

    public void limparLog() {
        DeepSeaLog.getInstance().reset();
    }

    //===========================================================
    // --- Serialização & Save 6 Load ---
    //===========================================================
    public boolean gravarJogo(){
        try(FileOutputStream fr = new FileOutputStream(Settings.FICHEIRO_SAVE);
            BufferedOutputStream br = new BufferedOutputStream(fr);
            ObjectOutputStream obj = new ObjectOutputStream(br);
        ){
            obj.writeObject(context);
            return true;
        }catch(IOException e){
            return false;
        }
    }
    public boolean carregarJogo(){
        try(FileInputStream fr = new FileInputStream(Settings.FICHEIRO_SAVE);
            BufferedInputStream br = new BufferedInputStream(fr);
            ObjectInputStream obj = new ObjectInputStream(br);
        ){
            context = (DeepSeaContext) obj.readObject();
            return true;
        }catch(IOException | ClassNotFoundException e){
            return false;
        }
    }
}
