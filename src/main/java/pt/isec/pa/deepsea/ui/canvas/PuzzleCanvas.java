package pt.isec.pa.deepsea.ui.canvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

public class PuzzleCanvas extends DeepSeaCanvas {

    public PuzzleCanvas(DeepSeaManager manager) {
        super(
                manager,
                Settings.PUZZLE_GRELHA ,
                Settings.PUZZLE_GRELHA
        );
        boolean ativo = manager.getState() == DeepSeaState.PUZZLE_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    @Override
    protected void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PUZZLE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_FUNDO,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.PUZZLE_STATE;
            setVisible(ativo);
            if (ativo) update();
        });
    }

    @Override
    protected void update() {
        GraphicsContext gc = getGraphicsContext2D();
        interface2D(gc);
    }

    private void interface2D(GraphicsContext gc) {
        int tam = Settings.PUZZLE_GRELHA;

        gc.clearRect(0, 0, getWidth(), getHeight());

        int[][] matrizPuzzle = manager.getMatrizPuzzle();

        // --- GRELHA PUZZLE----
        for (int l = 0; l < tam; l++) {
            for (int c = 0; c < tam; c++) {
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // --- Pecas do jogo---
                if (matrizPuzzle != null) {
                    int peca = matrizPuzzle[l][c];
                    if (peca != 0) {
                        gc.setFill(Color.BLACK);
                        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.setTextBaseline(VPos.CENTER);

                        double centerX = (c * CELL_SIZE) + (CELL_SIZE / 2.0);
                        double centerY = (l * CELL_SIZE) + (CELL_SIZE / 2.0);

                        gc.fillText(String.valueOf(peca), centerX, centerY);
                    }
                }
            }
        }
    }
}