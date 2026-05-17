package pt.isec.pa.deepsea.ui;

import pt.isec.pa.deepsea.model.DeepSeaManager;
import javafx.scene.control.*;

public class AppMenuBar extends MenuBar {
    private final DeepSeaManager manager;

    public AppMenuBar(DeepSeaManager manager) {
        this.manager = manager;
        createMenus();
    }

    private void createMenus() {
        // JOGO
        Menu menuGame = new Menu("Game");

        MenuItem mNew = new MenuItem("New");
        mNew.setOnAction(e -> manager.novoJogo());

        MenuItem mOpen = new MenuItem("Open");
        mOpen.setOnAction(e -> {/* diogo é cntg */});

        Menu mRecent = new Menu("Open recent");
        //diogo é cntg

        MenuItem mSave = new MenuItem("Save as...");
        mSave.setOnAction(f -> { /*diogo e cntg */});

        MenuItem mExit = new MenuItem("Exit");

        menuGame.getItems().addAll(
                mNew,
                mOpen,
                mRecent,
                new SeparatorMenuItem(),
                new SeparatorMenuItem(),
                mSave,
                mExit
        );

        // LOG
        Menu menuLog = new Menu("Log");

        MenuItem mShowHide = new MenuItem("Show/Hide");
        mShowHide.setOnAction(e -> new LogStage(manager).show());

        MenuItem mSaveLogs = new MenuItem("Save Logs");
        mSaveLogs.setOnAction(e -> {/*foda se*/});

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
}
