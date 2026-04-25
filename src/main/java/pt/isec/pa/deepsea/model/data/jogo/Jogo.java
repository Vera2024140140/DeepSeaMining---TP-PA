package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
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


}