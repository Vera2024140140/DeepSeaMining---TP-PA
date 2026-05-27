package pt.isec.pa.deepsea.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.DeepSeaApp;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import javafx.scene.input.KeyEvent;

public class MainJFX extends Application {
    DeepSeaManager manager;

    public MainJFX() {
        this.manager = DeepSeaApp.manager;
    }


    // codigo para abrir duas janelas como da aula
    @Override
    public void start(Stage stage) {
        createOneStage(stage);
        //Stage stage1 = new Stage();
        //createOneStage(stage1);
        //stage1.setOnCloseRequest(windowEvent -> {
        //  stage.close();
        //});
    }

    private void createOneStage(Stage stage) {
        RootPane root = new RootPane(manager);

        Scene scene = new Scene(root, Settings.WIDTH_JANELA, Settings.HEIGHT_JANELA);

        //movimentações
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
                case W, UP     -> manager.mover(Direcao.CIMA);
                case S, DOWN   -> manager.mover(Direcao.BAIXO);
                case A, LEFT   -> manager.mover(Direcao.ESQUERDA);
                case D, RIGHT  -> manager.mover(Direcao.DIREITA);
                case ENTER     -> manager.iniciarDescida();
                case O         -> manager.abrirOficina();
                default        -> { return; } // qualquer outra tecla pressionada n faz nd
            }
            e.consume();
        });

        stage.setTitle("Deep Sea Mining");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.show();
    }
}
