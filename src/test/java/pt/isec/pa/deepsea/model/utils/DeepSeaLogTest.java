package pt.isec.pa.deepsea.model.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.deepsea.model.data.Settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe {@link DeepSeaLog}.
 * Responsável por validar as operações de registo, limpeza, eliminação
 * de entradas e gravação de ficheiros de log da aplicação.
 */
class DeepSeaLogTest {

    private DeepSeaLog deepSeaLog;

    /**
     * Configuração inicial executada antes de cada teste.
     * Obtém a instância Singleton do {@link DeepSeaLog} e garante
     * que os registos são limpos, providenciando um ambiente limpo e isolado.
     */
    @BeforeEach
    void setUp() {
        deepSeaLog = DeepSeaLog.getInstance();
        deepSeaLog.reset(); // Garantir que o log está vazio antes de cada teste
    }

    /**
     * Rotina de limpeza executada após cada teste.
     * Apaga o ficheiro de log físico criado no sistema (se existir),
     * garantindo que não ficam ficheiros residuais após a execução dos testes.
     */
    @AfterEach
    void EliminarFicheiroLog() {
        File logFile = new File(Settings.FICHEIRO_LOG);
        if (logFile.exists()) {
            logFile.delete();
        }
    }

    /**
     * Testa o método de adição de registos (log).
     * Verifica se strings válidas são adicionadas com sucesso à lista e assegura
     * que valores inválidos (nulos, vazios ou apenas com espaços em branco)
     * são ignorados corretamente.
     */
    @Test
    void testLog() {
        deepSeaLog.log("teste");
        List<String> logs = deepSeaLog.getLog();

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).contains("teste"));

        // Testar entradas inválidas
        deepSeaLog.log(null);
        deepSeaLog.log("");
        deepSeaLog.log("   ");

        assertEquals(1, deepSeaLog.getLog().size());
    }

    /**
     * Testa o método de limpeza (reset) da lista de registos.
     * Verifica se, após adicionar elementos ao log, a invocação do método
     * resulta numa lista de logs totalmente vazia.
     */
    @Test
    void testReset() {
        deepSeaLog.log("teste1");
        deepSeaLog.log("teste2");

        assertEquals(2, deepSeaLog.getLog().size());

        deepSeaLog.reset();

        assertTrue(deepSeaLog.getLog().isEmpty());
    }

    /**
     * Testa a eliminação de um registo específico com base no seu índice.
     * Valida a remoção bem-sucedida de um índice válido (verificando o reajuste
     * da lista) e testa o comportamento do método ao receber
     * índices inválidos (negativos ou maiores que o tamanho da lista).
     */
    @Test
    void testEliminar() {
        deepSeaLog.log("teste1");
        deepSeaLog.log("teste2");
        deepSeaLog.log("teste3");

        // Testar eliminação válida
        boolean resultado = deepSeaLog.eliminar(1);
        assertTrue(resultado);

        List<String> logs = deepSeaLog.getLog();
        assertEquals(2, logs.size());
        assertTrue(logs.get(0).contains("teste1"));
        assertTrue(logs.get(1).contains("teste3"));

        // Testar eliminações inválidas
        assertFalse(deepSeaLog.eliminar(-1));
        assertFalse(deepSeaLog.eliminar(5));
    }

    /**
     * Testa a funcionalidade de gravação dos logs em memória para um ficheiro de texto.
     * Verifica se o método sinaliza sucesso, se o ficheiro físico é efetivamente
     * criado no disco e se as linhas escritas correspondem exatamente aos dados em memória.
     * * @throws IOException Se ocorrer um erro durante a leitura das linhas do ficheiro criado.
     */
    @Test
    void testGravarLog() throws IOException {
        deepSeaLog.log("Teste Gravar 1");
        deepSeaLog.log("Teste Gravar 2");

        boolean resultado = deepSeaLog.gravarLog();
        assertTrue(resultado);

        File logFile = new File(Settings.FICHEIRO_LOG);
        assertTrue(logFile.exists());

        // Verificar o conteúdo gravado
        List<String> linhas = Files.readAllLines(logFile.toPath());
        assertEquals(2, linhas.size());
        assertTrue(linhas.get(0).contains("Teste Gravar 1"));
        assertTrue(linhas.get(1).contains("Teste Gravar 2"));
    }
}