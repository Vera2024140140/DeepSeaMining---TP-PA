package pt.isec.pa.deepsea.model.data.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NavioTest {
    private Navio navio;

    @BeforeEach
    void setUp() {
        navio = new Navio();
    }

    @Test
    void testeValoresIniciais() {
        assertEquals(Settings.NAVIO_COMBUSTIVEL_INICIAL, navio.getCombustivel());
        assertEquals(0 , navio.getMinerios());
        assertEquals(0, navio.getArtefactos().size());
        assertEquals(Settings.NUM_DRONES_INICIAIS, navio.getDrones().size());
        assertEquals(Settings.LINHAS_SUPERFICIE / 2, navio.getLinha());
        assertEquals(Settings.COLUNAS_SUPERFICIE / 2, navio.getColuna());
    }


    @Test
    void setLocalizacao() {
        navio.setLocalizacao(10, 20);
        assertEquals(10, navio.getLinha());
        assertEquals(20, navio.getColuna());
    }

    @Test
    void addCombustivel() {
        navio.setCombustivel(100);

        //teste add
        navio.addCombustivel(50);
        assertEquals(150, navio.getCombustivel(), "100 + 50");

        //tentar transbordar
        navio.addCombustivel(Settings.NAVIO_COMBUSTIVEL_MAX + 500);
        assertEquals(Settings.NAVIO_COMBUSTIVEL_MAX, navio.getCombustivel(), "Deve ficar com o max apenas");
    }

    @Test
    void setCombustivelValido() {
        navio.setCombustivel(100);
        assertEquals(100, navio.getCombustivel());
    }

    @Test
    void setCombustivelAcimaMax() {
        navio.setCombustivel(2000);
        assertEquals(Settings.NAVIO_COMBUSTIVEL_INICIAL, navio.getCombustivel()); //como passa do limite é ignorado
    }

    @Test
    void setCombustivelValorNegativo() {
        navio.setCombustivel(-1);
        assertEquals(Settings.NAVIO_COMBUSTIVEL_INICIAL, navio.getCombustivel());
    }

    @Test
    void setMinerios() {
        navio.setMinerios(100);
        assertEquals(100, navio.getMinerios());
    }

    @Test
    void addArtefacto() {
        Artefacto a = null;
        navio.addArtefacto(a);
        assertEquals(1, navio.getArtefactos().size());
    }

    @Test
    void rmDrones() {
        int tam = navio.getDrones().size();
        assertTrue(tam > 0, "Devia ter 3 drones");

        /*
        como é um set, usams um iterador para 'apanhar'o primeiro que apareça
            o iterator cria um apontador para o meu set e o next mostra o prox elemento que encontrar
        */
        Drone d_rm = navio.getDrones().iterator().next();

        navio.rmDrones(d_rm);

        assertEquals(tam - 1, navio.getDrones().size(), "A lista deve ter 2");

        assertFalse(navio.getDrones().contains(d_rm), "Já nao deve existir");
    }

    @Test
    void getDronesOrdenadosCombustivel() {
        //lista temp para ser + facil aceder aos 3 drones
        List<Drone> lista_temp = new ArrayList<>(navio.getDrones());

        assertEquals(Settings.NUM_DRONES_INICIAIS, lista_temp.size(), "Numero de drones iniciall");

        //forcar valores de combustivel para os drones
        lista_temp.get(0).setCombustivel(80.0);
        lista_temp.get(1).setCombustivel(60.0);
        lista_temp.get(2).setCombustivel(30.0);

        java.util.List<Drone> ordenados = navio.getDronesOrdenadosCombustivel();

        //verf se esta pela ordem correta (crescente)
        assertEquals(30.0, ordenados.get(0).getCombustivel());
        assertEquals(60.0, ordenados.get(1).getCombustivel());
        assertEquals(80.0, ordenados.get(2).getCombustivel());
    }

    @Test
    void getDronesOrdenadosIntegridade() {
        List<Drone> lista_temp = new ArrayList<>(navio.getDrones());

        assertEquals(Settings.NUM_DRONES_INICIAIS, lista_temp.size());

        //forcar valores de integridade diferentes
        lista_temp.get(0).setIntegridadeCasco(80);
        lista_temp.get(1).setIntegridadeCasco(60);
        lista_temp.get(2).setIntegridadeCasco(30);

        List<Drone> ordenados = navio.getDronesOrdenadosIntegridade();

        assertEquals(30, ordenados.get(0).getIntegridadeCasco());
        assertEquals(60, ordenados.get(1).getIntegridadeCasco());
        assertEquals(80, ordenados.get(2).getIntegridadeCasco());
    }
}