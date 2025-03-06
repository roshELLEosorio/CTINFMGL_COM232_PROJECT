package kevs.dev.airbnb;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class EditPropertyController implements Initializable {
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    @FXML
    private TextField propertyNameField, propertyAddressField;
    @FXML
    private TextArea propertyDescriptionField;
    @FXML
    private Label propertyIdField, headerText;
    public void propertyDetails (String name, String address, String description, int id) {
        propertyIdField.setText(String.valueOf(id));
        propertyNameField.setText(name);
        propertyAddressField.setText(address);
        propertyDescriptionField.setText(description);
        headerText.setText("Edit " + name);
    }
    public void updateProperty (MouseEvent event) {
        try{

            int id = Integer.parseInt(propertyIdField.getText());
            String name = propertyNameField.getText();
            String address = propertyAddressField.getText();
            String description = propertyDescriptionField.getText();

            String updatePropertyQuery = "UPDATE tbl_property SET propertyName = ?, propertyAddress = ?, description = ? WHERE propertyId = ?";
            PreparedStatement updatePropertyStatement = connection.prepareStatement(updatePropertyQuery);
            updatePropertyStatement.setString(1, name);
            updatePropertyStatement.setString(2, address);
            updatePropertyStatement.setString(3, description);
            updatePropertyStatement.setInt(4, id);

            int executeUpdateProperty = updatePropertyStatement.executeUpdate();

            if(executeUpdateProperty > 0) {
                System.out.println("Property updated successfully");
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }


        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void cancel (MouseEvent event) {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
