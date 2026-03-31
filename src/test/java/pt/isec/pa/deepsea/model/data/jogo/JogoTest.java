package pt.isec.pa.deepsea.model.data.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogoTest {
    private Jogo jogo;

    @BeforeEach
    void setUp() {
        jogo = new Jogo();
    }

    //teste de instancias
    @Test
    void testeInicializacao() {
        assertNotNull(jogo.getNavio(), "O navio deve ser instanciado no construtor do jogo");
        assertNotNull(jogo.getGrelhaSuperficie(), "A grelha deve ser instanciado no construtor do jogo");
    }
}