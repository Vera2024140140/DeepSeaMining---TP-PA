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
    AppMenuBar menuBar;
    StackPane barrasPane;
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

        // para que nao exista um limite
        stackPane.setMinWidth(0);
        stackPane.setMinHeight(0);

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

        //adicionar ao stackPane sobrepostos as canvas
        stackPane.getChildren().addAll(
            superficieCanvas,
            fossoCanvas,
            fundoCanvas,
            puzzleCanvas,
            oficinaPane,
            acabouCanvas
        );

        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            resizeAll(newValue.doubleValue(), this.getHeight());
        });
        this.heightProperty().addListener((observable, oldValue, newValue) -> {
            resizeAll(this.getWidth(), newValue.doubleValue());
        });

        setCenter(stackPane);

        barrasPane = new StackPane();
        //adicionar ao stackPane sobrepostos as barras
        barrasPane.getChildren().addAll(barraSuperficie,barraFundo,barraFosso,barraPuzzle);
        setRight(barrasPane);

        // adicionar MenuBar no setTop()
        menuBar = new AppMenuBar(manager);
        setTop(menuBar);
    }

    private void resizeAll(double rootW, double rootH) {
        double w = rootW - Settings.WIDTH_BARRA_LATERAL - 40;
        double h = rootH - 40;

        // resize para cada um dos canvas
        superficieCanvas.resizeJanela(w, h);
        fossoCanvas.resizeJanela(w, h);
        fundoCanvas.resizeJanela(w, h);
        puzzleCanvas.resizeJanela(w, h);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evt -> update());
    }

    private void update() { }
}
