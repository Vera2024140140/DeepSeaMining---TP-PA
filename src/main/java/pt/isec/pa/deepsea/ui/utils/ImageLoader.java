package pt.isec.pa.deepsea.ui.utils;

import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageLoader {
    private static final HashMap<String, Image> images = new HashMap<>();

    public static Image getImage(String nome) {
        Image image = images.get(nome);
        if (image == null) {
            image = new Image(ImageLoader.class.getResourceAsStream("/images/" + nome));
            images.put(nome, image);
        }
        return image;
    }
}
