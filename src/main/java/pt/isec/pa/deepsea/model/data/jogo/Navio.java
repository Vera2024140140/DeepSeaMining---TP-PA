package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.io.Serializable;
import java.util.*;

public class Navio implements Serializable {
    private static final long serialVersionUID = 21L;
    private double combustivel;
    private int minerios;

    private List<Artefacto> artefactos;
    private Set<Drone> drones;

    private int linha;
    private int coluna;

    private int droneAtivoId = 1;

    public Navio() {
        this.combustivel = Settings.NAVIO_COMBUSTIVEL_INICIAL;
        this.minerios = 0;
        this.artefactos = new ArrayList<>();
        this.drones = new HashSet<>();
        this.linha = Settings.LINHAS_SUPERFICIE / 2; //centrar
        this.coluna = Settings.COLUNAS_SUPERFICIE / 2;

        //drones default
        for(int i = 0; i < Settings.NUM_DRONES_INICIAIS; i++) {
            Drone d = new Drone();
            drones.add(d);
            if (i == 0) this.droneAtivoId = d.getId();  // 1º drone fica ativo
        }
    }

    // -- LOCALIZAÇAO
    int getLinha() { return linha; }

    int getColuna() { return coluna; }

    void setLocalizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    double getCombustivelNavio() { return combustivel; }

    //setter puro
    void setCombustivel(double combustivel) {
        if (combustivel >= 0 && combustivel <= Settings.NAVIO_COMBUSTIVEL_MAX ) {
            this.combustivel = combustivel;
        }
    }

    void addCombustivel(double combustivel) {
        if (combustivel > 0 ) {
            this.combustivel += combustivel;
            if (this.combustivel > Settings.NAVIO_COMBUSTIVEL_MAX) {
                this.combustivel = Settings.NAVIO_COMBUSTIVEL_MAX;
            }
        }
    }

    int getMineriosNavio() { return minerios; }

    boolean removerCombustivel(double combustivel){
        if(combustivel <= 0)
            return false;
        if(this.combustivel >= combustivel){
            this.combustivel -= combustivel;
            return true;
        }
        return false;
    }

    void addMinerios(int qtd) {
        if (qtd > 0) {
            this.minerios += qtd;
        }
    }

    boolean removerMinerios(int qtd){
        if(qtd <= 0)
            return false;
        if(minerios >= qtd){
            minerios -= qtd;
            return true;
        }
        return false;
    }

    List<Integer> getIdsArtefactosNavio() {
        List<Integer> listaIdsArtefactos = new ArrayList<>();
        for(Artefacto artefacto : this.artefactos) {
            listaIdsArtefactos.add(artefacto.getId());
        }
        return listaIdsArtefactos;
    }

    void addArtefacto(Artefacto a) {
        if (a != null)
            artefactos.add(a);
    }

    // -- DRONES
    Set<Integer> getIdsDronesNavio() {
        //copia da lista
        Set<Integer> set_drones = new HashSet<>();

        for (Drone d : this.drones) {
            set_drones.add(d.getId());
        }
        return set_drones;
    }

    //quando os mesmos são perdids pq ficaram sem combustivel por ex
    void rmDrones(int idDrone) {
        //remove caso o id seja ==
        drones.removeIf(d -> d.getId() == idDrone);
        if (this.droneAtivoId == idDrone) {
            this.droneAtivoId = -1;
        }
    }

    boolean setDroneAtivo(int idDrone) {
        for (Drone d : drones) {
            if (d.getId() == idDrone) {
                this.droneAtivoId = idDrone;
                return true;
            }
        }
        return false;
    }

     int getDroneAtivoId() {
        return droneAtivoId;
    }

    Drone getDroneAtivo() {
        for (Drone d : drones) {
            if (d.getId() == droneAtivoId) return d;
        }
        return null;
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
    List<Integer> getDronesOrdenadosCombustivel() {
        List<Drone> dronesOrdenados = new ArrayList<>(this.drones);

        dronesOrdenados.sort(Comparator.comparingDouble(Drone::getCombustivel));

        List<Integer> listIdsDrones = new ArrayList<>();
        for (Drone d : dronesOrdenados) {
            listIdsDrones.add(d.getId());
        }
        return listIdsDrones;
    }

    List<Integer> getDronesOrdenadosIntegridade() {
        List<Drone> dronesOrdenados = new ArrayList<>(this.drones);

        dronesOrdenados.sort(Comparator.comparingInt(Drone::getIntegridadeCasco));

        List<Integer> listIdsDrones = new ArrayList<>();
        for (Drone d : dronesOrdenados) {
            listIdsDrones.add(d.getId());
        }
        return listIdsDrones;
    }

    double getCombustivelDroneAtivo() {
        Drone d  = getDroneAtivo();
        return d != null ? d.getCombustivel() : 0.0;
    }

    int getIntegridadeDroneAtivo() {
        Drone d = getDroneAtivo();
        return d != null ? d.getIntegridadeCasco() : 0;
    }

    public int getArtefactosNavio() {
        return artefactos.size();
    }
}
