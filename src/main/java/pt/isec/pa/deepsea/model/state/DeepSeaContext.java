package pt.isec.pa.deepsea.model.state;

import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.jogo.Jogo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DeepSeaContext implements Serializable {
    private Jogo jogo;
    private IDeepSeaState atual;

    public DeepSeaContext() {
        jogo = new Jogo();
        atual = SUPERFICIE_STATE.getInstance(this, jogo);
    }

    //pakcage private -> testes
    DeepSeaContext(Jogo jogo) {
        this.jogo = jogo;
        atual = SUPERFICIE_STATE.getInstance(this, jogo);
    }
}
