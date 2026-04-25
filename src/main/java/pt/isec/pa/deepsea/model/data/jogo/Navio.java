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

    public double getCombustivelNavio() { return combustivel; }

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

    public int getMineriosNavio() { return minerios; }

    public void addMinerios(int qtd) {
        if (qtd > 0) {
            this.minerios += qtd;
        }
    }

    public List<Integer> getIdsArtefactosNavio() {
        List<Integer> listaIdsArtefactos = new ArrayList<>();
        for(Artefacto artefacto : this.artefactos) {
            listaIdsArtefactos.add(artefacto.getId());
        }
        return listaIdsArtefactos;
    }

    public void addArtefacto(Artefacto a) {
        if (a != null)
            artefactos.add(a);
    }

    // -- DRONES
    public Set<Integer> getIdsDronesNavio() {
        //copia da lista
        Set<Integer> set_drones = new HashSet<>();

        for (Drone d : this.drones) {
            set_drones.add(d.getId());
        }
        return set_drones;
    }

    //quando os mesmos são perdids pq ficaram sem combustivel por ex
    public void rmDrones(int idDrone) {
        //remove caso o id seja ==
        drones.removeIf(d -> d.getId() == idDrone);
    }

    void setCombustivelDrone(int idDrone, double valor) {
        for (Drone d : this.drones) {
            if (d.getId() == idDrone) {
                d.setCombustivel(valor);
                return;
            }
        }
    }

    void setIntegridadeDrone(int idDrone, int valor) {
        for (Drone d : drones) {
            if (d.getId() == idDrone) {
                d.setIntegridadeCasco(valor);
                break;
            }
        }
    }

    //metodos de pesquisa por criterios especificos
    public List<Integer> getDronesOrdenadosCombustivel() {
        List<Drone> dronesOrdenados = new ArrayList<>(this.drones);

        dronesOrdenados.sort(Comparator.comparingDouble(Drone::getCombustivel));

        List<Integer> listIdsDrones = new ArrayList<>();
        for (Drone d : dronesOrdenados) {
            listIdsDrones.add(d.getId());
        }
        return listIdsDrones;
    }

    public List<Integer> getDronesOrdenadosIntegridade() {
        List<Drone> dronesOrdenados = new ArrayList<>(this.drones);

        dronesOrdenados.sort(Comparator.comparingInt(Drone::getIntegridadeCasco));

        List<Integer> listIdsDrones = new ArrayList<>();
        for (Drone d : dronesOrdenados) {
            listIdsDrones.add(d.getId());
        }
        return listIdsDrones;
    }
}
