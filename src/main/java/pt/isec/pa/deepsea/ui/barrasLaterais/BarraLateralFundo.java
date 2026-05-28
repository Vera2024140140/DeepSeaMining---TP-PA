package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

/**
 * Barra lateral exibida quando o ‘drone’ está no fundo, marinho.
 * Adiciona botões de recolha de minério/artefacto e de iniciar subida,
 * que aparecem dinamicamente conforme a posição do ‘drone’.
 *
 * @author Rafael2024143044
 * @author Vera2024140140
 * @author Diogo2024152576
 */
public class BarraLateralFundo extends BarraLateralNavegacao{
    Button btnApanharMinerio;
    Button btnApanharArtefacto;
    Button btnIniciarSubida;

    public BarraLateralFundo(DeepSeaManager manager){
        super(manager);
        boolean ativo = manager.getState() == DeepSeaState.FUNDO_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    /** Regista as ações dos botões e o listener do drone. */
    @Override
    void registerHandlers() {
        btnApanharMinerio.setOnAction(evento -> {
            manager.recolherMinerio();
        });
        btnApanharArtefacto.setOnAction(evento -> {
            manager.recolherArtefacto();
        });
        btnIniciarSubida.setOnAction(evento -> {
            manager.iniciarSubida();
        });
        // Verifica se o drone calhou numa posicao com minerio
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE,evento->{
            update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.FUNDO_STATE;
            setVisible(ativo);
            setManaged(ativo);
            if (ativo) update();
        });

    }

    /** Cria os botões de recolha e subida. */
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

        btnApanharArtefacto = new Button("Recolher Artefacto");
        btnApanharArtefacto.setMaxWidth(Double.MAX_VALUE);
        btnApanharArtefacto.setStyle(
            "-fx-background-color:#D4B872;" +
            "-fx-font-family:" + Settings.FONTE + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-cursor: hand;"
        );
        VBox.setMargin(btnApanharArtefacto, new Insets(20, 0, 0, 0));

        btnApanharArtefacto.setVisible(false);
        btnApanharArtefacto.setManaged(false);

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
            btnApanharMinerio,
            btnApanharArtefacto
        );

    }

    /** Atualiza a visibilidade dos botões conforme o conteúdo da célula atual. */
    @Override
    void update() {
        super.update(); // atualiza combustivel e minérios chamando o atualizar da Base

        if (btnApanharMinerio == null) return;
        // Verifica se está em cima do minério, se estiver, fica visível
        boolean temMinerio = manager.posicaoComMinerio();
        btnApanharMinerio.setVisible(temMinerio);
        btnApanharMinerio.setManaged(temMinerio);

        if (btnApanharArtefacto == null) return;

        boolean temArtefacto = manager.posicaoComArtefacto();
        btnApanharArtefacto.setVisible(temArtefacto);
        btnApanharArtefacto.setManaged(temArtefacto);
    }
}
