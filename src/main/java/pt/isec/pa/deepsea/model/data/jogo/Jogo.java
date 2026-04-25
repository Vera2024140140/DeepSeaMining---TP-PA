package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.grelhas.FossoMarinho;
import pt.isec.pa.deepsea.model.data.grelhas.GrelhaSuperficie;
import pt.isec.pa.deepsea.model.data.puzzle.Puzzle;

import java.util.*;

public class Jogo {
    private final Navio navio;
    private final GrelhaSuperficie grelhaSuperficie;
    private Puzzle puzzleAtual = null;

    public Jogo() {
        this.grelhaSuperficie = new GrelhaSuperficie();
        this.navio = new Navio();
    }

    // -- Getter's
    Navio getNavio() {
        return navio;
    }

    GrelhaSuperficie getGrelhaSuperficie() {
        return grelhaSuperficie;
    }

    public double getCombustivelNavio() {
        return navio.getCombustivelNavio();
    }

    public int getMineriosNavio() {
        return navio.getMineriosNavio();
    }

    public List<Integer> getIdsArtefactosNavio() {
        return navio.getIdsArtefactosNavio();
    }

    public Set<Integer> getIdsDronesNavio() {
        return navio.getIdsDronesNavio();
    }

    public boolean moverNavio(Direcao dir) {
        if (dir == null) return false;
        if (navio.getCombustivelNavio() < Settings.COMBUSTIVEL_MOV_NAVIO) return false;

        int novaLinha = navio.getLinha();
        int novaColuna = navio.getColuna();
        switch (dir) {
            case CIMA -> novaLinha--;
            case BAIXO -> novaLinha++;
            case DIREITA -> novaColuna++;
            case ESQUERDA -> novaColuna--;
        }

        if (novaLinha < 0 || novaLinha >= Settings.LINHAS_SUPERFICIE || novaColuna < 0 || novaColuna >= Settings.COLUNAS_SUPERFICIE) {
            return false;
        }

        navio.setLocalizacao(novaLinha, novaColuna);
        navio.setCombustivel(navio.getCombustivelNavio() - Settings.COMBUSTIVEL_MOV_NAVIO);
        return true;
    }

    public boolean selecionarDrone(int idDrone) {
        return navio.setDroneAtivo(idDrone);
    }

    public int getDroneAtivoId() {
        return navio.getDroneAtivoId();
    }

    private Drone getDroneAtivo() {
        return navio.getDroneAtivo();
    }

    public boolean podeIniciarDescida() {
        Drone d = navio.getDroneAtivo();
        return d != null && d.getCombustivel() > 0 && d.getIntegridadeCasco() > 0;
    }

    public boolean meteDroneNoFosso() {
        Drone d = navio.getDroneAtivo();
        if (d != null) {
            d.setLocalizacao(0, navio.getColuna());
            return true;
        }
        return false;
    }

    public double getCombustivelDroneAtivo() {
        if (navio.getDroneAtivo() != null)
            return navio.getCombustivelDroneAtivo();
        return 0;
    }


    public int getIntegridadeDroneAtivo() {
        if (navio.getDroneAtivo() != null)
            return navio.getIntegridadeDroneAtivo();
        return 0;
    }

    //get's de grelhas
    public String getMapaSuperficie() {
        return grelhaSuperficie.toString();
    }

    public TipoComponente[][] getMapaFundo() {
        //return grelhaFundo.toString();
        return null;
    }

    public TipoComponente[][] getMapaFosso() {
        //return grelhafosso.toString();
        return  null;
    }

    public boolean vitoria() {
        return navio.getIdsArtefactosNavio().size() >= Settings.NUM_ARTEFACTOS;
    }

    public boolean derrota() {
        return navio.getCombustivelNavio() <= 0 || navio.getIdsDronesNavio().isEmpty();
    }

    // simulação para testes de fim de jogo (PUBLIC APENAS PARA TESTES)
    public void simularVitoria() {
        while (navio.getIdsArtefactosNavio().size() < Settings.NUM_ARTEFACTOS) {
            navio.addArtefacto(new Artefacto(-1, -1));
        }
    }

    public void simularNavioSemCombustivel() {
        navio.setCombustivel(0);
    }

    public void simularNavioSemDrones() {
        for (int id : new HashSet<>(navio.getIdsDronesNavio())) {
            navio.rmDrones(id);
        }
    }

    // mapa completo do fundo (matriz de tipos) para futura UI gráfica
    public TipoComponente[][] getMapaFundo(int lSup, int cSup) {
        int nl = grelhaSuperficie.getLinhasFundo(lSup, cSup);
        int nc = grelhaSuperficie.getColunasFundo(lSup, cSup);
        TipoComponente[][] mapa = new TipoComponente[nl][nc];
        for (int l = 0; l < nl; l++) {
            for (int c = 0; c < nc; c++) {
                mapa[l][c] = grelhaSuperficie.getTipoNoFundo(lSup, cSup, l, c);
            }
        }
        return mapa;
    }

    // mapa completo do fosso (matriz de tipos) para futura UI gráfica
    public TipoComponente[][] getMapaFosso(int lSup, int cSup) {
        int nl = grelhaSuperficie.getLinhasFosso(lSup, cSup);
        int nc = grelhaSuperficie.getColunasFosso(lSup, cSup);
        TipoComponente[][] mapa = new TipoComponente[nl][nc];
        for (int l = 0; l < nl; l++) {
            for (int c = 0; c < nc; c++) {
                mapa[l][c] = grelhaSuperficie.getTipoNoFosso(lSup, cSup, l, c);
            }
        }
        return mapa;
    }

    // mapa do fundo da célula onde o navio está
    public TipoComponente[][] getMapaFundoNavio() {
        return getMapaFundo(navio.getLinha(), navio.getColuna());
    }

    // mapa do fosso da célula onde o navio está
    public TipoComponente[][] getMapaFossoNavio() {
        return getMapaFosso(navio.getLinha(), navio.getColuna());
    }


    private String mapaParaString(TipoComponente[][] mapa) {
        StringBuilder sb = new StringBuilder();
        for (TipoComponente[] linha : mapa) {
            for (TipoComponente tipo : linha) {
                sb.append(simbolo(tipo));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // mapeia cada tipo de componente ao char usado na UI de texto
    private char simbolo(TipoComponente tipo) {
        if (tipo == null) return '_';
        return switch (tipo) {
            case ROCHA         -> 'R';
            case MINERIO       -> 'M';
            case CORRENTE      -> 'C';
            case ANIMALMARINHO -> 'A';
            case MONSTRO       -> '#';
            case ARTEFACTO     -> '*';
        };
    }

    public boolean droneChegouFundo() {
        Drone d =  getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        if (d == null )
            return false;

        return d.getLinha() >= grelhaSuperficie.getLinhasFosso(lSup, cSup) - 1;
    }

    //movimentos
    public boolean moverDroneFosso(Direcao dir) {
        Drone drone = getDroneAtivo();
        if (drone == null) return false;

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        //calc pos destino
        int destinoLinha = drone.getLinha();
        int destinoColuna = drone.getColuna();

        switch (dir) {
            case CIMA -> destinoLinha--;
            case BAIXO -> destinoLinha++;
            case DIREITA -> destinoColuna++;
            case ESQUERDA -> destinoColuna--;
        }

        //ver paredes grelha
        if (destinoLinha < 0 || destinoLinha >= grelhaSuperficie.getLinhasFosso(lSup, cSup) ||
                destinoColuna < 0 || destinoColuna >= grelhaSuperficie.getColunasFosso(lSup, cSup)) {
            return false; //fora do mapa
        }

        drone.consumirCombustivelDrone(Settings.DRONE_CONSUMO_MOV);

        if (grelhaSuperficie.fossoTemRocha(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.sofrerImpacto();
            return true;
        }

        if (grelhaSuperficie.fossoTemAnimal(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.sofrerImpacto();
        }

        if (grelhaSuperficie.fossoTemCorrente(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.consumirCombustivelDrone(Settings.DRONE_CONSUMO_CORRENTE);
        }
        drone.setLocalizacao(destinoLinha, destinoColuna);
        return true;
    }

    public boolean droneChegouSuperficie() {
        Drone d =  getDroneAtivo();
        if (d == null)
            return false;

        return  d.getLinha() <= 0;
    }

    public boolean descarregarDroneNavio() {
        Drone d =  getDroneAtivo();
        if (d == null) return false;

        int mineriosRecolhidos = getDroneAtivo().descarregarMinerios();
        navio.addMinerios(mineriosRecolhidos);

        var artefatosRecolhidos = d.descarregarArtefactos();
        for (var art : artefatosRecolhidos) {
            navio.addArtefacto(art);
        }
        return true;
    }

    // ===================================================================
    // --- PUZZLE ---
    // ===================================================================
    public void iniciarPuzzle() {
        this.puzzleAtual = new Puzzle();
    }

    public void limparPuzzle() {
        this.puzzleAtual = null;
    }

    public boolean moverPecaPuzzle(Direcao dir) {
        if (puzzleAtual != null) {
            return puzzleAtual.mover(dir);
        }
        return false;
    }

    public boolean isPuzzleResolvido() {
        return puzzleAtual != null && puzzleAtual.estaResolvido();
    }

    public boolean isPuzzleSemMovimentos() {
        return puzzleAtual != null && puzzleAtual.getMovimentosRestantes() <= 0;
    }

    public void rescolherArtefactoPuzzle() {
        Drone d = getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();
        if (d != null) {
            int l = d.getLinha();
            int c = d.getColuna();

            if (grelhaSuperficie.fundoGetTipo(lSup, cSup, l, c) == TipoComponente.ARTEFACTO) {
                Artefacto art = grelhaSuperficie.fundoRecolherArtefacto(lSup, cSup, l, c);
                if (art != null) {
                    d.addArtefacto(art);
                }
            }
        }
        limparPuzzle();
    }


}