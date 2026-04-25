package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;

import static org.junit.jupiter.api.Assertions.*;

class FundoMarinhoTest {

    private FundoMarinho fundo;

    @BeforeEach
    void setUp() {
        fundo = new FundoMarinho();
    }

    @Test
    void testColocarMinerio() {
        assertTrue(fundo.colocarMinerio(0, 0, 3));
        Assertions.assertEquals(TipoComponente.MINERIO, fundo.getTipoComponente(0, 0));
        assertFalse(fundo.colocarMinerio(0, 0, 5));  // célula ocupada
        assertFalse(fundo.colocarMinerio(-1, 0, 1)); // fora dos limites
    }

    @Test
    void testColocarArtefacto() {
        assertTrue(fundo.colocarArtefacto(1, 1));
        assertEquals(TipoComponente.ARTEFACTO, fundo.getTipoComponente(1, 1));
        assertFalse(fundo.colocarArtefacto(1, 1));   // célula ocupada
        assertFalse(fundo.colocarArtefacto(-1, -1)); // fora dos limites
    }

    @Test
    void testCelulasReveladas() {
        for (int l = 0; l < fundo.getLinhas(); l++) {
            for (int c = 0; c < fundo.getColunas(); c++) {
                if (Settings.MODO_DEFESA) assertTrue(fundo.isRevelada(l, c));
                else assertFalse(fundo.isRevelada(l, c));
            }
        }
    }

    @Test
    void testaGerarMonstrosPosicoes() {
        if (!Settings.MODO_DEFESA) {
            for (int c = 0; c < fundo.getColunas(); c++) fundo.setRevelada(0, c);
            fundo.gerarMonstros();
            int monstros = 0;
            for (int i = 0; i < fundo.getLinhas(); i++) {
                for (int j = 0; j < fundo.getColunas(); j++) {
                    if (fundo.getTipoComponente(i, j) == TipoComponente.MONSTRO) {
                        monstros++;
                        assertFalse(fundo.isRevelada(i, j));
                    }
                }
            }
            assertTrue(monstros > 0);
        }
    }

    @Test
    void testaGerarMonstros() {
        fundo.gerarMonstros();
        int monstros = 0;
        for (int i = 0; i < fundo.getLinhas(); i++)
            for (int j = 0; j < fundo.getColunas(); j++)
                if (fundo.getTipoComponente(i, j) == TipoComponente.MONSTRO) monstros++;

        if (Settings.MODO_DEFESA) assertEquals(Settings.DEFESA_MONSTROS, monstros);
        else {
            assertTrue(monstros >= Settings.MONSTROS_FUNDO_MIN);
            assertTrue(monstros <= Settings.MONSTROS_FUNDO_MAX);
        }
    }
}