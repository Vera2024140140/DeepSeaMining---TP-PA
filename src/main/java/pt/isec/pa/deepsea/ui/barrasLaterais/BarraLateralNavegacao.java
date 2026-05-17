package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

public abstract class BarraLateralNavegacao extends BarraLateralBase{
    protected Label combustivelNavio;
    protected  Label qtdMineriosNavio;
    protected  Label qtdArtefactosNavio;
    protected  Label droneSelecionado;
    protected  Label combustivelDroneSelecionado;
    protected  Label integridadeDroneSelecionado;
    protected  Label qtdMineriosDrone;
    protected  Label qtdArtefactosDrone;

    protected  Label combustivelNavioQuantidades;
    protected  Label combustivelDroneQuantidades;
    protected  Label integridadeDroneQuantidades;
    public BarraLateralNavegacao(DeepSeaManager manager){
        super(manager);
    }
    @Override
    void registerHandlers() {
        //atualizar os valores
        manager.addPropertyChangeListener(DeepSeaManager.PROP_NAVIO, evento->{
            update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_DRONE, evento -> {
            update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GAME,  evento -> {
            update();
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE,
                evento -> update()
        );
    }
    @Override
    void createViews() {
        super.createViews();

        //estilo para as labels
        String estiloLabels =
                "-fx-text-fill:" + Settings.TEXT_PRIMARY + ";" +
                        "-fx-padding: 5 0 5 0;"
                ;


        // --- NAVIO ---
        Label tituloNavio = new Label("Navio");
        tituloNavio.setMaxWidth(Double.MAX_VALUE);
        tituloNavio.setStyle(
                "-fx-font-family:" + Settings.FONTE + ";" +
                        "-fx-font-weigth: bold; " +
                        "-fx-font-size: 22px; " +
                        "-fx-alignment: center;" +
                        "-fx-padding: 10 0 10 0;" +
                        "-fx-text-fill:" + Settings.TEXT_PRIMARY + ";"
        );

        tituloNavio.setAlignment(Pos.CENTER);
        combustivelNavio = new Label("Combustível");
        combustivelNavio.setStyle(estiloLabels);

        combustivelNavioQuantidades = new Label("x/x");
        combustivelNavioQuantidades.setStyle(estiloLabels);

        qtdMineriosNavio = new Label("Minérios: --");
        qtdMineriosNavio.setStyle(estiloLabels);

        qtdArtefactosNavio = new Label("Artefactos: --");
        qtdArtefactosNavio.setStyle(estiloLabels);

        droneSelecionado = new Label("Drone ativo: --");
        droneSelecionado.setStyle(estiloLabels);


        // --- DRONE ---
        Label tituloDrone = new Label("DRONE");
        tituloDrone.setMaxWidth(Double.MAX_VALUE);
        tituloDrone.setStyle(
                "-fx-font-family:" + Settings.FONTE + ";" +
                        "-fx-font-weigth: bold; " +
                        "-fx-font-size: 22px; " +
                        "-fx-alignment: center;" +
                        "-fx-padding: 10 0 10 0;" +
                        "-fx-text-fill:" + Settings.TEXT_PRIMARY + ";"
        );
        tituloDrone.setAlignment(Pos.CENTER);

        combustivelDroneSelecionado =  new Label("Combustível");
        combustivelDroneSelecionado.setStyle(estiloLabels);

        combustivelDroneQuantidades = new Label("x/x");
        combustivelDroneQuantidades.setStyle(estiloLabels);

        integridadeDroneSelecionado = new Label("Integridade");
        integridadeDroneSelecionado.setStyle(estiloLabels);

        integridadeDroneQuantidades = new Label("x/x");
        integridadeDroneQuantidades.setStyle(estiloLabels);

        qtdMineriosDrone = new Label("Minérios: --");
        qtdMineriosDrone.setStyle(estiloLabels);

        qtdArtefactosDrone = new Label("Artefactos: --");
        qtdArtefactosDrone.setStyle(estiloLabels);

        // HBOX -- combustivel do navio


        HBox rowCombustivelNavio = new HBox(5,
                combustivelNavio,
                combustivelNavioQuantidades
        );
        rowCombustivelNavio.setAlignment(Pos.CENTER_LEFT);
        rowCombustivelNavio.setMaxWidth(Double.MAX_VALUE);

        // HBOX -- combustivel do drone --

        HBox rowCombustivelDrone = new HBox(5,
                combustivelDroneSelecionado,
                combustivelDroneQuantidades
        );
        rowCombustivelDrone.setAlignment(Pos.CENTER_LEFT);
        rowCombustivelDrone.setMaxWidth(Double.MAX_VALUE);
        rowCombustivelDrone.setStyle(
                "-fx-padding: 0 0 10 0;"
        );

        // HBOX -- integridade do drone

        HBox rowIntegridadeDrone = new HBox(5,
                integridadeDroneSelecionado,
                integridadeDroneQuantidades
        );
        rowIntegridadeDrone.setAlignment(Pos.CENTER_LEFT);
        rowIntegridadeDrone.setMaxWidth(Double.MAX_VALUE);
        rowIntegridadeDrone.setStyle(
                "-fx-padding: 0 0 10 0;"
        );

        //adicionar tudo à VBox
        areaMeio.getChildren().addAll(
                tituloNavio,
                rowCombustivelNavio,
                qtdMineriosNavio,
                qtdArtefactosNavio,
                droneSelecionado,
                tituloDrone,
                rowCombustivelDrone,
                rowIntegridadeDrone,
                qtdMineriosDrone,
                qtdArtefactosDrone
        );
    }
    @Override
    void update(){
        double larguraLabelEsq = 80;
        double larguraLabelDir = 70;

        combustivelNavio.setText("Combustível");
        combustivelNavioQuantidades.setText((int)manager.getCombustivelNavio() + "/" + (int) Settings.NAVIO_COMBUSTIVEL_MAX);
        qtdMineriosNavio.setText("Minérios: " + manager.getMineriosNavio());
        qtdArtefactosNavio.setText("Artefactos: " + manager.getArtefactosNavio());
        droneSelecionado.setText("Drone ativo: " + manager.getIdDroneAtivo());
        combustivelDroneSelecionado.setText("Combustível");
        combustivelDroneQuantidades.setText( (int) manager.getCombustivelDroneAtivo() + "/" + (int) manager.getMaxCombustivelDroneAtivo());
        integridadeDroneSelecionado.setText("Integridade");
        integridadeDroneQuantidades.setText(manager.getIntegridadeDroneAtivo()+ "/" + manager.getIntegridadeMaxDrone());
        qtdMineriosDrone.setText("Minérios Drone Ativo: " + manager.getMineriosDroneAtivo());
        qtdArtefactosDrone.setText("Artefactos Drone Ativo: " + manager.getArtefactosDroneAtivo());

        combustivelNavio.setPrefWidth(larguraLabelEsq);
        combustivelDroneSelecionado.setPrefWidth(larguraLabelEsq);
        integridadeDroneSelecionado.setPrefWidth(larguraLabelEsq);

        combustivelNavioQuantidades.setPrefWidth(larguraLabelDir);
        combustivelDroneQuantidades.setPrefWidth(larguraLabelDir);
        integridadeDroneQuantidades.setPrefWidth(larguraLabelDir);

    }
}
