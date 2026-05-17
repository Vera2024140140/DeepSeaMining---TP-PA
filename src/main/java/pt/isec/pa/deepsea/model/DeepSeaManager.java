package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.elementos.Monstro;
import pt.isec.pa.deepsea.model.data.elementos.Obstaculo;
import pt.isec.pa.deepsea.model.data.jogo.Drone;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;
import java.util.Set;

public class DeepSeaManager {

    public static final String PROP_STATE   = "state";
    public static final String PROP_NAVIO   = "navio";
    public static final String PROP_DRONE   = "drone";
    public static final String PROP_FUNDO   = "fundo";
    public static final String PROP_FOSSO   = "fosso";
    public static final String PROP_PUZZLE  = "puzzle";
    public static final String PROP_OFICINA = "oficina";
    public static final String PROP_LOG     = "log";
    public static final String PROP_GAME    = "game";

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private DeepSeaContext context;

    public DeepSeaManager() {
        this.context = new DeepSeaContext();
    }

    public void addPropertyChangeListener(String prop, PropertyChangeListener l) {
        pcs.addPropertyChangeListener(prop, l);
    }

    public void removePropertyChangeListener(String prop, PropertyChangeListener l) {
        pcs.removePropertyChangeListener(prop, l);
    }

    private void fire(String prop) { pcs.firePropertyChange(prop, null, null); }

    private void fireStateIfChanged(DeepSeaState antes) {
        DeepSeaState depois = context.getState();
        pcs.firePropertyChange(PROP_STATE, antes, depois);
        if (depois == DeepSeaState.SUPERFICIE_STATE || depois == DeepSeaState.ACABOU_STATE){
            fire(PROP_NAVIO);
        }
        if (depois == DeepSeaState.PUZZLE_STATE) {
            fire(PROP_PUZZLE);
        }
    }

    // ===========================================================
    // ações
    // ===========================================================

    public boolean iniciarDescida() {
        DeepSeaState antes = context.getState();
        boolean desceu = context.iniciarDescida();
        if (desceu) { fireStateIfChanged(antes); fire(PROP_NAVIO); fire(PROP_DRONE); fire(PROP_LOG); }
        return desceu;
    }

    public boolean iniciarSubida() {
        DeepSeaState antes = context.getState();
        boolean subiu = context.iniciarSubida();
        if (subiu) { fireStateIfChanged(antes); fire(PROP_DRONE); fire(PROP_FOSSO); fire(PROP_LOG); }
        return subiu;
    }

    public boolean mover(Direcao dir) {
        DeepSeaState antes = context.getState();
        boolean ok = context.mover(dir);
        if (!ok) return false;
        switch (antes) {
            case SUPERFICIE_STATE -> fire(PROP_NAVIO);
            case FUNDO_STATE -> { fire(PROP_DRONE); fire(PROP_FUNDO); }
            case DESCIDA_STATE, SUBIDA_STATE -> { fire(PROP_DRONE); fire(PROP_FOSSO); }
            case PUZZLE_STATE -> fire(PROP_PUZZLE);
            default -> {}
        }
        fireStateIfChanged(antes);
        fire(PROP_LOG);
        return true;
    }

    public boolean recolherMinerio(){
        boolean recolheu = context.recolherMinerio();
        if(recolheu) {
            fire(PROP_FUNDO);
            fire(PROP_DRONE);
            fire(PROP_LOG);
        }
        return recolheu;
    }

    public boolean abrirOficina() {
        DeepSeaState antes = context.getState();
        boolean abriu = context.abrirOficina();
        if (abriu){
            fireStateIfChanged(antes);
            fire(PROP_LOG);
        }
        return abriu;
    }

    public boolean fecharOficina() {
        DeepSeaState antes = context.getState();
        boolean fechou = context.fecharOficina();
        if (fechou){
            fireStateIfChanged(antes);
            fire(PROP_LOG);
        }
        return fechou;
    }

    public boolean selecionarDrone(int idDrone) {
        boolean selecionou = context.selecionarDrone(idDrone);
        if (selecionou){
            fire(PROP_OFICINA);
            fire(PROP_DRONE);
            fire(PROP_NAVIO);
            fire(PROP_LOG);
        }
        return selecionou;
    }
    public boolean abastecerDrone(double litros) {
        DeepSeaState antes = context.getState();
        boolean abasteceu = context.abastecerDrone(litros);
        if (abasteceu){
            fire(PROP_DRONE);
            fire(PROP_NAVIO);
            fire(PROP_OFICINA);
            fire(PROP_LOG);
            fireStateIfChanged(antes);
        }
        return abasteceu;
    }
    public boolean repararDrone(int pontos) {
        DeepSeaState antes = context.getState();
        boolean reparou = context.repararDrone(pontos);
        if (reparou){
            fire(PROP_DRONE);
            fire(PROP_NAVIO);
            fire(PROP_OFICINA);
            fire(PROP_LOG);
            fireStateIfChanged(antes);
        }
        return reparou;
    }

    public boolean melhorarTanqueDrone(){
        boolean melhorou = context.melhorarTanqueDrone();
        if (melhorou){
            fire(PROP_DRONE);
            fire(PROP_NAVIO);
            fire(PROP_OFICINA);
            fire(PROP_LOG);
        }
        return melhorou;
    }

    public boolean melhorarIntegridadeDrone(){
        boolean melhorou = context.melhorarIntegridadeDrone();
        if (melhorou){
            fire(PROP_DRONE);
            fire(PROP_NAVIO);
            fire(PROP_OFICINA);
            fire(PROP_LOG);
        }
        return melhorou;
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

    public int[][] getMatrizPuzzle() {
        return context.getMatrizPuzzle();
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

    public boolean gravarLog(String caminho) {
        return DeepSeaLog.getInstance().gravarLog(caminho);
    }

    // --- UI grelha superficie

    // -- pos atual do navio á superficie
    public int getLinhaNavioSuperficie() {
        return context.getLinhaNavioSuperficie();
    }

    public int getColunaNavioSuperficie() {
        return context.getColunaNavioSuperficie();
    }

    // pistas dos artefactos int[l][c] pistas artefactos
    public int[][] getMapaPistasArtefactos() {
        return context.getMapaPistasArtefactos();
    }

    //===========================================================
    // --- Serialização & Save 6 Load ---
    //===========================================================

    public void novoJogo(){
        resetContadores();
        this.context = new DeepSeaContext();
        fire(PROP_GAME);
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
    }

    private void resetContadores() {
        Drone.resetContadorIds();
        Obstaculo.resetContadorObstaculos();
        Monstro.resetContadorMonstros();
        Artefacto.resetContadorArtefactos();
    }

    public boolean gravarJogo(File file){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(context);
        } catch (Exception e) {
            System.err.println("Erro ao gravar o jogo: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregarJogo(File file){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            context = (DeepSeaContext) ois.readObject();
        } catch (Exception e) {
            System.err.println("Erro ao carregar o jogo: " + e.getMessage());
            return false;
        }
        resetContadores();
        fire(PROP_GAME);
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        fire(PROP_NAVIO);
        fire(PROP_DRONE);
        fire(PROP_FUNDO);
        fire(PROP_FOSSO);
        fire(PROP_PUZZLE);
        fire(PROP_OFICINA);
        fire(PROP_LOG);
        return true;
    }

    public List<String> getInfoDrones() { return context.getInfoDrones(); }

    public int getArtefactosNavio() {
        return context.getArtefactosNavio();
    }

    public int getIdDroneAtivo() {
        return context.getIdDroneAtivo();
    }
    public boolean posicaoComMinerio() {
        return context.posicaoComMinerio();
    }
    public int getLinhaDroneAtivo(){return context.getLinhaDroneAtivo();}
    public int getColunaDroneAtivo(){return context.getColunaDroneAtivo();}

    public int getMovimentosRestantesPuzzle()  {
        return context.getMovimentosRestantesPuzzle();
    }

    public boolean isCelulaFundoRevelada(int lsup,int csup,int lF,int cF){
        return context.isCelulaFundoRevelada(lsup,csup,lF,cF);
    }
    public int getMineriosDroneAtivo() {
        return context.getMineriosDroneAtivo();
    }

    public int getArtefactosDroneAtivo() {
        return context.getArtefactosDroneAtivo();
    }

    public double getMaxCombustivelDroneAtivo() {
        return context.getMaxCombustivelDroneAtivo();
    }

    public double getIntegridadeMaxDrone() {
        return context.getIntegridadeMaxDrone();
    }
}
