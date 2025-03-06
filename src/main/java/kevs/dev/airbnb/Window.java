package kevs.dev.airbnb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Window {
    Files file = new Files();
    public void goTo (String fxml, String windowTitle, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            windowInfo(stage, windowTitle);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void windowInfo(Stage stage, String title) {
        stage.setTitle(title);
        stage.getIcons().setAll(file.logoImage);
    }
}
