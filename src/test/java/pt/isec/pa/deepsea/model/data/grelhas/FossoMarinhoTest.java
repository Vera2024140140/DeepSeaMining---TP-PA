package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.TipoComponente;

import static org.junit.jupiter.api.Assertions.*;

class FossoMarinhoTest {

    private FossoMarinho fosso;

    @BeforeEach
    void setUp() {
        fosso = new FossoMarinho();
    }

    @Test
    void testFossoNavegavel() {
        int centroC = fosso.getColunas() / 2;
        for (int l = 0; l < fosso.getLinhas(); l++) {
            Assertions.assertEquals(TipoComponente.ROCHA, fosso.getTipoComponente(l, 0),
                    "Linha " + l + ": deve ter rocha no lado esquerdo");
            assertEquals(TipoComponente.ROCHA, fosso.getTipoComponente(l, fosso.getColunas() - 1),
                    "Linha " + l + ": deve ter rocha no lado direito");
            assertNull(fosso.getTipoComponente(l, centroC),
                    "Linha " + l + ": o centro deve estar livre");
        }
    }

    @Test
    void testRochasMaximo50Porcento() {
        int max = (int)(fosso.getColunas() * Settings.ROCHAS_PERCENTAGEM_MAX);
        for (int l = 0; l < fosso.getLinhas(); l++) {
            int rochas = 0;
            for (int c = 0; c < fosso.getColunas(); c++) {
                if (fosso.getTipoComponente(l, c) == TipoComponente.ROCHA) rochas++;
            }
            assertTrue(rochas <= max, "Linha " + l + ": tem " + rochas + " rochas, máximo é " + max);
        }
    }

    @Test
    void testaGerarObstaculos() {
        fosso.gerarObstaculos();
        int contadorAnimais = 0;
        int contadorCorrente = 0;
        for (int i = 0; i < fosso.getLinhas(); i++) {
            for (int j = 0; j < fosso.getColunas(); j++) {
                TipoComponente tipo = fosso.getTipoComponente(i, j);
                if (tipo == TipoComponente.ANIMALMARINHO) contadorAnimais++;
                else if (tipo == TipoComponente.CORRENTE) contadorCorrente++;
            }
        }
        if (Settings.MODO_DEFESA) {
            assertEquals(2, contadorAnimais + contadorCorrente);
            assertEquals(1, contadorAnimais);
            assertEquals(1, contadorCorrente);
        } else {
            assertTrue(contadorAnimais + contadorCorrente > 0);
            assertTrue(contadorAnimais + contadorCorrente <= Settings.OBSTACULOS_FOSSO_MAX);
        }
    }
}