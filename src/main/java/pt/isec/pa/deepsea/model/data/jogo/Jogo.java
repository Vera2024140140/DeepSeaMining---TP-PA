package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.grelhas.GrelhaSuperficie;
import java.util.*;

public class Jogo {
    private final Navio navio;
    private final GrelhaSuperficie grelhaSuperficie;

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

}