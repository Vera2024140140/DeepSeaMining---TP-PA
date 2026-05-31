package pt.isec.pa.deepsea.ui;

import javafx.application.Platform;
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
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;


public class AcabouPane extends BorderPane {

    private final DeepSeaManager manager;

    private Label lblTitulo;
    private Label lblArtefactos;
    private Button btnJogarNovo;
    private Button btnSair;

    public AcabouPane(DeepSeaManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        boolean ativo = manager.getState() == DeepSeaState.ACABOU_STATE;
        setVisible(ativo);
        if (ativo) update();
    }

    private void createViews() {
        setPrefSize(Settings.WIDTH_JANELA, Settings.HEIGHT_JANELA);
        setStyle("-fx-background-color: " + Settings.BG_FUNDO + ";");

        lblTitulo = new Label("GAME OVER");
        lblTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 72));
        lblTitulo.setStyle(
            "-fx-text-fill: " + Settings.TEXT_PRIMARY + ";" +
            "-fx-background-color: " + Settings.BG_PANEL + ";" +
            "-fx-padding: 20 40 20 40;" +
            "-fx-background-radius: 14;" +
            "-fx-border-radius: 14;"
        );

        lblArtefactos = new Label();
        lblArtefactos.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        lblArtefactos.setAlignment(Pos.CENTER);
        lblArtefactos.setStyle(
            "-fx-text-fill: " + Settings.TEXT_DARK + ";" +
            "-fx-background-color: " + Settings.TEXT_PRIMARY + ";" +
            "-fx-padding: 25 25 25 25;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;"
        );

        btnJogarNovo = botao("Jogar de Novo", Settings.BTN_PRIMARY);
        btnSair = botao("Sair", Settings.BAR_CRIT);

        HBox botoes = new HBox(40, btnJogarNovo, btnSair);
        botoes.setAlignment(Pos.CENTER);

        VBox conteudo = new VBox(50, lblTitulo, lblArtefactos, botoes);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setPadding(new Insets(60));

        setCenter(conteudo);
    }

    private Button botao(String texto, String corFundo) {
        Button b = new Button(texto);
        b.setPrefSize(220, 60);
        b.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        b.setStyle(
            "-fx-background-color: " + corFundo + ";" +
            "-fx-text-fill: " + Settings.TEXT_PRIMARY + ";" +
            "-fx-background-radius: 8;"
        );
        return b;
    }

    private void registerHandlers() {
        btnJogarNovo.setOnAction(e -> manager.novoJogo());
        btnSair.setOnAction(e -> Platform.exit());

        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,  ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_NAVIO, ev -> update());
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.ACABOU_STATE;
            setVisible(ativo);
            if (ativo) update();
        });
    }

    private void update() {
        lblArtefactos.setText(
            "Apanhou " + manager.getArtefactosNavio() + " / " + Settings.NUM_ARTEFACTOS + " artefactos"
        );
    }
}
