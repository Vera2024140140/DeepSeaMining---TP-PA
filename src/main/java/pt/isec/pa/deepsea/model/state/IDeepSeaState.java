package pt.isec.pa.deepsea.model.state;


import pt.isec.pa.deepsea.model.Direcao;

/**
 * Interface principal do Padrão State para o jogo Deep Sea.
 * <p>
 * Define exclusivamente as ações do utilizador que podem ser disparadas
 * em cada momento do jogo. As transições de estado nunca são desencadeadas
 * automaticamente pelo sistema — resultam sempre de uma ação aqui declarada.
 * Cada estado concreto implementa esta interface (através do
 * {@link DeepSeaStateAdapter}) e sobrescreve apenas os métodos que fazem
 * sentido no seu contexto.
 * <p>
 * O método {@link #mover(Direcao) mover} é polimórfico: na superfície move o
 * navio, em descida/subida/fundo move o drone ativo e no puzzle move uma peça.
 * A semântica é definida por cada estado concreto.
 * <p>
 * Todos os métodos devolvem {@code boolean}: {@code true} indica que a ação
 * é válida no estado atual e foi executada com sucesso; {@code false} indica
 * que a ação é inválida para o estado atual ou falhou pelas regras do jogo.
 * @author Diogo2024152576
 * @author Vera2024140140
 */

public interface IDeepSeaState {

    boolean iniciarDescida();
    boolean iniciarSubida();
    boolean apanharArtefacto();
    boolean recolherMinerio();
    boolean abrirOficina();
    boolean fecharOficina();
    boolean mover(Direcao dir);
    boolean selecionarDrone(int idDrone);
    boolean abastecerDrone(double litros);
    boolean repararDrone(int integridade);
    boolean melhorarTanqueDrone();
    boolean melhorarIntegridadeDrone();

    DeepSeaState getState();
}
