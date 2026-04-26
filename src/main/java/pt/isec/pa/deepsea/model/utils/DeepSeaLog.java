package pt.isec.pa.deepsea.model.utils;

import pt.isec.pa.deepsea.model.data.Settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo registo (logging) de eventos e mensagens da aplicação.
 * Implementa o padrão de desenho Singleton.
 */
public class DeepSeaLog {

    /**
     * Instância única (Singleton) da classe DeepSeaLog.
     */
    private static DeepSeaLog _instance = null;

    /**
     * Obtém a instância única da classe DeepSeaLog.
     * Se a instância ainda não existir, é criada uma nova.
     *
     * @return A instância Singleton de {@link DeepSeaLog}.
     */
    public static DeepSeaLog getInstance() {
        if (_instance == null)
            _instance = new DeepSeaLog();
        return _instance;
    }

    /**
     * Lista interna que armazena as mensagens de log em memória.
     */
    protected ArrayList<String> log;

    /**
     * Construtor privado para impedir a instanciação direta (padrão Singleton).
     * Inicializa a lista de logs vazia.
     */
    private DeepSeaLog() {
        log = new ArrayList<>();
    }

    /**
     * Limpa todos os registos atuais em memória, esvaziando a lista de logs.
     */
    public void reset() {
        log.clear();
    }

    /**
     * Adiciona uma nova mensagem ao log.
     * A mensagem é antecedida por um carimbo de data/hora (timestamp) formatado
     * de acordo com a configuração {@link Settings#FORMATO_TEMPO_LOG}.
     * Se a mensagem for nula ou contiver apenas espaços em branco, é ignorada.
     *
     * @param msg A mensagem de texto a ser registada.
     */
    public void log(String msg) {
        if (msg == null || msg.isBlank()) return;
        log.add("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(Settings.FORMATO_TEMPO_LOG)) + "] " + msg);
    }

    /**
     * Retorna uma cópia imutável (read-only) da lista atual de registos.
     *
     * @return Uma {@link List} de strings contendo todos os logs.
     */
    public List<String> getLog() {
        return List.copyOf(log);
    }

    /**
     * Elimina um registo específico da lista com base no seu índice.
     *
     * @param i O índice do registo a ser eliminado.
     * @return {@code true} se o registo foi eliminado com sucesso;
     * {@code false} se o índice for inválido (menor que 0 ou maior/igual ao tamanho da lista).
     */
    public boolean eliminar(int i){
        if (i < 0 || i >= log.size()) return false;
        log.remove(i);
        return true;
    }

    /**
     * Grava todos os registos mantidos em memória num ficheiro de texto,
     * definido pela configuração {@link Settings#FICHEIRO_LOG}.
     *
     * @return {@code true} se o ficheiro for gravado com sucesso;
     * {@code false} se ocorrer algum erro durante a escrita do ficheiro (ex: permissões, I/O).
     */
    public boolean gravarLog(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Settings.FICHEIRO_LOG))) {
            for (String s : log) {
                bw.write(s);
                bw.newLine();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}