package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaContext;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.model.utils.DeepSeaLog;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;
import java.util.Set;

import static pt.isec.pa.deepsea.model.state.DeepSeaState.*;
import static pt.isec.pa.deepsea.model.state.DeepSeaState.ACABOU_STATE;

/**
 * Facade entre a UI e o modelo de dados.
 * Delega as ações para o {@link DeepSeaContext} e notifica
 * a UI através de {@link PropertyChangeSupport} (Observer).
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class DeepSeaManager {

    public static final String PROP_STATE = "state";
    public static final String PROP_NAVIO = "navio";
    public static final String PROP_DRONE = "drone";
    public static final String PROP_FUNDO = "fundo";
    public static final String PROP_FOSSO = "fosso";
    public static final String PROP_PUZZLE = "puzzle";
    public static final String PROP_OFICINA = "oficina";
    public static final String PROP_LOG = "log";
    public static final String PROP_GAME = "game";
    public static final String PROP_ROCHA = "rocha";
    public static final String PROP_MONSTRO = "monstro";
    public static final String PROP_CORRENTE = "corrente";
    public static final String PROP_MINERIO = "minerio";
    public static final String PROP_ANIMAL_MARINHO = "animalMarinho";
    public static final String PROP_ABRIR_OFICINA = "abrirOficina";
    public static final String PROP_FECHAR_OFICINA = "fecharOficina";
    public static final String PROP_MOVER_DRONE = "moverDrone";
    public static final String PROP_MOVER_NAVIO = "moverNavio";
    public static final String PROP_MOVER_PUZZLE = "moverPuzzle";
    public static final String PROP_GANHAR_JOGO = "ganhouJogo";
    public static final String PROP_PERDER_JOGO = "perdeuJogo";
    public static final String PROP_ARTEFACTO = "artefactoRecolhido";
    public static final String PROP_PERDER_PUZZLE = "perdeuPuzzle";
    public static final String PROP_PERDER_DRONE = "perderDrone";

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private DeepSeaContext context;

    public DeepSeaManager() {
        this.context = new DeepSeaContext();
    }

    public void addPropertyChangeListener(String prop, PropertyChangeListener l) {
        pcs.addPropertyChangeListener(prop, l);
    }

    private void fire(String... props) {
        for (String prop : props) {
            pcs.firePropertyChange(prop, null, null);
        }
    }

    private void fireStateIfChanged(DeepSeaState antes) {
        DeepSeaState depois = context.getState();
        pcs.firePropertyChange(PROP_STATE, antes, depois);
        if (depois == DeepSeaState.SUPERFICIE_STATE || depois == DeepSeaState.ACABOU_STATE){
            fire(PROP_NAVIO);
        }
        if (depois == DeepSeaState.PUZZLE_STATE) {
            fire(PROP_PUZZLE);
        }
        //ganhar/perder puzzle
        if (antes == PUZZLE_STATE && depois == FUNDO_STATE){
            fire(PROP_ARTEFACTO);
        }else if (antes == PUZZLE_STATE && depois == SUBIDA_STATE){
            fire(PROP_PERDER_PUZZLE);
        }
        //fim do jogo
        if(depois == ACABOU_STATE){
            if(context.getArtefactosNavio() == Settings.NUM_ARTEFACTOS){
                fire(PROP_GANHAR_JOGO);
            }else{
                fire(PROP_PERDER_JOGO);
            }
        }
    }

    // ===========================================================
    // ações
    // ===========================================================

    /**
     * Inicia a descida do drone ativo para o fosso.
     */
    public boolean iniciarDescida() {
        DeepSeaState antes = context.getState();
        boolean desceu = context.iniciarDescida();
        if (desceu) { fireStateIfChanged(antes); fire(PROP_FOSSO, PROP_DRONE, PROP_LOG); }
        return desceu;
    }

    /**
     * Inicia a subida do drone de volta à superfície.
     */
    public boolean iniciarSubida() {
        DeepSeaState antes = context.getState();
        boolean subiu = context.iniciarSubida();
        if (subiu) { fireStateIfChanged(antes); fire(PROP_DRONE, PROP_FOSSO, PROP_LOG); }
        return subiu;
    }

    /**
     * Move o navio ou drone na direção indicada.
     * Verifica colisões e notifica a UI.
     */
    public boolean mover(Direcao dir) {
        DeepSeaState antes = context.getState();
        TipoComponente colisao = verificarColisoesDrone(dir, antes);
        int dronesAntes = context.getIdsDronesNavio().size();
        boolean ok = context.mover(dir);
        DeepSeaState depois = context.getState();
        if (ok && context.getIdsDronesNavio().size() < dronesAntes) {
            fire(PROP_PERDER_DRONE);
        }
        if(ok) {
            if (antes == depois) {
                if (antes == SUPERFICIE_STATE) {
                    fire(PROP_MOVER_NAVIO);
                } else if (antes != PUZZLE_STATE) {
                    fire(PROP_MOVER_DRONE);
                }else {
                    fire(PROP_MOVER_PUZZLE);
                }
            }
            if (colisao != null) {
                switch (colisao) {
                    case ROCHA -> fire(PROP_ROCHA);
                    case MONSTRO -> fire(PROP_MONSTRO);
                    case ANIMALMARINHO -> fire(PROP_ANIMAL_MARINHO);
                    case CORRENTE -> fire(PROP_CORRENTE);
                }
            }
        }
        if (!ok) return false;
        switch (antes) {
            case SUPERFICIE_STATE -> fire(PROP_NAVIO);
            case FUNDO_STATE -> fire(PROP_DRONE, PROP_FUNDO);
            case DESCIDA_STATE, SUBIDA_STATE -> fire(PROP_DRONE, PROP_FOSSO);
            case PUZZLE_STATE -> fire(PROP_PUZZLE, PROP_DRONE, PROP_FUNDO);
            default -> {}
        }
        fireStateIfChanged(antes);
        fire(PROP_LOG);
        return true;
    }

    private TipoComponente verificarColisoesDrone(Direcao dir, DeepSeaState antes) {
        int l = getLinhaDroneAtivo();
        int c = getColunaDroneAtivo();
        switch (dir) {
            case CIMA -> l--;
            case BAIXO -> l++;
            case ESQUERDA -> c--;
            case DIREITA -> c++;
        }
        int lSup = getLinhaNavioSuperficie();
        int cSup = getColunaNavioSuperficie();

        TipoComponente[][] mapa = null;
        if(antes == FUNDO_STATE){
            mapa = getMapaFundo(lSup,cSup);
        }else if(antes == DESCIDA_STATE || antes == SUBIDA_STATE){
            mapa = getMapaFosso(lSup,cSup);
        }
        if (mapa == null) return null;
        if (l < 0 || l >= mapa.length || c < 0 || c >= mapa[0].length) return null;
        return mapa[l][c];
    }

    /**
     * Recolhe o minério na posição atual do drone.
     */
    public boolean recolherMinerio(){
        boolean recolheu = context.recolherMinerio();
        if(recolheu) {
            fire(PROP_FUNDO, PROP_DRONE, PROP_LOG,PROP_MINERIO);
        }
        return recolheu;
    }

    /**
     * Recolhe o artefacto na posição atual, iniciando o puzzle.
     */
    public boolean recolherArtefacto() {
        DeepSeaState antes = context.getState();
        boolean ok = context.recolherArtefacto();
        if (ok) {
            fireStateIfChanged(antes);  // muda para PUZZLE_STATE
            fire(PROP_PUZZLE, PROP_FUNDO, PROP_LOG);
        }
        return ok;
    }

    /**
     * Abre a oficina para gestão dos drones.
     */
    public boolean abrirOficina() {
        DeepSeaState antes = context.getState();
        boolean abriu = context.abrirOficina();
        if (abriu){
            fireStateIfChanged(antes);
            fire(PROP_LOG,PROP_ABRIR_OFICINA);
        }
        return abriu;
    }

    /**
     * Fecha a oficina, regressando à superfície.
     */
    public boolean fecharOficina() {
        DeepSeaState antes = context.getState();
        boolean fechou = context.fecharOficina();
        if (fechou){
            fireStateIfChanged(antes);
            fire(PROP_LOG,PROP_FECHAR_OFICINA);
        }
        return fechou;
    }

    /**
     * Seleciona o drone com o id indicado na oficina.
     */
    public boolean selecionarDrone(int idDrone) {
        boolean selecionou = context.selecionarDrone(idDrone);
        if (selecionou){
            fire(PROP_OFICINA, PROP_DRONE, PROP_LOG);
        }
        return selecionou;
    }

    /**
     * Abastece o drone ativo com combustível do navio.
     */
    public boolean abastecerDrone(double litros) {
        DeepSeaState antes = context.getState();
        boolean abasteceu = context.abastecerDrone(litros);
        if (abasteceu){
            fire(PROP_DRONE, PROP_NAVIO, PROP_OFICINA, PROP_LOG);
            fireStateIfChanged(antes);
        }
        return abasteceu;
    }

    /**
     * Repara a integridade do casco do drone ativo.
     */
    public boolean repararDrone(int pontos) {
        DeepSeaState antes = context.getState();
        boolean reparou = context.repararDrone(pontos);
        if (reparou){
            fire(PROP_DRONE, PROP_NAVIO, PROP_OFICINA, PROP_LOG);
            fireStateIfChanged(antes);
        }
        return reparou;
    }

    /**
     * Melhora a capacidade do tanque do drone ativo, gastando minérios.
     */
    public boolean melhorarTanqueDrone(){
        boolean melhorou = context.melhorarTanqueDrone();
        if (melhorou){
            fire(PROP_DRONE, PROP_NAVIO, PROP_OFICINA, PROP_LOG);
        }
        return melhorou;
    }

    /**
     * Melhora a integridade máxima do drone ativo, gastando minérios.
     */
    public boolean melhorarIntegridadeDrone(){
        boolean melhorou = context.melhorarIntegridadeDrone();
        if (melhorou){
            fire(PROP_DRONE, PROP_NAVIO, PROP_OFICINA, PROP_LOG);
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

    /**
     * Inicia um novo jogo, reiniciando o contexto.
     */
    public void novoJogo(){
        resetContadores();
        this.context = new DeepSeaContext();
        fire(PROP_GAME);
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
    }

    private void resetContadores() {
        context.resetContadores();
    }

    /**
     * Grava o jogo atual num ficheiro por serialização.
     */
    public boolean gravarJogo(File file){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(context);
        } catch (Exception e) {
            System.err.println("Erro ao gravar o jogo: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Carrega um jogo gravado a partir de um ficheiro.
     */
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
        fire(PROP_NAVIO, PROP_DRONE, PROP_FUNDO, PROP_FOSSO, PROP_PUZZLE, PROP_OFICINA, PROP_LOG);
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

    public boolean posicaoComArtefacto() {
        return context.posicaoComArtefacto();
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
