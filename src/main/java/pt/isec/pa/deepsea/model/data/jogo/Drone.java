package pt.isec.pa.deepsea.model.data.jogo;


import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    //combustivel
    private double combustivel;
    private double combustivelMax;

    //integridade
    private int integridadeCasco;
    private int integridadeMax;

    private int linha;
    private int coluna;

    private int minerios;
    private List<Artefacto> artefactos;
    private static int contadorIds = 1;
    private final int id;


    public Drone() {
        this.combustivelMax = Settings.DRONE_COMBUSTIVEL_MAX;
        this.combustivel = combustivelMax;
        this.integridadeMax = Settings.DRONE_INTEGRIDADE_MAX;
        this.integridadeCasco = integridadeMax;
        this.linha = -1; //-1 quer dizer que está no navio
        this.coluna = -1;
        this.minerios = 0;
        this.artefactos = new ArrayList<>();
        this.id = contadorIds++;
    }

    int getId() {
        return this.id;
    }

    // -- COMBUSTIVEL
    public double getCombustivel() { return combustivel; }

    void setCombustivel(double combustivel) {
        if (combustivel >= 0 && combustivel <= this.combustivelMax) {
            this.combustivel = combustivel;
        }
    }

    double addCombustivel(double combustivel) {
        if (combustivel <= 0)
            return combustivel;

        //calcular espaço livre
        double espacoLivre = this.combustivelMax - this.combustivel;

        if (combustivel <= espacoLivre) {
            this.combustivel += combustivel;
            return 0;
        } else {
            this.combustivel = this.combustivelMax;
            double resto = combustivel - espacoLivre;

            return resto;
        }
    }

    public double getCombustivelMax() { return combustivelMax; }

    public void setCombustivelMax(double combustivelMax) {
        if (combustivelMax > 0) {
            this.combustivelMax = combustivelMax;
            if (this.combustivel > this.combustivelMax) {
                this.combustivel = this.combustivelMax;
            }
        }
    }

    // -- CASCO
    public int getIntegridadeCasco() { return integridadeCasco; }

    void setIntegridadeCasco(int integridadeCasco) {
        if (integridadeCasco < 0)  {
            this.integridadeCasco = 0;
        } else if (integridadeCasco > this.integridadeMax) {
            this.integridadeCasco = integridadeMax;
        } else {
            this.integridadeCasco = integridadeCasco;
        }
    }

    boolean addIntegridade(int integridade){
        if(integridade <= 0)
            return false;
        int integridadeFalta = this.integridadeMax - this.integridadeCasco;
        if (integridade <= integridadeFalta) {
            this.integridadeCasco += integridade;
            return true;
        }
        return false;
    }

    public int getIntegridadeMax() { return integridadeMax; }

    void setIntegridadeMax(int integridadeMax) {
        this.integridadeMax = integridadeMax;
    }

    // -- LOCALIZAÇAO

    public int getLinha() { return linha; }

    public int getColuna() { return coluna; }

    void setLocalizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    // -- MINERIOS
    public int getMinerios() { return minerios; }

    void addMinerios() {
        this.minerios++;
    }

    public List<Integer> getArtefactos() {
        List<Integer> listaIdsArtefcactos = new ArrayList<>();

        for (Artefacto artefacto : this.artefactos) {
            listaIdsArtefcactos.add(artefacto.getId());
        }
        return listaIdsArtefcactos;
    }

    void addArtefacto(Artefacto a) {
        if (a != null)
            artefactos.add(a);
    }

    boolean consumirCombustivelDrone(double qtd) {
        if (qtd <= 0) return false; // nao gasta nada

        if (this.combustivel >= qtd) {
            this.combustivel -= qtd;
            return true;
        } else {
            this.combustivel = 0; //ficou sem combustivdel
            return false; // false para o jgo saber que terminou o combustivel
        }
    }

    //limpa a qtd de minerios da 'mochila' e retorna-os
    int descarregarMinerios() {
        int mineriosRecolhidos = this.minerios;
        this.minerios = 0;
        return mineriosRecolhidos;
    }

    // devolve a lista de artefctos atual e limpa (a lista)
    List<Artefacto> descarregarArtefactos() {
        //referencia para a lista atual
        List<Artefacto> artefactosRecolhidos = new ArrayList<>(this.artefactos);

        this.artefactos.clear();
        return  artefactosRecolhidos;
    }
}
