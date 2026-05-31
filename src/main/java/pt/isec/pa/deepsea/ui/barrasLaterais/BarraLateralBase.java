package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import javafx.scene.layout.VBox;
import pt.isec.pa.deepsea.model.data.Settings;
import javafx.geometry.Insets;
import pt.isec.pa.deepsea.ui.canvas.DeepSeaCanvas;

public abstract class BarraLateralBase extends VBox {
    DeepSeaManager manager;
    Label titulo;
    Label subtitulo;
    Label autores;
    Label nomes;
    VBox areaMeio;

    public BarraLateralBase(DeepSeaManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }
    abstract void registerHandlers();

    void createViews() {
        this.setMaxHeight(Double.MAX_VALUE);
        this.setPrefWidth(Settings.WIDTH_BARRA_LATERAL);
        this.setMinWidth(Settings.WIDTH_BARRA_LATERAL);
        this.setMaxWidth(Settings.WIDTH_BARRA_LATERAL);
        this.setStyle(
                "-fx-background-color: " + Settings.BG_PANEL + ";" +
                        " -fx-border-color: #555;" +
                        " -fx-border-width: 0 0 0 2;"
        );
        this.setPadding(new Insets(15));
        this.setSpacing(10);

        titulo = new Label("D E E P  S E A  M I N I N G");
        titulo.setStyle(
                "-fx-text-fill: #D4B872; " +
                        "-fx-font-weight: bold;" +
                        " -fx-font-size: 23px;" +
                        "-fx-padding: 0 0 10 0;" +
                        "-fx-font-family:" + Settings.FONTE
        );
        titulo.setWrapText(true);
        titulo.setAlignment(Pos.CENTER);

        subtitulo = new Label("P R O G R A M A Ç Ã O   A V A N Ç A D A   2 5 / 2 6\n\nD E I S  -  I S E C\n");
        subtitulo.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-text-alignment: center;" +
                        "-fx-font-family:" + Settings.FONTE + ";"

        );
        subtitulo.setAlignment(Pos.CENTER);

        autores = new Label("A U T O R E S");
        autores.setStyle(
                "-fx-text-fill: #D4B872; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 20px; " +
                        "-fx-padding: 15 0 10 0;" +
                        "-fx-font-family:" + Settings.FONTE + ";"
        );

        nomes = new Label("DIOGO  PINTO | RAFAEL  MARQUES | VERA  RIBEIRO ");
        nomes.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-font-size: 10px;" +
                        "-fx-font-family:" + Settings.FONTE + ";"
        );
        nomes.setWrapText(true);
        nomes.setAlignment(Pos.CENTER);

        VBox cabecalho = new VBox(titulo,subtitulo,autores,nomes);
        cabecalho.setAlignment(Pos.CENTER);

        //Area do meio diferente em cada barra
        areaMeio = new VBox();
        VBox.setVgrow(areaMeio, Priority.ALWAYS);
        VBox.setMargin(areaMeio, new Insets(20, 0, 0, 0));

        this.getChildren().addAll(cabecalho, areaMeio);
    }
    abstract void update();
}
