package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    Users currentUser = SessionManager.getInstance().getLoggedInUser();
    @FXML
    private Label propertyName, propertyDescription, propertyAddress, propertyId;
    @FXML
    private DatePicker checkInDate, checkOutDate;
    public void propertyInfo (String name, String address, String description, int id) {
        propertyName.setText(name);
        propertyAddress.setText(address);
        propertyDescription.setText(description);
        propertyId.setText(String.valueOf(id));
    }
    public void submitBooking (MouseEvent event) {
        try{
            Date checkIn = Date.valueOf(checkInDate.getValue());
            Date checkOut = Date.valueOf(checkOutDate.getValue());

            String bookQuery = "INSERT INTO tbl_booking (propertyId, userId, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";
            PreparedStatement bookStatement = connection.prepareStatement(bookQuery, Statement.RETURN_GENERATED_KEYS);
            bookStatement.setInt(1, Integer.parseInt(propertyId.getText()));
            bookStatement.setInt(2, currentUser.getUserId());
            bookStatement.setDate(3, checkIn);
            bookStatement.setDate(4, checkOut);

            int success = bookStatement.executeUpdate();

            if(success > 0) {
                System.out.println("Booked Successfully.");

                ResultSet generatedKeys = bookStatement.getGeneratedKeys();
                if(generatedKeys.next()) {
                    int bookingId = generatedKeys.getInt(1);
                    String paymentQuery = "INSERT INTO tbl_payment (bookingId) VALUES (?);";
                    PreparedStatement paymentStatement = connection.prepareStatement(paymentQuery);
                    paymentStatement.setInt(1, bookingId);
                    int paymentSuccess = paymentStatement.executeUpdate();

                    if(paymentSuccess > 0) {
                        cancelBooking(event);
                    }
                }
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void cancelBooking (MouseEvent event) {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
