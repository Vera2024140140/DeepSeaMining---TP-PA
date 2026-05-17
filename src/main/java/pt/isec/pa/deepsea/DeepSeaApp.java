package pt.isec.pa.deepsea;

import javafx.application.Application;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.ui.MainJFX;

public class DeepSeaApp {
    public static DeepSeaManager manager = null;

    public static void main(String[] args) {
        manager = new DeepSeaManager();
        Application.launch(MainJFX.class, args);
    }
}
