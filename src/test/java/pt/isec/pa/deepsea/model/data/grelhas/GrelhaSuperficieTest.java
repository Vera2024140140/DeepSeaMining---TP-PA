package pt.isec.pa.deepsea.model.data.grelhas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;

import static org.junit.jupiter.api.Assertions.*;

class GrelhaSuperficieTest {

    private GrelhaSuperficie grelha;

    @BeforeEach
    void setUp() {
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
    void dimensoesFosso() {
        assertEquals(Settings.LINHAS_FOSSO, grelha.getLinhasFosso(0,0));
        assertEquals(Settings.COLUNAS_FOSSO, grelha.getColunasFosso(0,0));
    }

    @Test
    void dimensoesFundo() {
        assertEquals(Settings.LINHAS_FUNDO, grelha.getLinhasFundo(0,0));
        assertEquals(Settings.COLUNAS_FUNDO, grelha.getColunasFundo(0,0));
    }

    @Test
    void geracaoMinerios() {
        int nminerios = 0;
        for (int l = 0; l < grelha.getLinhas(); l++)
            for (int c = 0; c < grelha.getColunas(); c++)
                for (int lf = 0; lf < grelha.getLinhasFundo(l, c); lf++)
                    for (int cf = 0; cf < grelha.getColunasFundo(l, c); cf++)
                        if (grelha.getTipoNoFundo(l, c, lf, cf) == TipoComponente.MINERIO) nminerios++;

        if (Settings.MODO_DEFESA)
            assertEquals(Settings.DEFESA_MINERIOS * Settings.LINHAS_SUPERFICIE * Settings.COLUNAS_SUPERFICIE, nminerios);
        else
            assertTrue(Settings.MINERIO_ZONA_MIN * Settings.LINHAS_SUPERFICIE * Settings.COLUNAS_SUPERFICIE <= nminerios);
    }

    @Test
    void geracaoArtefactos() {
        int nartefactos = 0;
        for (int l = 0; l < grelha.getLinhas(); l++)
            for (int c = 0; c < grelha.getColunas(); c++)
                for (int lf = 0; lf < grelha.getLinhasFundo(l, c); lf++)
                    for (int cf = 0; cf < grelha.getColunasFundo(l, c); cf++)
                        if (grelha.getTipoNoFundo(l, c, lf, cf) == TipoComponente.ARTEFACTO) nartefactos++;

        if (Settings.MODO_DEFESA)
            assertTrue(Settings.DEFESA_NUM_ARTEFACTOS_LADO * 2 <= nartefactos);
        else
            assertEquals(Settings.NUM_ARTEFACTOS, nartefactos);
    }
}