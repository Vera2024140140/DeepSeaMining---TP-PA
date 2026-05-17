package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

public class BarraLateralFundo extends BarraLateralNavegacao{
    Button btnApanharMinerio;
    Button btnIniciarSubida;
    public BarraLateralFundo(DeepSeaManager manager){
        super(manager);
    }
    @Override
    void registerHandlers() {
        btnApanharMinerio.setOnAction(evento -> {
            manager.recolherMinerio();
        });
        btnIniciarSubida.setOnAction(evento -> {
            manager.iniciarSubida();
        });
        // Verifica se o drone calhou numa posicao com minerio
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE,evento->{
            update();
        });

    }
    @Override
    void createViews() {
        super.createViews();
        btnApanharMinerio = new Button("Recolher Minério");
        btnApanharMinerio.setMaxWidth(Double.MAX_VALUE);
        btnApanharMinerio.setStyle(
                "-fx-background-color:#D4B872;" +
                        "-fx-font-family:" + Settings.FONTE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 12 8 12;" +
                        "-fx-cursor: hand;"
        );
        VBox.setMargin(btnApanharMinerio, new Insets(20, 0, 0, 0));


        btnApanharMinerio.setVisible(false);
        btnApanharMinerio.setManaged(false);

        btnIniciarSubida = new Button("Iniciar Subida");

        btnIniciarSubida.setMaxWidth(Double.MAX_VALUE);
        btnIniciarSubida.setOnAction(e -> manager.iniciarDescida());
        btnIniciarSubida.setPadding(new Insets(5, 25, 5, 25));
        btnIniciarSubida.setStyle(
                "-fx-background-color:" + Settings.BTN_PRIMARY + ";" +
                        "-fx-font-family:" + Settings.FONTE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 12 8 12;" +
                        "-fx-cursor: hand;"
        );
        btnIniciarSubida.setMinWidth(140);
        btnIniciarSubida.setMinHeight(16);

        VBox.setMargin(btnIniciarSubida, new Insets(10, 0, 0, 0));

        // Adiciona ao final da VBox
        areaMeio.getChildren().addAll(
                btnIniciarSubida,
                btnApanharMinerio

        );

    }
    @Override
    void update() {
        super.update(); // atualiza combustivel e minérios chamando o atualizar da Base

        if (btnApanharMinerio == null) return;
        // Verifica se está em cima do minério, se estiver, fica visível
        boolean temMinerio = manager.posicaoComMinerio();
        btnApanharMinerio.setVisible(temMinerio);
        btnApanharMinerio.setManaged(temMinerio);


    }
}
