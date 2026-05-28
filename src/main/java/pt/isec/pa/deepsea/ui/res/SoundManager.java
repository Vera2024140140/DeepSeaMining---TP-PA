package pt.isec.pa.deepsea.ui.res;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import pt.isec.pa.deepsea.model.DeepSeaManager;

import java.util.HashMap;

public class SoundManager {
    private SoundManager() { }
    private static MediaPlayer mp;
    private static MediaPlayer mpLoop;
    private static final BooleanProperty somLigado = new SimpleBooleanProperty(true);
    private static String somPendente;
    private static final HashMap<String, Media> sounds = new HashMap<>();
    public static BooleanProperty somLigadoProperty() {
        return somLigado;
    }
    public static boolean isSomLigado() {
        return somLigado.get();
    }
    public static void setSomLigado(boolean v){
        somLigado.set(v);
        if (!v) {
            stop();
            stopLoop();
        }
    }
    private static Media getMedia(String ficheiro) {
        if (!sounds.containsKey(ficheiro)) {
            var url = SoundManager.class.getResource("/sounds/" + ficheiro);
            if (url != null) {
                sounds.put(ficheiro, new Media(url.toExternalForm()));
            }
        }
        return sounds.get(ficheiro);
    }
    public static void stop() {
        if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING)
            mp.stop();
        mp = null;
        somPendente = null;
    }
    public static boolean playMovimento(String somMover) {
        if (!somLigado.get()) {
            return false;
        }
        try {
            stop();
            Media music = getMedia(somMover);
            if (music == null) return false;
            mp = new MediaPlayer(music);
            mp.setStartTime(Duration.ZERO);
            mp.setStopTime(Duration.millis(500));
            mp.setOnEndOfMedia(() -> {
                mp = null;
                String proximo = somPendente;
                somPendente = null;
                if (proximo != null) playSomSobreposto(proximo);
            });
            mp.play();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean playSomSobreposto(String ficheiro) {
        if(!somLigado.get()){
            return false;
        }
        try {
            if(mp != null){
                somPendente = ficheiro;
            }else{
                Media music = getMedia(ficheiro);
                if (music == null) return false;
                MediaPlayer mp2 = new MediaPlayer(music);
                mp2.play();
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static void playLoop(String ficheiro) {
        stopLoop(); // para o loop anterior se existir
        if (!somLigado.get()) return;

        Media media = getMedia(ficheiro);
        if (media == null) return ;
        mpLoop = new MediaPlayer(media);
        mpLoop.setCycleCount(MediaPlayer.INDEFINITE); // repete para sempre
        mpLoop.play();
    }
    public static void stopLoop() {
        if (mpLoop != null) {
            mpLoop.stop();
            mpLoop = null;
        }
    }
    public static void setSounds(DeepSeaManager manager){
        manager.addPropertyChangeListener(pt.isec.pa.deepsea.model.DeepSeaManager.PROP_ABRIR_OFICINA, evt -> {
            playLoop("musicaOficina.mp3");
        });
        manager.addPropertyChangeListener(pt.isec.pa.deepsea.model.DeepSeaManager.PROP_FECHAR_OFICINA, evt -> {
            stopLoop();
        });
        manager.addPropertyChangeListener(pt.isec.pa.deepsea.model.DeepSeaManager.PROP_MINERIO, evt -> {
            playSomSobreposto("minerio.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_MOVER_DRONE, evt -> {
            playMovimento("moverDrone.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_MOVER_NAVIO, evt -> {
            playMovimento("moverNavio.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_ROCHA, evt -> {
            playSomSobreposto("dronerocha.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_CORRENTE, evt -> {
            playSomSobreposto("dronecorrente.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_ANIMAL_MARINHO, evt -> {
            playSomSobreposto("polvo.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_MONSTRO, evt -> {
            playSomSobreposto("monstro.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_GANHAR_JOGO, evt -> {
            playSomSobreposto("ganharJogo.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PERDER_JOGO, evt -> {
            playSomSobreposto("somPerder.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_ARTEFACTO, evt -> {
            playSomSobreposto("recolherArtefacto.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PERDER_PUZZLE, evt -> {
            playSomSobreposto("somPerder.mp3");
        });
        manager.addPropertyChangeListener(DeepSeaManager.PROP_PERDER_DRONE, evt -> {
            playSomSobreposto("perderDrone.mp3");
        });
    }
}
