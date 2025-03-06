package kevs.dev.airbnb;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    Components component = new Components();
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    Users currentUser = SessionManager.getInstance().getLoggedInUser();
    Window window = new Window();
    @FXML
    private Label headerText, subHeaderText;
    @FXML
    private FlowPane propertyContainer, bookingContainer;
    @FXML
    private Button reloadListingButton, listingButton, bookingButton, profileButton, logoutButton;
    @FXML
    private ScrollPane listingView, bookingView;
    @FXML
    private TextField usernameField, phoneNumberField;
    @FXML
    private DatePicker birthdateField;
    @FXML
    private VBox profileView;
    public void navigationPanel (MouseEvent event) {
        if(event.getSource() == listingButton){
            headerText.setText("Welcome Back, " + (currentUser.getUsername().isEmpty() ? null : currentUser.getUsername()) + "!");
            subHeaderText.setText("Here’s a look at your upcoming stays. Get ready for a great experience!");
            listingView.setVisible(true);
            bookingView.setVisible(false);
            profileView.setVisible(false);
        }
        if(event.getSource() == bookingButton){
            headerText.setText("Your Bookings");
            subHeaderText.setText("Here’s a list of all the properties you’ve booked. Manage your stays and get ready for your next adventure!");
            listingView.setVisible(false);
            bookingView.setVisible(true);
            profileView.setVisible(false);
        }
        if (event.getSource() == profileButton) {
            headerText.setText("Profile Settings");
            subHeaderText.setText("Manage your account information and update your preferences.");
            listingView.setVisible(false);
            bookingView.setVisible(false);
            profileView.setVisible(true);
        }

        if (event.getSource() == logoutButton) {
            SessionManager.getInstance().logout();
            window.goTo("login.view", "TravelFree | Login", event);
        }
    }

    public void updateUser (MouseEvent event) {
        try{
            String username = usernameField.getText();
            String phoneNumber = phoneNumberField.getText();
            LocalDate birthdate = birthdateField.getValue();

            String updateUserQuery = "UPDATE tbl_users SET username = ?, phoneNumber = ?, birthdate = ? WHERE userId = ?";
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
            updateUserStatement.setString(1, username);
            updateUserStatement.setLong(2, Long.parseLong(phoneNumber));
            updateUserStatement.setDate(3, Date.valueOf(birthdate));
            updateUserStatement.setInt(4, currentUser.getUserId());

            int updateUser = updateUserStatement.executeUpdate();

            if(updateUser > 0) {
                System.out.println("User profile updates successfully. Please re-login your account.");
                SessionManager.getInstance().logout();
                window.goTo("login.view", "TravelFree | Login", event);
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listingView.setVisible(true);
        bookingView.setVisible(false);
        profileView.setVisible(false);

        headerText.setText("Welcome Back, " + (currentUser.getUsername().isEmpty() ? null : currentUser.getUsername()) + "!");
        subHeaderText.setText("Here’s a look at your upcoming stays. Get ready for a great experience!");

        usernameField.setText(currentUser.getUsername());
        birthdateField.setValue(currentUser.getBirthdate());
        phoneNumberField.setText(String.valueOf(currentUser.getPhoneNumber()));

        component.generateProperty(propertyContainer);
        component.generateUserBooked(bookingContainer);

        reloadListingButton.setOnMouseClicked((event -> {
            component.generateProperty(propertyContainer);
            component.generateUserBooked(bookingContainer);
        }));
    }
}
