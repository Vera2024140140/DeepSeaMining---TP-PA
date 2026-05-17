package pt.isec.pa.deepsea.ui.oficina;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

public class OficinaInfoBox extends VBox {
    DeepSeaManager manager;

    Label lblCombNavio;
    Label lblMineriosNavio;
    VBox  boxDrones;

    public OficinaInfoBox(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setSpacing(8);
        setPadding(new Insets(18));

        setMinWidth((double) Settings.NUM_DRONES_INICIAIS * Settings.CANVAS_DRONE_W / 2.0);
        setStyle(
            "-fx-background-color: #ecf0f1;" +
            "-fx-border-color: #34495e;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label titulo = new Label("Informação");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titulo.setMaxWidth(Double.MAX_VALUE);
        titulo.setAlignment(Pos.CENTER);
        titulo.setStyle("-fx-text-fill: #2c3e50;");

        Label tituloNavio = new Label("Navio");
        tituloNavio.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        tituloNavio.setStyle("-fx-text-fill: #2c3e50;");

        Label tituloDrones = new Label("Drones");
        tituloDrones.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        tituloDrones.setStyle("-fx-text-fill: #2c3e50;");

        lblCombNavio     = textoInfo();
        lblMineriosNavio = textoInfo();
        boxDrones = new VBox(4);

        VBox espaco1 = new VBox(); espaco1.setMinHeight(8);
        VBox espaco2 = new VBox(); espaco2.setMinHeight(8);

        getChildren().addAll(
            titulo,
            espaco1,
            tituloNavio,
            lblCombNavio,
            lblMineriosNavio,
            espaco2,
            tituloDrones,
            boxDrones
        );
    }

    private Label textoInfo() {
        Label l = new Label();
        l.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        l.setStyle("-fx-text-fill: #34495e;");
        return l;
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_NAVIO,   ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE,   ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_OFICINA, ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,    ev -> update());
    }

    private void update() {
        lblCombNavio.setText("Combustível: " + manager.getCombustivelNavio() + " L");
        lblMineriosNavio.setText("Minérios: " + manager.getMineriosNavio());

        boxDrones.getChildren().clear();
        for (String linha : manager.getInfoDrones()) {
            Label l = textoInfo();
            l.setText(linha);
            boxDrones.getChildren().add(l);
        }
    }
}
