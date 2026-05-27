package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.ui.res.ImageLoader;

public class SuperficieCanvas extends Canvas {
    private final DeepSeaManager manager;
    int CELL_SIZE = Settings.CELL_SIZE;

    public SuperficieCanvas(DeepSeaManager manager) {
        super(
                Settings.COLUNAS_SUPERFICIE * Settings.CELL_SIZE,
                Settings.LINHAS_SUPERFICIE * Settings.CELL_SIZE
        );
        this.manager = manager;
        registarHandlers();
        // visibilidade inicial definida
        boolean ativo = manager.getState() == DeepSeaState.SUPERFICIE_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    private void registarHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_NAVIO, evt ->{
            if (isVisible()) update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME, evt -> {
            if (isVisible()) update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt ->{
            boolean ativo = manager.getState() == DeepSeaState.SUPERFICIE_STATE;
            setVisible(ativo);
            if (ativo) update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_LOG,evt -> {
            if (isVisible()) update();
        });
    }

    private void update() {
        GraphicsContext gc = getGraphicsContext2D();
        interface2D(gc);
    }

    private void interface2D(GraphicsContext gc) {
        int linhas = Settings.LINHAS_SUPERFICIE;
        int colunas = Settings.COLUNAS_SUPERFICIE;

        //limpar canvas
        gc.clearRect(0, 0, getWidth(), getHeight());
        //desenhar a grelha
        for(int l = 0; l < linhas; l++) {
            for(int c = 0; c < colunas; c++) {
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.STEELBLUE);
                gc.strokeRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        // --- PISTAS ---
        // 3 niveis: 1=baixa, 2=media, >=3=alta probabilidade de artefactos no fundo
        int[][]  pistas = manager.getMapaPistasArtefactos();
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                int n = pistas[l][c];
                if (n <= 0) continue;
                Color cor;
                if (n == 1) {
                    cor = Color.web("#FFF2A8"); // baixa
                } else if (n == 2) {
                    cor = Color.web("#FFB347"); // media
                } else {
                    cor = Color.web("#E74C3C"); // alta
                }
                gc.setFill(cor);
                gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        // --- NAVIO ---
        int lNavio = manager.getLinhaNavioSuperficie();
        int cNavio = manager.getColunaNavioSuperficie();
        var imgNavio = ImageLoader.getImage("navio.png");
        if (imgNavio != null) {
            gc.drawImage(imgNavio, cNavio * CELL_SIZE, lNavio * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else {
            //se a img falhar
            gc.setFill(Color.DARKRED);
            gc.fillRect(cNavio * CELL_SIZE, lNavio * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}
