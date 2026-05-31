package pt.isec.pa.deepsea.ui.barrasLaterais;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import pt.isec.pa.deepsea.model.DeepSeaManager;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.state.DeepSeaState;

public class BarraLateralSuperficie extends BarraLateralNavegacao {


    public BarraLateralSuperficie(DeepSeaManager manager) {
        super(manager);
        boolean ativo = manager.getState() == DeepSeaState.SUPERFICIE_STATE;
        setVisible(ativo);
        setManaged(ativo);
        if (ativo) update();
    }

    @Override
    void createViews(){
        super.createViews();

        //empurar botoes para o fundo
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        String estilosBtns =
            "-fx-background-color:" + Settings.BTN_PRIMARY + ";" +
            "-fx-font-family:" + Settings.FONTE + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-cursor: hand;";
        //BTN oficina
        Button btnOficina = new Button("Abrir a Oficina");
        btnOficina.setMaxWidth(Double.MAX_VALUE);
        btnOficina.setOnAction(e -> manager.abrirOficina());
        btnOficina.setPadding(new Insets(5, 25, 5, 25));
        btnOficina.setStyle(estilosBtns);
        btnOficina.setMinWidth(140);
        btnOficina.setMinHeight(16);



        //BTN iniciar descida
        Button btnDescida = new Button("Iniciar Descida");
        btnDescida.setMaxWidth(Double.MAX_VALUE);
        btnDescida.setOnAction(e -> manager.iniciarDescida());
        btnDescida.setPadding(new Insets(5, 25, 5, 25));
        btnDescida.setStyle(estilosBtns);
        btnDescida.setMinWidth(140);
        btnDescida.setMinHeight(16);

        // row para colocar btns lado a lado
        HBox rowBotoes = new HBox(5, btnOficina, btnDescida);
        rowBotoes.setAlignment(Pos.CENTER);
        rowBotoes.setSpacing(20);

        VBox.setMargin(rowBotoes, new Insets(0, 0, 20, 0));
        // adicionar spacer e btn's a vbox
        areaMeio.getChildren().addAll(spacer, rowBotoes);
    }
    @Override
    void registerHandlers(){
        super.registerHandlers();
        manager.addPropertyChangeListener(DeepSeaManager.PROP_STATE, evt -> {
            boolean ativo = manager.getState() == DeepSeaState.SUPERFICIE_STATE;
            setVisible(ativo);
            setManaged(ativo);
            if (ativo) update();
        });
    }
}
