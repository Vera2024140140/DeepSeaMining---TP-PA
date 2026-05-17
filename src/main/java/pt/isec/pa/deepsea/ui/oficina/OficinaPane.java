package pt.isec.pa.deepsea.ui.oficina;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.deepsea.model.DeepSeaManager;

public class OficinaPane extends BorderPane {
    DeepSeaManager manager;

    DronesCanvas drones;
    OficinaInfoBox infoBox;
    OficinaActionsBox actionsBox;
    Button btnFechar;

    public OficinaPane(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setStyle("-fx-background-color: #d9d9d9;");

        Label titulo = new Label("OFICINA");
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        VBox top = new VBox(titulo);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 20, 10, 20));
        setTop(top);

        drones = new DronesCanvas(manager);
        HBox dronesWrapper = new HBox(drones);
        dronesWrapper.setAlignment(Pos.CENTER);
        dronesWrapper.setPadding(new Insets(5, 20, 15, 20));

        infoBox    = new OficinaInfoBox(manager);
        actionsBox = new OficinaActionsBox(manager);

        HBox meio = new HBox(40, infoBox, actionsBox);
        meio.setAlignment(Pos.CENTER);
        meio.setPadding(new Insets(10, 30, 10, 30));

        VBox centro = new VBox(10, dronesWrapper, meio);
        centro.setAlignment(Pos.TOP_CENTER);
        setCenter(centro);

        btnFechar = new Button("Fechar Oficina");
        btnFechar.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
        btnFechar.setStyle("-fx-background-color: #bdc3c7; -fx-text-fill: #2c3e50;");
        btnFechar.setPrefHeight(35);
        HBox baixo = new HBox(btnFechar);
        baixo.setPadding(new Insets(10, 20, 15, 20));
        setBottom(baixo);
    }

    private void registerHandlers() {
        btnFechar.setOnAction(e -> manager.fecharOficina());
    }

    private void update() { }
}
