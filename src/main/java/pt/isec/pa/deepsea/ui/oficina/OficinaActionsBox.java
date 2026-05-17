package pt.isec.pa.deepsea.ui.oficina;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

import java.util.Optional;

public class OficinaActionsBox extends GridPane {
    private static final int BTN_W = 170;
    private static final int BTN_H = 65;

    DeepSeaManager manager;

    Button btnAbastecer, btnReparar, btnTanque, btnIntegridade;

    public OficinaActionsBox(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setHgap(15);
        setVgap(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        setStyle(
            "-fx-background-color: #ecf0f1;" +
            "-fx-border-color: #34495e;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        btnAbastecer = new Button("Abastecer");
        btnAbastecer.setPrefSize(BTN_W, BTN_H);
        btnReparar = new Button("Reparar");
        btnReparar.setPrefSize(BTN_W, BTN_H);
        btnTanque = new Button("Melhorar Tanque");
        btnTanque.setPrefSize(BTN_W, BTN_H);
        btnIntegridade = new Button("Melhorar Integridade");
        btnIntegridade.setPrefSize(BTN_W, BTN_H);

        add(btnAbastecer,   0, 0);
        add(btnReparar,     1, 0);
        add(btnTanque,      0, 1);
        add(btnIntegridade, 1, 1);
    }

    private Button botao(String texto) {
        Button b = new Button(texto);
        b.setPrefSize(BTN_W, BTN_H);
        b.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        b.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;"
        );
        return b;
    }

    private void registerHandlers() {
        btnAbastecer.setOnAction(e   -> abrirAbastecer());
        btnReparar.setOnAction(e     -> abrirReparar());
        btnTanque.setOnAction(e      -> abrirMelhorarTanque());
        btnIntegridade.setOnAction(e -> abrirMelhorarIntegridade());
    }

    private void update() { }

    private void abrirAbastecer() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle("Abastecer Drone");
        dlg.setHeaderText("Quantos litros quer abastecer?");
        dlg.setContentText("Litros:");
        Optional<String> r = dlg.showAndWait();
        if (r.isPresent()) {
            double litros = Double.parseDouble(r.get().replace(',', '.'));
            if (!manager.abastecerDrone(litros)) erro("Não foi possível abastecer.");
        }
    }

    private void abrirReparar() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle("Reparar Drone");
        dlg.setHeaderText("Quantos pontos de integridade quer reparar?");
        dlg.setContentText("Pontos:");
        Optional<String> r = dlg.showAndWait();
        if (r.isPresent()) {
            int pontos = Integer.parseInt(r.get().trim());
            if (!manager.repararDrone(pontos)) erro("Não foi possível reparar.");
        }
    }

    private void abrirMelhorarTanque() {
        if (confirmar("Melhorar Tanque",
                "Custa " + Settings.CUSTO_MINERIOS_MELHORAR_TANQUE +
                " minérios.\nAumenta o tanque em " + Settings.INCREMENTO_COMBUSTIVEL_MAX + " L.")) {
            if (!manager.melhorarTanqueDrone()) erro("Não foi possível melhorar o tanque.");
        }
    }

    private void abrirMelhorarIntegridade() {
        if (confirmar("Melhorar Integridade",
                "Custa " + Settings.CUSTO_MINERIOS_MELHORAR_INTEGRIDADE +
                " minérios.\nAumenta a integridade máxima em " + Settings.INCREMENTO_INTEGRIDADE_MAXIMA + ".")) {
            if (!manager.melhorarIntegridadeDrone()) erro("Não foi possível melhorar a integridade.");
        }
    }

    private boolean confirmar(String titulo, String texto) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(texto);
        return a.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }

    private void erro(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Oficina");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
