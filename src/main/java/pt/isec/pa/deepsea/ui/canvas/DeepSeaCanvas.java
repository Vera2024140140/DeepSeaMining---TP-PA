package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

public abstract class DeepSeaCanvas extends Canvas {
    protected final DeepSeaManager manager;
    protected  double CELL_SIZE = Settings.CELL_SIZE;
    private final int cols, lins;

    public DeepSeaCanvas(DeepSeaManager manager, int lins, int cols) {
        super(
                cols * Settings.CELL_SIZE,
                lins * Settings.CELL_SIZE
        );
        this.manager = manager;
        this.cols = cols;
        this.lins = lins;
        registerHandlers();
        update();
    }

    public void resizeJanela(double largura, double altura) {
        if (largura <= 0 ||  altura <= 0) return;

        CELL_SIZE = (int) Math.min(largura / cols, altura / lins);
        setWidth(cols * CELL_SIZE);
        setHeight(lins * CELL_SIZE);
        update();
    }

    protected abstract void update();
    protected abstract void registerHandlers();
}