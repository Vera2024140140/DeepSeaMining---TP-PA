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

public class PuzzleCanvas extends Canvas {private  final DeepSeaManager manager;
    double CELL_SIZE = Settings.CELL_SIZE * 3.25;

    public PuzzleCanvas(DeepSeaManager manager) {
        super(
                Settings.PUZZLE_GRELHA * (Settings.CELL_SIZE * 3.25),
                Settings.PUZZLE_GRELHA * (Settings.CELL_SIZE * 3.25)
        );
        this.manager = manager;
        registarHandlers();
        update();
    }

    private void registarHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PUZZLE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_FUNDO,
                evt -> update());
    }

    private void update() {
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