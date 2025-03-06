package kevs.dev.airbnb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RatingController implements Initializable {
    Users currentUser = SessionManager.getInstance().getLoggedInUser();
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();

    @FXML
    private Label propertyIdText, bookingIdText;
    @FXML
    private TextField rateField;
    private LocalDate currentDate = LocalDate.now();
    public void propertyDetails (int propertyId, int bookingId) {
        propertyIdText.setText(String.valueOf(propertyId));
        bookingIdText.setText(String.valueOf(bookingId));
        System.out.println(propertyIdText.getText());
    }
    public void createReview(MouseEvent event) {
        try{
            String reviewQuery = "INSERT INTO tbl_review (propertyId, bookingId, userId, date, rating) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement reviewStatement = connection.prepareStatement(reviewQuery);
            reviewStatement.setInt(1, Integer.parseInt(propertyIdText.getText()));
            reviewStatement.setInt(2, Integer.parseInt(bookingIdText.getText()));
            reviewStatement.setInt(3, currentUser.getUserId());
            reviewStatement.setDate(4, Date.valueOf(currentDate));
            reviewStatement.setDouble(5, Integer.parseInt(rateField.getText()));

            int executeReview = reviewStatement.executeUpdate();

            if(executeReview > 0) {
                System.out.println("Thank you for your feedback!");
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
