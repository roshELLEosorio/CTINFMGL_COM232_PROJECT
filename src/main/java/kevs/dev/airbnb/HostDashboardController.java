package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class HostDashboardController implements Initializable {
    Window window = new Window();
    Components component = new Components();
    @FXML
    private Button bookedPropertyButton, addPropertyButton, reloadButton, logoutButton;

    @FXML
    private FlowPane propertyContainer;
    public void navigatePanel (MouseEvent event) {
        try{
            if (event.getSource() == bookedPropertyButton) {
                window.goTo("bookedList.view", "TravelFree", event);
            }

            if(event.getSource() == addPropertyButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addProperty.view.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                window.windowInfo(stage, "TravelFree | Property");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }

            if(event.getSource() == reloadButton) {
                component.generateHostProperty(propertyContainer);
            }
            if(event.getSource() == logoutButton) {
                SessionManager.getInstance().logout();
                window.goTo("login.view", "TravelFree | Login", event);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        component.generateHostProperty(propertyContainer);
    }
}
