package pt.isec.pa.deepsea.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.Main;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;

public class MainJFX extends Application {
    DeepSeaManager manager;

    public MainJFX() {
        this.manager = Main.manager;
    }


    @Override
    public void start(Stage stage) {
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
        stage.setResizable(false);
        stage.show();
    }
}