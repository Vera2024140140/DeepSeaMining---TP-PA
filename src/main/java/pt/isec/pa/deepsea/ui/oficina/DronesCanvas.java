package pt.isec.pa.deepsea.ui.oficina;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.ui.res.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class DronesCanvas extends Canvas {
    private static final int    CELULAS = Settings.NUM_DRONES_INICIAIS;
    private static final double LARGURA = (double) CELULAS * Settings.CANVAS_DRONE_W;
    private static final double ALTURA  = Settings.CANVAS_DRONE_H;

    DeepSeaManager manager;
    Image imgDrone;
    ImageLoader imageLoader = new ImageLoader();

    public DronesCanvas(DeepSeaManager manager) {
        super(LARGURA, ALTURA);
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        imgDrone = imageLoader.getImage("drone.png");
    }

    private void registerHandlers() {
        setOnMouseClicked(ev -> {
            int idx = (int) (ev.getX() / (LARGURA / CELULAS));
            List<Integer> ids = idsOrdenados();
            if (idx >= 0 && idx < ids.size()) {
                manager.selecionarDrone(ids.get(idx));
            }
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE, ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,  ev -> update());
    }

    private void update() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, LARGURA, ALTURA);

        List<Integer> ids = idsOrdenados();
        int idSelecionado = manager.getIdDroneAtivo();
        double celulaW = LARGURA / CELULAS;
        double margem  = 15;

        for (int i = 0; i < CELULAS; i++) {
            double x = i * celulaW;
            boolean selecionado = (i < ids.size() && ids.get(i) == idSelecionado);

            gc.setFill(selecionado ? Color.web("#dfeaf5") : Color.web("#ececec"));
            gc.fillRoundRect(x + 5, 5, celulaW - 10, ALTURA - 10, 12, 12);

            gc.setStroke(selecionado ? Color.web("#2980b9") : Color.web("#7f8c8d"));
            gc.setLineWidth(selecionado ? 4 : 2);
            gc.strokeRoundRect(x + 5, 5, celulaW - 10, ALTURA - 10, 12, 12);

            if (i >= ids.size()) continue;
            int id = ids.get(i);

            gc.setEffect(selecionado ? null : new ColorAdjust(0, -1, -0.2, 0));
            gc.drawImage(imgDrone, x + margem, margem + 10,
                         celulaW - 2 * margem, ALTURA - 2 * margem - 30);
            gc.setEffect(null);

            gc.setFill(selecionado ? Color.web("#2980b9") : Color.web("#7f8c8d"));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Drone " + id, x + celulaW / 2, ALTURA - 12);
        }
    }

    private List<Integer> idsOrdenados() {
        List<Integer> ids = new ArrayList<>(manager.getIdsDronesNavio());
        ids.sort(Integer::compareTo);
        return ids;
    }
}
