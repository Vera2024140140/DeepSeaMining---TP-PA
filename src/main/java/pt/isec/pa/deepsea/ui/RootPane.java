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
        //adicionar ao stackPane sobrepostos
        barraSuperficie = new BarraLateralSuperficie(manager);
        barraFosso = new BarraLateralFosso(manager);
        barraFundo = new BarraLateralFundo(manager);
        oficinaPane = new OficinaPane(manager);
        acabouCanvas = new AcabouCanvas(manager);

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
            case DESCIDA_STATE, SUBIDA_STATE -> {
                stackPane.getChildren().add(fossoCanvas);
                setRight(barraFosso);
                stackPane.setAlignment(Pos.CENTER);
            }
            case FUNDO_STATE -> {
                stackPane.getChildren().add(fundoCanvas);
                setRight(barraFundo);
            }
            case PUZZLE_STATE -> {
                stackPane.getChildren().add(puzzleCanvas);
                setRight(barraPuzzle);
                stackPane.setAlignment(Pos.CENTER);
            }
            case ACABOU_STATE -> {
                stackPane.getChildren().add(acabouCanvas);
                setRight(null);
            }
            default -> {

            }
        }
    }
}
