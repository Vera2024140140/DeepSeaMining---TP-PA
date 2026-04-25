package pt.isec.pa.deepsea.model.data.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {
    private Drone d;

    @BeforeEach
    void setUp() {
        d = new Drone();
    }

    @Test
    void testarValoresIniciais() {
        assertEquals(Settings.DRONE_COMBUSTIVEL_MAX, d.getCombustivel());
        assertEquals(Settings.DRONE_COMBUSTIVEL_MAX, d.getCombustivelMax());
        assertEquals(Settings.DRONE_INTEGRIDADE_MAX, d.getIntegridadeMax());
        assertEquals(-1, d.getLinha());
        assertEquals(-1, d.getColuna());
        assertEquals(0, d.getMinerios());
        assertTrue(d.getArtefactos().isEmpty());
    }

    @Test
    void setCombustivelAdicionarPosLimite() {
        double resto = d.addCombustivel(10);
        assertEquals(10,resto,"devolve 10");
        assertEquals(Settings.DRONE_COMBUSTIVEL_MAX, d.getCombustivel());
    }

    @Test
    void setCombustivelValorNegativo() {
        d.setCombustivel(-5);
        assertNotEquals(-5, d.getCombustivel(), "Deve devolver o valor atual de combustivel");
        assertEquals(Settings.DRONE_COMBUSTIVEL_MAX, d.getCombustivel());
    }

    @Test
    void addCombustivel() {
        //forcar combustivel a baixar do max
        d.setCombustivel(50);
        assertEquals(50, d.getCombustivel());

        d.addCombustivel(45);
        assertEquals(95, d.getCombustivel());

        double resto = d.addCombustivel(106);
        assertEquals(200, d.getCombustivel());
        assertEquals(1, resto); //retorna 1 e completa o tanque
    }

    @Test
    void addCombustivelPosLimite() {
        d.addCombustivel(1000.0);
        assertEquals(Settings.DRONE_COMBUSTIVEL_MAX, d.getCombustivel(), "O tanque deve ficar no limite");
    }

    @Test
    void setCombustivelMax() {
        d.setCombustivelMax(500.0);
        assertEquals(500.0, d.getCombustivelMax());

        d.setCombustivelMax(-10.0);
        assertEquals(500.0, d.getCombustivelMax(), "Nao permite valores negativos");
    }

    @Test
    void setIntegridadeCasco() {
        d.setIntegridadeCasco(50);
        assertEquals(50, d.getIntegridadeCasco());
    }

    @Test
    void setIntegridadeMax() {
        d.setIntegridadeMax(100);
        assertEquals(100, d.getIntegridadeMax());
    }

    @Test
    void testLocalizacao() {
        d.setLocalizacao(5, 10);
        assertEquals(5, d.getLinha());
        assertEquals(10, d.getColuna());
    }

    @Test
    void setMinerios() {
        d.addMinerios();
        assertEquals(10, d.getMinerios());
    }

    @Test
    void addArtefactos() {
        Artefacto a1 = new Artefacto(1,1);
        d.addArtefacto(a1);
        List<Integer> idsNoNome = d.getArtefactos();

        assertEquals(1, idsNoNome.size(), "A lista devia de ter 1 artefacto");
        assertTrue(idsNoNome.contains(a1.getId()), "Deve conter a");
    }
}