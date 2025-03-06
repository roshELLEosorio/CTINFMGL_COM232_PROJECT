package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import kevs.dev.airbnb.DatabaseConnection;
import kevs.dev.airbnb.Window;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    Window window = new Window();
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    @FXML
    private TextField usernameField, phoneNumberField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private DatePicker  birthdateField;
    @FXML
    private ComboBox<String> roleDropdown;
    public void signIn(MouseEvent event) {
        window.goTo("login.view", "WayStay | Login", event);
    }
    public void registerAccount(MouseEvent event) {
        try {

            String username = usernameField.getText();
            String phoneNumber = phoneNumberField.getText();
            LocalDate birthdate = birthdateField.getValue();
            String role = roleDropdown.getValue();

            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if(!Objects.equals(password, confirmPassword)) {
                System.out.println("Password do not match.");
                return;
            }

            String createAccountQuery = "INSERT INTO tbl_users (username, phoneNumber, birthdate, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement createAccountStatement = connection.prepareStatement(createAccountQuery);
            createAccountStatement.setString(1, username);
            createAccountStatement.setString(2, phoneNumber);
            createAccountStatement.setDate(3, Date.valueOf(birthdate));
            createAccountStatement.setString(4, password);
            createAccountStatement.setString(5, role);

            int newUser = createAccountStatement.executeUpdate();

            if(newUser > 0) {
                System.out.println("Created an account successfully!");
                window.goTo("login.view", "TravelFree | Login", event);
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles = {"User", "Host"};
        roleDropdown.getItems().setAll(roles);
    }
}
