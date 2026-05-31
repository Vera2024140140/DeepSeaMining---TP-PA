package pt.isec.pa.deepsea.ui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.TipoComponente;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.ui.res.ImageLoader;

public class FundoCanvas extends DeepSeaCanvas {

    public FundoCanvas(DeepSeaManager manager) {
        super(
                manager,
                Settings.LINHAS_FUNDO ,
                Settings.COLUNAS_FUNDO
        );
        boolean ativo = manager.getState() == DeepSeaState.FUNDO_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    @Override
    protected void registerHandlers() {
        // Sempre que o drone se move ou o fosso muda (correntes, obstáculos) redesenha o canvas
        manager.addPropertyChangeListener(DeepSeaManager.PROP_FUNDO,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.FUNDO_STATE;
            setVisible(ativo);
            if (ativo) update();
        });
    }

    @Override
    protected void update() {
        GraphicsContext gc = getGraphicsContext2D();
        draw(gc);
    }

    private void draw(GraphicsContext gc) {
        int rows = Settings.LINHAS_FUNDO;
        int cols = Settings.COLUNAS_FUNDO;
        //limpar canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        int navioL = manager.getLinhaNavioSuperficie();
        int navioC = manager.getColunaNavioSuperficie();

        //desenhar a grelha
        for (int l = 0; l < rows; l++) {
            for (int c = 0; c < cols; c++) {
                if (manager.isCelulaFundoRevelada(navioL, navioC, l, c)) {
                    gc.setFill(Color.web("#7ab0d6"));
                    gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    TipoComponente[][] mapa = manager.getMapaFundo(navioL, navioC);
                    if (mapa != null  && mapa[l][c] != null) {
                        double x = c * CELL_SIZE;
                        double y = l * CELL_SIZE;
                        switch (mapa[l][c]) {
                            case MONSTRO -> {
                                var img = ImageLoader.getImage("monstro.png");
                                gc.drawImage(img, x, y, CELL_SIZE, CELL_SIZE);

                            }
                            case MINERIO -> {
                                var img = ImageLoader.getImage("minerio.png");
                                gc.drawImage(img, x, y, CELL_SIZE, CELL_SIZE);
                            }
                            case ARTEFACTO -> {
                                var img = ImageLoader.getImage("artefacto.png");
                                gc.drawImage(img, x, y, CELL_SIZE, CELL_SIZE);
                            }
                            default -> {
                                //vazio
                            }
                        }
                    }
                } else {
                    gc.setFill(Color.web("#0a111f"));
                    gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                }
                gc.setStroke(Color.web("#ffffff", 0.30)); //linhas brancas da grelha
                gc.strokeRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        int droneL = manager.getLinhaDroneAtivo();
        int droneC = manager.getColunaDroneAtivo();
        if (droneL >= 0 && droneC >= 0) {
            var droneImg = ImageLoader.getImage("drone.png");
            gc.drawImage(droneImg, droneC * CELL_SIZE, droneL * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}
