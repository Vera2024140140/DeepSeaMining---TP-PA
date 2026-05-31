package pt.isec.pa.deepsea.model.data.elementos;
import pt.isec.pa.deepsea.model.data.Settings;

/**
 * Classe abstrata que representa um obstáculo no fosso.
 * Cada obstáculo tem um id único gerado automaticamente.
 * Subclasses: {@link AnimalMarinho} e {@link Corrente}.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public abstract class Obstaculo extends Componente{
    private static final long serialVersionUID = 2L;
    private static int contadorID = Settings.ID_OBSTACULO_INICIAL;
    private final int id;

    public Obstaculo(int linha, int coluna){
        super(linha,coluna);
        this.id = contadorID;
        contadorID++;

    }

    public int getId() {
        return id;
    }
    public static void resetContadorObstaculos() {
        contadorID = Settings.ID_OBSTACULO_INICIAL;
    }
}
