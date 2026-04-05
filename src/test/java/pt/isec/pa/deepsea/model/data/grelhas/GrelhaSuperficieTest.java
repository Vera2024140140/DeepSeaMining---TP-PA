package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.Utilidades;
import pt.isec.pa.deepsea.model.data.elementos.*;

import static org.junit.jupiter.api.Assertions.*;


class GrelhaSuperficieTest {

    private GrelhaSuperficie grelha;

    @BeforeEach
    void setUp() {
        Utilidades.reiniciarContadores();
        grelha = new GrelhaSuperficie();
    }

    @Test
    void getLinhas() {
        assertEquals(Settings.LINHAS_SUPERFICIE, grelha.getLinhas());
    }

    @Test
    void getColunas() {
        assertEquals(Settings.COLUNAS_SUPERFICIE, grelha.getColunas());
    }

    @Test
    void getFosso() {
        FossoMarinho fosso = grelha.getFosso(0,0);
        assertEquals(Settings.LINHAS_FOSSO, fosso.getLinhas());
        assertEquals(Settings.COLUNAS_FOSSO, fosso.getColunas());

        for(int l = 0; l < Settings.LINHAS_SUPERFICIE; l++) {
            for(int c = 0; c < Settings.COLUNAS_SUPERFICIE; c++) {
                assertNotNull(grelha.getFosso(l, c));
            }
        }
    }

    @Test
    void getFundo() {
        FundoMarinho fundo = grelha.getFundo(0,0);
        assertEquals(Settings.LINHAS_FUNDO, fundo.getLinhas());
        assertEquals(Settings.COLUNAS_FUNDO, fundo.getColunas());

        for(int l = 0; l < Settings.LINHAS_SUPERFICIE; l++) {
            for(int c = 0; c < Settings.COLUNAS_SUPERFICIE; c++) {
                assertNotNull(grelha.getFundo(l, c));
            }
        }
    }

    @Test
    void geracaoMinerios(){
        int nminerios = 0;

        for (int l = 0; l < grelha.getLinhas(); l++) {
            for (int c = 0; c < grelha.getColunas(); c++) {
                FundoMarinho fundo = grelha.getFundo(l, c);
                for (int lf = 0; lf < fundo.getLinhas(); lf++) {
                    for (int cf = 0; cf < fundo.getColunas(); cf++) {
                        if (fundo.getComponente(lf, cf) instanceof Minerio){
                            nminerios++;
                        }
                    }
                }

            }
        }

        if (Settings.MODO_DEFESA){
            assertEquals(Settings.DEFESA_MINERIOS * Settings.LINHAS_SUPERFICIE * Settings.COLUNAS_SUPERFICIE, nminerios);
        } else {
            assertTrue(Settings.MINERIO_ZONA_MIN * Settings.LINHAS_SUPERFICIE * Settings.COLUNAS_SUPERFICIE <= nminerios);
        }

    }

    @Test
    void geracaoArtefactos(){
        int nartefactos = 0;
        for (int l = 0; l < grelha.getLinhas(); l++) {
            for (int c = 0; c < grelha.getColunas(); c++) {
                FundoMarinho fundo = grelha.getFundo(l, c);
                for (int lf = 0; lf < fundo.getLinhas(); lf++) {
                    for (int cf = 0; cf < fundo.getColunas(); cf++) {
                        if (fundo.getComponente(lf, cf) instanceof Artefacto){
                            nartefactos++;
                        }
                    }
                }
            }
        }
        if (Settings.MODO_DEFESA){
            assertTrue(Settings.DEFESA_NUM_ARTEFACTOS_LADO * 2 <= nartefactos);
        } else {
            assertEquals(Settings.NUM_ARTEFACTOS, nartefactos);
        }
    }
}