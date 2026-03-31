package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.*;

import static org.junit.jupiter.api.Assertions.*;

class FossoMarinhoTest {

    private FossoMarinho fosso;

    @BeforeEach
    void setUp() {
        Utilidades.reiniciarContadores();
        fosso = new FossoMarinho();
    }

    @Test
    void testFossoNavegavel() {
        int centroC = fosso.getColunas() / 2;
        for (int l = 0; l < fosso.getLinhas(); l++) {
            assertInstanceOf(Rocha.class, fosso.getComponente(l, 0), "Linha " + l + ": deve ter rocha no lado esquerdo");
            assertInstanceOf(Rocha.class, fosso.getComponente(l, fosso.getColunas() - 1), "Linha " + l + ": deve ter rocha no lado direito");
            assertNull(fosso.getComponente(l, centroC), "Linha " + l + ": o centro deve estar livre");
        }
    }

    @Test
    void testRochasMaximo50Porcento() {
        int max = (int) (fosso.getColunas() * Settings.ROCHAS_PERCENTAGEM_MAX);
        for (int l = 0; l < fosso.getLinhas(); l++) {
            int rochas = 0;
            for (int c = 0; c < fosso.getColunas(); c++) {
                if (fosso.getComponente(l, c) instanceof Rocha){
                    rochas++;
                }
            }
            assertTrue(rochas <= max, "Linha " + l + ": tem " + rochas + " rochas, maximo e " + max);
        }
    }

}