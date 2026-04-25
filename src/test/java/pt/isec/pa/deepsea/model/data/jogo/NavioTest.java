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
        navio.addMinerios(100);
        assertEquals(100, navio.getMinerios());
    }

    @Test
    void addArtefacto() {
        Artefacto a = new Artefacto(1,1);
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
        int id_rm = navio.getDrones().iterator().next();
        navio.rmDrones(id_rm);

        assertEquals(tam - 1, navio.getDrones().size(), "A lista deve ter 2");

        assertFalse(navio.getDrones().contains(id_rm), "Já nao deve existir");
    }

    @Test
    void getDronesOrdenadosCombustivel() {
        //lista temp para ser + facil aceder aos 3 drones
        List<Integer> lista_temp = new ArrayList<>(navio.getDrones());
        assertEquals(Settings.NUM_DRONES_INICIAIS, lista_temp.size(), "Numero de drones inicial");

        int id1 = lista_temp.get(0);
        int id2 = lista_temp.get(1);
        int id3 = lista_temp.get(2);

        //forcar valores de combustivel para os drones
        navio.setCombustivelDrone(id1,80.0);
        navio.setCombustivelDrone(id2,60.0);
        navio.setCombustivelDrone(id3,30.0);

        List<Integer> ordenados = navio.getDronesOrdenadosCombustivel();

        //verf se esta pela ordem correta (crescente)
        assertEquals(id3, ordenados.get(0));
        assertEquals(id2, ordenados.get(1));
        assertEquals(id1, ordenados.get(2));
    }

    @Test
    void getDronesOrdenadosIntegridade() {
        List<Integer> lista_temp = new ArrayList<>(navio.getDrones());

        assertEquals(Settings.NUM_DRONES_INICIAIS, lista_temp.size());

        int id1 = lista_temp.get(0);
        int id2 = lista_temp.get(1);
        int id3 = lista_temp.get(2);

        //forcar valores de integridade diferentes
        navio.setIntegridadeDrone(id1,80);
        navio.setIntegridadeDrone(id2,60);
        navio.setIntegridadeDrone(id3,30);

        List<Integer> ordenados = navio.getDronesOrdenadosIntegridade();

        assertEquals(id3, ordenados.get(0));
        assertEquals(id2, ordenados.get(1));
        assertEquals(id1, ordenados.get(2));
    }
}