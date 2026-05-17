package pt.isec.pa.deepsea.ui;

import pt.isec.pa.deepsea.model.data.Settings;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralSuperficie;
import pt.isec.pa.deepsea.ui.canvas.SuperficieCanvas;

public class RootPane extends BorderPane {
    DeepSeaManager manager;

    StackPane stackPane;
    private BarraLateralSuperficie barraSuperficie;

    public RootPane(DeepSeaManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        //vai contar todos os Canvasses sobrepostos
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle(
                "-fx-background-color : " + Settings.BG_GRELHAS + ";"
        );

        //correr o superficie canvas (TESTE)
        SuperficieCanvas superficieCanvas = new SuperficieCanvas(manager);
        stackPane.getChildren().add(superficieCanvas);

        barraSuperficie = new BarraLateralSuperficie(manager);

        // adicionar MenuBar no setTop() e a InfoBox no setRight() depois
        AppMenuBar menuBar = new AppMenuBar(manager);
        setTop(menuBar);
    }
    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> update());
    }

    private void update() {
        //aqui verificamos o get state do manager e de acordo  com isso damos upd dos canvas
        //temos aqui apenas o canva do estado atual
    }
}
