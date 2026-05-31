package pt.isec.pa.deepsea.ui.barrasLaterais;

import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

public class BarraLateralFosso extends BarraLateralNavegacao{
    public BarraLateralFosso(DeepSeaManager manager){
        super(manager);
        boolean ativo = manager.getState() == DeepSeaState.DESCIDA_STATE
                || manager.getState() == DeepSeaState.SUBIDA_STATE;
        setVisible(ativo);
        setManaged(ativo);
        if (ativo) update();
    }
    @Override
    void registerHandlers(){
        super.registerHandlers();
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.DESCIDA_STATE
                    || manager.getState() == DeepSeaState.SUBIDA_STATE;
            setVisible(ativo);
            setManaged(ativo);
            if (ativo) update();
        });
    }
}
