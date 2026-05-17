package pt.isec.pa.deepsea.model.data.elementos;

import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.TipoComponente;

public class Artefacto extends Componente{
    private static final long serialVersionUID = 6L;
    private static int contadorIDArtefactos = Settings.ID_ARTEFACTOS_INICIAL;
    private final int id;
    public Artefacto(int linha, int coluna){
        super(linha,coluna);
        this.id = contadorIDArtefactos;
        contadorIDArtefactos++;
    }

    public int getId() {
        return id;
    }
    @Override
    public TipoComponente getTipo(){
        return TipoComponente.ARTEFACTO;
    }
    public static void resetContadorArtefactos() {
        contadorIDArtefactos = Settings.ID_ARTEFACTOS_INICIAL;
    }
}
