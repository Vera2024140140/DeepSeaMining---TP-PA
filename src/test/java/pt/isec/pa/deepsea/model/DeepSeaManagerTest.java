package pt.isec.pa.deepsea.model;


import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pt.isec.pa.deepsea.model.data.Settings.FICHEIRO_SAVE;

public class DeepSeaManagerTest {
    private DeepSeaManager manager;

    /*@BeforeEach
    void setUp() {
        manager = new DeepSeaManager();
        manager.limparLog();
    }*/

    /*@AfterEach
    void apagarFicheiros() {
        new File(FICHEIRO_SAVE).delete();
        new File(FICHEIRO_LOG).delete();
    }*/

    @Test
    void testNovoJogo() {
        assertEquals(DeepSeaState.SUPERFICIE_STATE, manager.getState());
        assertEquals(3, manager.getIdsDronesNavio().size());
    }

    @Test
    void testListasImutaveis() {
        List<Integer> artefactos = manager.getIdsArtefactosNavio();
        //tentar adicionar um artefacto falso a lista devolvida pelo Manager
        assertThrows(UnsupportedOperationException.class, () -> {
            artefactos.add(999);
        });
    }

    @Test
    void testSerializacao() {
        //teste de serialização
        // comb inicial , comb pos mover
        // save game, comb com gasto de mov
        // carregar jogo guardado (load)
        // verf comb
        double combInicial = manager.getCombustivelNavio();
        //manager.moverNavio(Direcao.DIREITA);
        double combAposMover = manager.getCombustivelNavio();

        assertTrue(combAposMover < combInicial);
        //save game
        //assertTrue(manager.gravarJogo());
        //manager.moverNavio(Direcao.DIREITA);

        DeepSeaManager novoManager = new DeepSeaManager();
        assertEquals(combInicial, novoManager.getCombustivelNavio());
        //load game
        //assertTrue(novoManager.carregarJogo());

        assertEquals(combAposMover, novoManager.getCombustivelNavio());
        assertEquals(DeepSeaState.SUPERFICIE_STATE, manager.getState());
    }

    @Test
    void testCarregarFicheiroInexistente() {
        new File(FICHEIRO_SAVE).delete();

        //boolean res = manager.carregarJogo();

        //assertFalse(res);
    }
}
