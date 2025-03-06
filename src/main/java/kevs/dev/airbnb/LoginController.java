package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Window window = new Window();
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    private Parent root;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    public void createAccount(MouseEvent event) {
        window.goTo("register.view", "WayStay | Create an account", event);
    }
    public void signIn(MouseEvent event) {
        try{
            String username = usernameField.getText();
            String password = passwordField.getText();

            String loginQuery = "SELECT * FROM tbl_users WHERE username = ? AND password = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, username);
            loginStatement.setString(2, password);

            ResultSet result = loginStatement.executeQuery();

            if(result.next()) {
                int userId = result.getInt("userId");
                String fetchUsername = result.getString("username");
                long phoneNumber = result.getLong("phoneNumber");
                LocalDate birthdate = result.getDate("birthdate").toLocalDate();
                String role = result.getString("role");

                Users loggedInUser = new Users(userId, fetchUsername, phoneNumber, birthdate, role);
                SessionManager.getInstance().setLoggedInUser(loggedInUser);

                if(role.equals("Host")) {
                    window.goTo("host.dashboard.view", "TravelFree", event);
                    return;
                }

                window.goTo("user.dashboard.view", "TravelFree", event);
            } else {
                System.out.println("Invalid credentials.");
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
