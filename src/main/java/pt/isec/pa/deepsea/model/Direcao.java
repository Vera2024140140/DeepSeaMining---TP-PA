package pt.isec.pa.deepsea.model;
/**
 * Enumeração que define as direções possíveis para os movimentos dentro do jogo.
 * <p>
 * É utilizada no projeto para ditar o movimento do navio na superfície,
 * do drone nos vários estados de exploração (fosso e fundo marinho), e das peças no minijogo.
 * Nas matrizes do jogo, estas direções traduzem-se em incrementos ou decrementos de
 * linhas e colunas.
 * @author Diogo2024152576
 */
public enum Direcao {
    CIMA, BAIXO, ESQUERDA, DIREITA
}
