package pt.isec.pa.deepsea.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import pt.isec.pa.deepsea.ui.res.ImageLoader;
import pt.isec.pa.deepsea.ui.res.SoundManager;

public class BotaoSom extends Button {
    private static final double TAMANHO = 22;

    private final Line barraMudo;

    public BotaoSom() {
        setFocusTraversable(false);
        setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 2 8 2 8;");
        setTooltip(new Tooltip());

        ImageView icone = new ImageView(ImageLoader.getImage("volume.png"));
        icone.setFitWidth(TAMANHO);
        icone.setFitHeight(TAMANHO);
        icone.setPreserveRatio(true);
        // passar imagem de preto para branco
        ColorAdjust branco = new ColorAdjust();
        branco.setBrightness(1.0);
        icone.setEffect(branco);

        // barra diagonal sobreposta quando o som está desligado
        barraMudo = new Line(2, 2, TAMANHO - 2, TAMANHO - 2);
        barraMudo.setStroke(Color.web("#E74C3C"));
        barraMudo.setStrokeWidth(2.5);
        barraMudo.setStrokeLineCap(StrokeLineCap.ROUND);

        setGraphic(new StackPane(icone, barraMudo));

        setOnAction(e -> SoundManager.setSomLigado(!SoundManager.isSomLigado()));
        SoundManager.somLigadoProperty().addListener((obs, oldV, novo) -> atualizar(novo));
        atualizar(SoundManager.isSomLigado());
    }

    private void atualizar(boolean ligado) {
        barraMudo.setVisible(!ligado);
        getTooltip().setText(ligado ? "Desligar som" : "Ligar som");
    }
}
