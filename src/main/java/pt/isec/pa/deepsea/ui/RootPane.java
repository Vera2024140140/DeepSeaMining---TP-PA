package pt.isec.pa.deepsea.ui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.deepsea.model.DeepSeaManager;

public class RootPane extends BorderPane {
    DeepSeaManager manager;

    StackPane stackPane;


    public RootPane(DeepSeaManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        //vai contar todos os Canvasses sobrepostos
        stackPane = new StackPane();

        //adicionar canvas aqui
        setCenter(stackPane);

        // adicionar MenuBar no setTop() e a InfoBox no setRight() depois
    }
    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> update());
    }

    private void update() {
        //aqui verificamos o get state do manager e de acordo  com isso damos upd dos canvas
        //temos aqui apenas o canva do estado atual
    }
}
