package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.Direcao;
/**
 * Interface principal do Padrão State para o jogo Deep Sea.
 * <p>
 * Define todos os eventos e ações possíveis que podem ser desencadeados pelo utilizador
 * ou pelo sistema ao longo do jogo. Cada estado específico implementa esta interface
 * (através do {@link DeepSeaStateAdapter}) e substitui apenas os métodos
 * que fazem sentido no seu contexto.
 * @author Diogo2024152576
 */

public interface IDeepSeaState {

    boolean iniciarDescida();
    boolean chegarFundo();
    boolean iniciarSubida();
    boolean apanharArtefacto();
    boolean recolherMinerio();
    boolean fimPuzzle();
    boolean subirSuperficie();
    boolean perderDrone();
    boolean avaliarFimJogo();
    boolean abrirOficina();
    boolean fecharOficina();
    boolean moverNavio(Direcao dir);
    boolean moverDroneFosso(Direcao dir);
    boolean moverDroneFundo(Direcao dir);
    boolean selecionarDrone(int idDrone);
    boolean abastecerDrone(double litros);
    boolean repararDrone(int integridade);
    boolean melhorarTanqueDrone();
    boolean melhorarIntegridadeDrone();
    boolean moverPeca(Direcao dir);

    DeepSeaState getState();
}