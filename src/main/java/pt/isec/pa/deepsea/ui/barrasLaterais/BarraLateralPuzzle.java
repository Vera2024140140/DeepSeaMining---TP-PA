package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

import static pt.isec.pa.deepsea.Main.manager;

public class BarraLateralPuzzle extends BarraLateralBase{
    private Label lblMovimentosTitulo;
    private Label lblMovimentos;

    public BarraLateralPuzzle(DeepSeaManager manager) {
        super(manager);
    }

    @Override
    void createViews() {
        super.createViews();

        lblMovimentosTitulo = new Label("MOVIMENTOS RESTANTES:");
        lblMovimentosTitulo.setStyle(
                "-fx-text-fill: " + Settings.TEXT_PRIMARY + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );

        lblMovimentos = new Label("--");
        lblMovimentos.setStyle(
                "-fx-text-fill: " + Settings.TEXT_PRIMARY + ";" +
                        "-fx-font-size: 18px;"
        );

        VBox conteudo = new VBox(8, lblMovimentosTitulo, lblMovimentos);
        conteudo.setAlignment(Pos.CENTER);

        areaMeio.setAlignment(Pos.TOP_CENTER);
        areaMeio.setPadding(new Insets(80, 0, 0, 0));
        areaMeio.getChildren().add(conteudo);
    }

    @Override
    void registerHandlers() {
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PUZZLE,
                evt -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,evt -> {
            DeepSeaState anterior = (DeepSeaState) evt.getOldValue();
            DeepSeaState atual = (DeepSeaState) evt.getNewValue();
            if (anterior == DeepSeaState.PUZZLE_STATE && atual != DeepSeaState.PUZZLE_STATE) {
                update();
            }
        }
        );
    }

    @Override
    public void update() {
        if (lblMovimentos != null) {
            lblMovimentos.setText(
                    manager.getMovimentosRestantesPuzzle() + " / " + Settings.PUZZLE_MAX_MOVIMENTOS
            );
        }
    }
}
