package pt.isec.pa.deepsea.model.data.jogo;


import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Drone implements Serializable {
    private static final long serialVersionUID = 20L;
    //combustivel
    private double combustivel;
    private double combustivelMax;

    //integridade
    private int integridadeCasco;
    private int integridadeMax;

    private int impactosExpedicao;

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
        this.impactosExpedicao = 0;
    }

    public static void resetContadorIds() {
        contadorIds = 1;
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
            return (combustivel - espacoLivre);
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
        if (qtd <= 0) return false; // não gasta nada

        if (this.combustivel >= qtd) {
            this.combustivel -= qtd;
            return true;
        }
            return false; // nao consumiu combustivel

    }

    //limpa a qtd de minerios da 'mochila' e retorna-os
    int descarregarMinerios() {
        int mineriosRecolhidos = this.minerios;
        this.minerios = 0;
        return mineriosRecolhidos;
    }
    public boolean addMinerios(int qtd) {
        if(qtd <= 0)
            return false;
        this.minerios += qtd;
        return true;
    }
    // devolve a lista de artefctos atual e limpa (a lista)
    List<Artefacto> descarregarArtefactos() {
        //referencia para a lista atual
        List<Artefacto> artefactosRecolhidos = new ArrayList<>(this.artefactos);

        this.artefactos.clear();
        return  artefactosRecolhidos;
    }

    //================================================
    // --- Lógica dano w primos ---
    //================================================

    //metodo chamado a cada expedição
    void resetImpactos() {
        this.impactosExpedicao = 0;
    }

    void sofrerImpacto() {
        int dano = calcularDanoAtual(impactosExpedicao);

        this.integridadeCasco -= dano;
        if (this.impactosExpedicao < 0) {
            this.integridadeCasco = 0;
        }

        impactosExpedicao++; //inc contador de impactos
    }

    //calculo do valor da sequencia
    int calcularDanoAtual(int impactoAtual) {
        int primosEncontrados = 0;
        int numeroTestePrimo = 1; //numero que vamos verificar se é primo

        while(true) {
            if (isPrimo(numeroTestePrimo)) {
                if (primosEncontrados == impactoAtual) {
                    return numeroTestePrimo;
                }
                primosEncontrados++;
            }
            numeroTestePrimo++;
        }
    }

    int getQtdMinerios() {
        return this.minerios;
    }

    int getQtdArtefactos() {
        return this.artefactos.size();
    }

    boolean isPrimo(int n) {
        if (n < 1) return false;
        if (n == 1 || n == 2) return true;
        if (n %  2 == 0) return false;

        //divisores impares (2 em 2) a partir de 3
        for(int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
