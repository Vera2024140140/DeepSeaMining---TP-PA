package pt.isec.pa.deepsea.ui;

import pt.isec.pa.deepsea.model.data.Settings;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralFosso;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralFundo;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralSuperficie;
import pt.isec.pa.deepsea.ui.canvas.SuperficieCanvas;
import pt.isec.pa.deepsea.ui.oficina.OficinaPane;

public class RootPane extends BorderPane {
    DeepSeaManager manager;

    StackPane stackPane;
    private SuperficieCanvas superficieCanvas;
    private BarraLateralSuperficie barraSuperficie;
    private BarraLateralFosso barraFosso;
    private BarraLateralFundo barraFundo;
    private OficinaPane oficinaPane;

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

        superficieCanvas = new SuperficieCanvas(manager);
        barraSuperficie = new BarraLateralSuperficie(manager);
        oficinaPane = new OficinaPane(manager);

        setCenter(stackPane);

        // adicionar MenuBar no setTop() e a InfoBox no setRight() depois
        AppMenuBar menuBar = new AppMenuBar(manager);
        setTop(menuBar);
    }
    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> update());
    }

    private void update() {
        stackPane.getChildren().clear();
        switch (manager.getState()) {
            case SUPERFICIE_STATE -> {
                stackPane.getChildren().add(superficieCanvas);
                setRight(barraSuperficie);
            }
            case OFICINA_STATE -> {
                stackPane.getChildren().add(oficinaPane);
                setRight(null);
            }
            default -> {

            }
        }
    }
}
