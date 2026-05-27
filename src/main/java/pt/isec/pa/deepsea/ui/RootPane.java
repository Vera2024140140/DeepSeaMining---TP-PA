package pt.isec.pa.deepsea.ui;


import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralPuzzle;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralFosso;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralFundo;
import pt.isec.pa.deepsea.ui.barrasLaterais.BarraLateralSuperficie;
import pt.isec.pa.deepsea.ui.canvas.*;
import pt.isec.pa.deepsea.ui.oficina.OficinaPane;

import static pt.isec.pa.deepsea.model.state.DeepSeaState.*;

public class RootPane extends BorderPane {
    DeepSeaManager manager;

    StackPane stackPane;
    private SuperficieCanvas superficieCanvas;
    private FossoCanvas fossoCanvas;
    private FundoCanvas fundoCanvas;
    private BarraLateralSuperficie barraSuperficie;
    private BarraLateralFosso barraFosso;
    private BarraLateralFundo barraFundo;
    private OficinaPane oficinaPane;
    private BarraLateralPuzzle barraPuzzle;
    private PuzzleCanvas puzzleCanvas;
    private AcabouCanvas acabouCanvas;

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

        barraPuzzle = new BarraLateralPuzzle(manager);
        puzzleCanvas = new PuzzleCanvas(manager);
        superficieCanvas = new SuperficieCanvas(manager);
        fossoCanvas = new FossoCanvas(manager);
        fundoCanvas = new FundoCanvas(manager);
        barraSuperficie = new BarraLateralSuperficie(manager);
        barraFosso = new BarraLateralFosso(manager);
        barraFundo = new BarraLateralFundo(manager);
        oficinaPane = new OficinaPane(manager);
        acabouCanvas = new AcabouCanvas(manager);

        //adicionar ao stackPane sobrepostos
        stackPane.getChildren().addAll(
                superficieCanvas, fossoCanvas, fundoCanvas,
                puzzleCanvas, oficinaPane, acabouCanvas
        );
        setCenter(stackPane);

        // adicionar MenuBar no setTop()
        AppMenuBar menuBar = new AppMenuBar(manager);
        setTop(menuBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evt -> update());
    }

    private void update() {
        switch (manager.getState()){
            case SUPERFICIE_STATE -> setRight(barraSuperficie);
            case OFICINA_STATE -> setRight(null);
            case DESCIDA_STATE,SUBIDA_STATE -> setRight(barraFosso);
            case FUNDO_STATE ->  setRight(barraFundo);
            case PUZZLE_STATE -> setRight(barraPuzzle);
            case ACABOU_STATE -> setRight(null);
            default -> setRight(null);
        }

    }
}
