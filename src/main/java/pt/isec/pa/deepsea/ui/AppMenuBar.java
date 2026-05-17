package pt.isec.pa.deepsea.ui;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import javafx.scene.control.*;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.ui.LogStage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppMenuBar extends MenuBar {
    private final DeepSeaManager manager;
    private final LogStage janelaLogs;
    public AppMenuBar(DeepSeaManager manager) {
        this.manager = manager;
        this.janelaLogs = new LogStage(manager);
        createMenus();
        this.setStyle(
                "-fx-background-color:" + Settings.BG_PANEL + ";"
        );
    }

    Menu mRecent;

    private void createMenus() {
        // JOGO
        Label lblGame = new Label("Game");
        lblGame.setStyle("-fx-text-fill: " + Settings.TEXT_PRIMARY + ";");
        Menu menuGame = new Menu();
        menuGame.setGraphic(lblGame);

        MenuItem mNew = new MenuItem("New");
        mNew.setOnAction(e -> manager.novoJogo());

        MenuItem mOpen = new MenuItem("Open");
        mOpen.setOnAction(e -> {
            File fopen = chooser("Abrir Jogo").showOpenDialog(owner());
            if(fopen == null) return;
            if (manager.carregarJogo(fopen)) {
                registaRecente(fopen);
            } else {
                alerta("Não foi possível carregar o jogo: " + fopen.getName());
            }
        });

        mRecent = new Menu("Open recent");
        reconstruirRecentes();

        MenuItem mSave = new MenuItem("Save as...");
        mSave.setOnAction(e -> {
            File fsave = chooser("Gravar Jogo").showSaveDialog(owner());
            if (fsave == null) return;
            if (manager.gravarJogo(fsave)) {
                registaRecente(fsave);
            } else {
                alerta("Não foi possível gravar o jogo: " + fsave.getName());
            }
        });

        MenuItem mExit = new MenuItem("Exit");
        mExit.setOnAction(e -> Platform.exit());
        menuGame.getItems().addAll(
                mNew,
                mOpen,
                mRecent,
                new SeparatorMenuItem(),
                mSave,
                mExit
        );

        // LOG
        Menu menuLog = new Menu("Log");

        MenuItem mShowHide = new MenuItem("Show/Hide");
        mShowHide.setOnAction(e -> new LogStage(manager).show());

        MenuItem mSaveLogs = new MenuItem("Save Logs");
        mSaveLogs.setOnAction(e -> {
            javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
            fc.setTitle("Guardar Logs");
            fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Ficheiros de Texto", "*.log", "*.txt"));

            java.io.File ficheiro = fc.showSaveDialog(null); // janela separada
            if (ficheiro != null) {
                manager.gravarLog(ficheiro.getAbsolutePath());
            }
        });

        MenuItem mClearLogs = new MenuItem("Clear Logs");
        mClearLogs.setOnAction(e -> manager.limparLog());


        menuLog.getItems().addAll(
                mShowHide,
                mSaveLogs,
                mClearLogs
        );

        // -- Adiciona a menu bar
        getMenus().addAll(menuGame, menuLog);
    }

    private Stage owner() {
        return (Stage) getScene().getWindow();
    }

    private FileChooser chooser(String titulo) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(titulo);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("DeepSea Save (*.sav)", "*.sav"));
        return chooser;
    }

    private void alerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private List<String> lerRecentes() {
        Path p = Path.of(Settings.FICHEIRO_RECENTES);
        try {
            if (Files.exists(p)) {
                return new ArrayList<>(Files.readAllLines(p));
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void registaRecente(File f){
        List<String> recentes = lerRecentes();
        // se o ficheiro tiver na lista e adiciona em primeiro para ficar o mais recente
        recentes.remove(f.getAbsolutePath());
        recentes.add(0, f.getAbsolutePath());
        // remover extras
        while (recentes.size() > Settings.MAX_RECENT){
            recentes.remove(recentes.size() - 1);
        }
        try {
            Files.write(Path.of(Settings.FICHEIRO_RECENTES), recentes);
        } catch (IOException ignored) {}
        reconstruirRecentes();
    }

    private void reconstruirRecentes(){
        mRecent.getItems().clear();
        List<String> recentes = lerRecentes();
        if(recentes.isEmpty()){
            MenuItem vazio = new MenuItem("Sem recentes");
            vazio.setDisable(true);
            mRecent.getItems().add(vazio);
            return;
        }
        for(String recente : recentes){
            MenuItem item = new MenuItem(recente);
            item.setOnAction(e -> {
                File f = new File(recente);
                if(f.exists() && manager.carregarJogo(f)){
                    registaRecente(f);
                }else{
                    alerta("Não foi possível carregar o jogo: " + recente);
                }
            });
            mRecent.getItems().add(item);
        }
    }
}
