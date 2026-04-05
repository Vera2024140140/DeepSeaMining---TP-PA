package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.util.*;

public class Navio {
    private double combustivel;
    private int minerios;

    private List<Artefacto> artefactos;
    private Set<Drone> drones;

    private int linha;
    private int coluna;

    public Navio() {
        this.combustivel = Settings.NAVIO_COMBUSTIVEL_INICIAL;
        this.minerios = 0;
        this.artefactos = new ArrayList<>();
        this.drones = new HashSet<>();
        this.linha = Settings.LINHAS_SUPERFICIE / 2; //centrar
        this.coluna = Settings.COLUNAS_SUPERFICIE / 2;

        //drones default
        for(int i = 0; i < Settings.NUM_DRONES_INICIAIS; i++) {
            drones.add(new Drone());
        }
    }

    // -- LOCALIZAÇAO
    public int getLinha() { return linha; }

    public int getColuna() { return coluna; }

    public void setLocalizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public double getCombustivel() { return combustivel; }

    //setter puro
    public void setCombustivel(double combustivel) {
        if (combustivel >= 0 && combustivel <= Settings.NAVIO_COMBUSTIVEL_MAX ) {
            this.combustivel = combustivel;
        }
    }

    public void addCombustivel(double combustivel) {
        if (combustivel > 0 ) {
            this.combustivel += combustivel;
            if (this.combustivel > Settings.NAVIO_COMBUSTIVEL_MAX) {
                this.combustivel = Settings.NAVIO_COMBUSTIVEL_MAX;
            }
        }
    }

    public int getMinerios() { return minerios; }

    public void setMinerios(int minerios) {
        this.minerios = minerios;
    }

    public List<Artefacto> getArtefactos() { return artefactos; }

    public void addArtefacto(Artefacto a) {
        artefactos.add(a);
    }

    // -- DRONES
    public Set<Drone> getDrones() { return drones; }

    //quando os mesmos são perdids pq ficaram sem combustivel por ex
    public void rmDrones(Drone d) {
        drones.remove(d);
    }

    //metodos de pesquisa por criterios especificos
    public List<Drone> getDronesOrdenadosCombustivel() {
        List<Drone> drones = new ArrayList<>(this.drones);
        drones.sort(Comparator.comparingDouble(Drone::getCombustivel));
        return drones;
    }

    public List<Drone> getDronesOrdenadosIntegridade() {
        List<Drone> drones = new ArrayList<>(this.drones);
        drones.sort(Comparator.comparingInt(Drone::getIntegridadeCasco));
        return drones;
    }
}
