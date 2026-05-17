package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.ui.res.ImageLoader;

public class SuperficieCanvas extends Canvas {
    private static final int CELL_SIZE = 60;

    private final DeepSeaManager manager;

    public SuperficieCanvas(DeepSeaManager manager) {
        super(
                Settings.COLUNAS_SUPERFICIE * CELL_SIZE,
                Settings.LINHAS_SUPERFICIE * CELL_SIZE

        );
        this.manager = manager;
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        // Sempre que o navio se move, redesenha
        manager.addPropertyChangeListener(DeepSeaManager.PROP_NAVIO, evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,  evt -> update());
    }
    private void update() {
        GraphicsContext gc = getGraphicsContext2D();
        draw(gc);
    }
    private void draw(GraphicsContext gc) {
        int rows = Settings.LINHAS_SUPERFICIE;
        int cols = Settings.COLUNAS_SUPERFICIE;
        // 1. Limpar canvas
        gc.clearRect(0, 0, getWidth(), getHeight());
        // 2. Desenhar grelha base (água)
        for (int l = 0; l < rows; l++) {
            for (int c = 0; c < cols; c++) {
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.STEELBLUE);
                gc.strokeRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        // 3. Desenhar pistas de artefactos (quando o Diogo expuser o método)
        int[][] pistas = manager.getMapaPistasArtefactos();
        for (int l = 0; l < rows; l++)
            for (int c = 0; c < cols; c++)
                if (pistas[l][c] > 0) { /* desenhar indicador */ }
        // 4. Desenhar o navio (quando o Diogo expuser os getters de posição)
        int navioL = manager.getLinhaNavioSuperficie();
        int navioC = manager.getColunaNavioSuperficie();
        var navioImg = ImageLoader.getImage("navio.png");
        if (navioImg != null) {
            gc.drawImage(navioImg, navioC * CELL_SIZE, navioL * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else {
            // fallback se não houver imagem ainda
            gc.setFill(Color.DARKRED);
            gc.fillRect(navioC * CELL_SIZE, navioL * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

}
