package pt.isec.pa.deepsea.ui.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.TipoComponente;
import pt.isec.pa.deepsea.model.state.DeepSeaState;
import pt.isec.pa.deepsea.ui.res.ImageLoader;

public class FossoCanvas extends DeepSeaCanvas {

    public FossoCanvas(DeepSeaManager manager) {
        super(
                manager,
                Settings.LINHAS_FOSSO,
                Settings.COLUNAS_FOSSO
        );

        boolean ativo = manager.getState() == DeepSeaState.DESCIDA_STATE || manager.getState() == DeepSeaState.SUBIDA_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    @Override
    protected void registerHandlers(){
        // Sempre que o drone se move ou o fosso muda (correntes, obstáculos) redesenha o canvas
        manager.addPropertyChangeListener(DeepSeaManager.PROP_FOSSO,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.DESCIDA_STATE
                    || manager.getState() == DeepSeaState.SUBIDA_STATE;
            setVisible(ativo);
            if (ativo) update();
        });
    }

    @Override
    protected void update() {
        GraphicsContext gc = getGraphicsContext2D();
        draw(gc);
    }
    private void draw(GraphicsContext gc){
        int rows = Settings.LINHAS_FOSSO;
        int cols = Settings.COLUNAS_FOSSO;
        //limpar canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        //desenhar a grelha
        for (int l = 0; l < rows; l++) {
            for (int c = 0; c < cols; c++) {
                gc.setFill(Color.web("#40618a"));
                gc.fillRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                int navioL = manager.getLinhaNavioSuperficie();
                int navioC = manager.getColunaNavioSuperficie();
                TipoComponente[][] mapa = manager.getMapaFosso(navioL, navioC);
                if(mapa != null && mapa[l][c] != null){
                    double x = c * CELL_SIZE;
                    double y = l * CELL_SIZE;
                    switch (mapa[l][c]){
                        case ROCHA -> {
                            var img = ImageLoader.getImage("rocha.jpeg");
                            gc.drawImage(img,x,y,CELL_SIZE,CELL_SIZE);

                        }
                        case CORRENTE -> {
                            var img = ImageLoader.getImage("corrente.jpeg");
                            gc.drawImage(img,x,y,CELL_SIZE,CELL_SIZE);
                        }
                        case ANIMALMARINHO -> {
                            var img = ImageLoader.getImage("animal.png");
                            gc.drawImage(img,x,y,CELL_SIZE,CELL_SIZE);
                        }
                        default -> {
                            //vazio, fica so o azul da agua
                        }
                    }
                }
                gc.setStroke(Color.web("#ffffff", 0.30)); //linhas brancas da grelha
                gc.strokeRect(c * CELL_SIZE, l * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        //posicao do drone
        int droneL = manager.getLinhaDroneAtivo();
        int droneC = manager.getColunaDroneAtivo();
        var droneImg = ImageLoader.getImage("drone.png");
        gc.drawImage(droneImg, droneC * CELL_SIZE, droneL * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}
