package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AddPropertyController implements Initializable {
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    Users currentUser = SessionManager.getInstance().getLoggedInUser();
    @FXML
    private TextField propertyNameField, propertyAddressField;
    @FXML
    private TextArea propertyDescriptionField;
    public void createProperty (MouseEvent event) {
        try{
            String name = propertyNameField.getText();
            String address = propertyAddressField.getText();
            String description = propertyDescriptionField.getText();

            if(name.isEmpty() || address.isEmpty() || description.isEmpty()) {
                return;
            }

            String newPropertyQuery = "INSERT INTO tbl_property (userId, propertyName, propertyAddress, description) VALUES (?, ?, ?, ?)";
            PreparedStatement newPropertyStatement = connection.prepareStatement(newPropertyQuery);
            newPropertyStatement.setInt(1, currentUser.getUserId());
            newPropertyStatement.setString(2, name);
            newPropertyStatement.setString(3, address);
            newPropertyStatement.setString(4, description);

            int newProperty = newPropertyStatement.executeUpdate();

            if(newProperty > 0) {
                System.out.println("New property created!");
                Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                currentStage.close();
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void cancel (MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
