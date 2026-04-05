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

    public Drone() {
        this.combustivelMax = Settings.DRONE_COMBUSTIVEL_MAX;
        this.combustivel = combustivelMax;
        this.integridadeMax = Settings.DRONE_INTEGRIDADE_MAX;
        this.integridadeCasco = integridadeMax;
        this.linha = -1; //-1 quer dizer que está no navio
        this.coluna = -1;
        this.minerios = 0;
        this.artefactos = new ArrayList<>();

    }

    // -- COMBUSTIVEL
    public double getCombustivel() { return combustivel; }

    public void setCombustivel(double combustivel) {
        if (combustivel >= 0 && combustivel <= this.combustivelMax) {
            this.combustivel = combustivel;
        }
    }

    public double addCombustivel(double combustivel) {
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

    public void setIntegridadeCasco(int integridadeCasco) {
        this.integridadeCasco = integridadeCasco;
    }

    public int getIntegridadeMax() { return integridadeMax; }

    public void setIntegridadeMax(int integridadeMax) {
        this.integridadeMax = integridadeMax;
    }

    // -- LOCALIZAÇAO

    public int getLinha() { return linha; }

    public int getColuna() { return coluna; }

    public void setLocalizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    // -- MINERIOS
    public int getMinerios() { return minerios; }

    public void setMinerios(int minerios) {
        this.minerios = minerios;
    }

    public List<Artefacto> getArtefactos() { return artefactos; }

    public void addArtefacto(Artefacto a) {
        artefactos.add(a);
    }
}
