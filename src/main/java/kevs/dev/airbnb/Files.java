package kevs.dev.airbnb;

import javafx.scene.image.Image;

import java.io.File;

public class Files {
    public File logoFile = new File("src/main/resources/assets/logo.jpg");
    public Image logoImage = new Image(logoFile.toURI().toString());
}
