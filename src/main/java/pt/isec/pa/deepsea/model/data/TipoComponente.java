package pt.isec.pa.deepsea.model.data;
/**
 * Enumeração que define todos os tipos de componentes (entidades e obstáculos)
 * que podem existir nas células das grelhas do jogo (Fundo Marinho e Fosso).
 * <p>
 * A utilização desta enumeração facilita a identificação do conteúdo
 * de uma célula. Isto permite que o modelo de dados aplique as regras do jogo
 * corretas (como causar dano, recolher recursos ou bloquear movimento) sem a
 * necessidade de recorrer a verificações de tipo complexas (ex: {@code instanceof}).
 * @author VeraRibeiro2024140140
 */
public enum TipoComponente {
    ROCHA,
    MINERIO,
    CORRENTE,
    ANIMALMARINHO,
    MONSTRO,
    ARTEFACTO
}
