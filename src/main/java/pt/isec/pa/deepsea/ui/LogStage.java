package pt.isec.pa.deepsea.ui;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import pt.isec.pa.deepsea.model.data.Settings;

public class LogStage extends Stage {
    private ListView<String> listaLogs;
    private final DeepSeaManager manager;
    public LogStage(DeepSeaManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        setOnShowing(evento->update()); //quando a janela é mostrada, mostra os logs
        manager.addPropertyChangeListener(DeepSeaManager.PROP_LOG,evento->{
            update();
        });
    }

    private void createViews() {
        this.listaLogs = new ListView<>();
        VBox conteudo = new VBox(listaLogs);

        setTitle("Deep Sea — Logs");
        setScene(new Scene(conteudo, Settings.WIDTH_LOG, Settings.HEIGHT_LOG));
        setResizable(false);
        VBox.setVgrow(listaLogs, Priority.ALWAYS); //para a listaLogs ocupar a janela toda
    }

    private void update(){
        listaLogs.getItems().clear();
        listaLogs.getItems().addAll(manager.getLog());
        // scroll automático para o fim (entrada mais recente)
        if (!listaLogs.getItems().isEmpty()) {
            listaLogs.scrollTo(listaLogs.getItems().size() - 1);
        }
    }
}
