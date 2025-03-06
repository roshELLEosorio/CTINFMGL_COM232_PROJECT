package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BookListController implements Initializable {
    Components components = new Components();
    Window window = new Window();
    @FXML
    private FlowPane bookingList;
    @FXML
    private Button backButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Booking List Section");
        components.generateHostBooking(bookingList);
        backButton.setOnMouseClicked((event -> window.goTo("host.dashboard.view", "TravelFree", event)));
    }
}
