package pt.isec.pa.deepsea.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.Main;
import pt.isec.pa.deepsea.model.DeepSeaManager;

public class MainJFX extends Application {
    DeepSeaManager manager;

    public MainJFX() {
        this.manager = Main.manager;
    }


    @Override
    public void start(Stage stage) {
        RootPane root = new RootPane(manager);

        Scene scene = new Scene(root, 1000, 800);

        stage.setTitle("Deep Sea Mining");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}