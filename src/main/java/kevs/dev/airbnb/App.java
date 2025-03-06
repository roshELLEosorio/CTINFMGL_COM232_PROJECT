package kevs.dev.airbnb;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Window window = new Window();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("started.view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.windowInfo(stage, "TravelFree");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}