package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.*;

import static org.junit.jupiter.api.Assertions.*;

class FundoMarinhoTest {

    private FundoMarinho fundo;

    @BeforeEach
    void setUp() {
        Utilidades.reiniciarContadores();
        fundo = new FundoMarinho();
    }

    @Test
    void testColocarMinerio() {
        assertTrue(fundo.colocarMinerio(0, 0, 3));
        assertInstanceOf(Minerio.class, fundo.getComponente(0, 0));
        // celula ocupada nao aceita outro
        assertFalse(fundo.colocarMinerio(0, 0, 5));
        // fora dos limites
        assertFalse(fundo.colocarMinerio(-1, 0, 1));
    }

    @Test
    void testColocarArtefacto() {
        assertTrue(fundo.colocarArtefacto(1, 1));
        assertInstanceOf(Artefacto.class, fundo.getComponente(1, 1));
        // celula ocupada
        assertFalse(fundo.colocarArtefacto(1, 1));
        // fora dos limites
        assertFalse(fundo.colocarArtefacto(-1, -1));
    }

    @Test
    void testCelulasReveladas() {
        for (int l = 0; l < fundo.getLinhas(); l++) {
            for (int c = 0; c < fundo.getColunas(); c++) {
                if (Settings.MODO_DEFESA) {
                    assertTrue(fundo.isRevelada(l, c));
                } else {
                    assertFalse(fundo.isRevelada(l, c));
                }
            }
        }
    }
    @Test
    void testaGerarMonstrosPosicoes(){
        if(!Settings.MODO_DEFESA){
            for(int c = 0; c < fundo.getColunas(); c++){
                fundo.setRevelada(0,c);
            }
            fundo.gerarMonstros();
            int monstros = 0;
            for (int i = 0; i < fundo.getLinhas(); i++){
                for (int j = 0; j < fundo.getColunas(); j++){
                    if (fundo.getComponente(i,j) instanceof Monstro){
                        monstros++;
                        assertFalse(fundo.isRevelada(i,j));
                    }
                }
            }
            assertTrue(monstros > 0);
        }
    }

    @Test
    void testaGerarMonstros(){
        fundo.gerarMonstros();
        int monstros = 0;
        for (int i = 0; i < fundo.getLinhas(); i++){
            for (int j = 0; j < fundo.getColunas(); j++){
                if (fundo.getComponente(i,j) instanceof Monstro){
                    monstros++;
                }
            }
        }
        if(Settings.MODO_DEFESA){
            assertEquals(Settings.DEFESA_MONSTROS,monstros);
        }else{
            assertTrue(monstros >= Settings.MONSTROS_FUNDO_MIN);
            assertTrue(monstros <= Settings.MONSTROS_FUNDO_MAX);
        }

    }
}