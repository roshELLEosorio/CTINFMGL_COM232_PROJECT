package kevs.dev.airbnb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.Flow;

public class Components {
    DatabaseConnection connectDB = new DatabaseConnection();
    Connection connection = connectDB.getConnection();
    Users currentUser = SessionManager.getInstance().getLoggedInUser();
    Window window = new Window();
    String propertyCardStyle = "-fx-border-color: black; -fx-border-radius: 10px; -fx-pref-width: 300; -fx-pref-height: 190; -fx-padding: 15 10 15 10;  -fx-spacing: 15";
    String propertyNameStyle = "-fx-font-weight: 700; -fx-font-size: 15px;";
    String propertyAddressStyle = "-fx-font-weight: normal; -fx-font-size: 12px; -fx-wrap-text: true;";
    String propertyDescriptionStyle = "-fx-font-weight: normal; -fx-font-size: 12px; -fx-wrap-text: true; -fx-pref-height: 83;";
    String bookButtonStyle = "-fx-background-color: #1d89f3; -fx-text-fill: #F2EFE7; -fx-font-weight: 800; -fx-font-family: \"Lucida Fax\"; -fx-cursor: HAND; -fx-pref-width: 500;";
    String bookingCardStyle = "-fx-border-color: black; -fx-border-radius: 8px; -fx-spacing: 8px; -fx-padding: 8px; -fx-pref-width: 300; -fx-pref-height: 120;";
    String propertyHostCardStyle = "-fx-border-color: black; -fx-border-radius: 10px; -fx-pref-width: 300; -fx-padding: 15 10 15 10; -fx-spacing: 10px;";
    String propertyHostNameStyle = "-fx-font-weight: 700; -fx-font-size: 20px;";
    String propertyHostEditButtonStyle = "-fx-background-color: #1d89f3; -fx-text-fill: #F2EFE7; -fx-font-weight: 800; -fx-font-family: \"Lucida Fax\"; -fx-cursor: HAND; -fx-pref-width: 500; -fx-pref-height: 30;";
    String propertyHostDeleteButtonStyle = "-fx-background-color: #b0161f; -fx-text-fill: #F2EFE7; -fx-font-weight: 800; -fx-font-family: \"Lucida Fax\"; -fx-cursor: HAND; -fx-pref-width: 500; -fx-pref-height: 30;";
    String propertyHostDescriptionStyle = "-fx-font-weight: normal; -fx-font-size: 12px; -fx-wrap-text: true; -fx-pref-height: 60; -fx-alignment: TOP_LEFT;";
    String bookingCardCustomerStyle = "-fx-border-color: black; -fx-border-radius: 10px; -fx-padding: 15 10 15 10; -fx-spacing: 15px; -fx-pref-width: 400; -fx-pref-height: 134;";
    String bookingMarkAsPaidButtonStyle = "-fx-background-color: #1dc267; -fx-text-fill: #F2EFE7; -fx-font-weight: 800; -fx-font-family: \"Lucida Fax\"; -fx-cursor: HAND; ";
    public void generateProperty(FlowPane pane) {
        try {
            String propertyQuery = "SELECT * FROM tbl_property;";
            PreparedStatement propertyStatement = connection.prepareStatement(propertyQuery);
            ResultSet properties = propertyStatement.executeQuery();
            pane.getChildren().clear();
            while(properties.next()) {

                String propertyNameValue = properties.getString("propertyName");
                String propertyAddressValue = properties.getString("propertyAddress");
                String propertyDescValue = properties.getString("description");
                int propertyIdValue = properties.getInt("propertyId");

                VBox propertyCard = new VBox();
                propertyCard.setStyle(propertyCardStyle);
                propertyCard.setFillWidth(true);

                Label propertyName = new Label(properties.getString("propertyName"));
                Label propertyAddress = new Label("Location: " + properties.getString("propertyAddress"));
                Label propertyDesc = new Label(properties.getString("description").length() > 50 ? "Description: " + properties.getString("description").substring(0, 50) + "..." : "Description: " + properties.getString("description"));
                Label propertyRating = new Label(properties.getDouble("rating") < 1 ? "No Rating" : "Rating: " + properties.getString("rating"));
                propertyName.setStyle(propertyNameStyle);
                propertyDesc.setStyle(propertyDescriptionStyle);
                propertyAddress.setStyle(propertyAddressStyle);
                propertyRating.setStyle(propertyAddressStyle);

                Button bookButton = new Button("Book");
                bookButton.setStyle(bookButtonStyle);
                bookButton.setOnMouseClicked((event -> {
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("booking.view.fxml"));
                        Parent root = loader.load();

                        BookingController bookingController = loader.getController();
                        bookingController.propertyInfo(
                                propertyNameValue,
                                propertyAddressValue,
                                propertyDescValue,
                                propertyIdValue
                        );

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        window.windowInfo(stage, "TravelFree | Book");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }));

                propertyCard.getChildren().addAll(propertyName, propertyDesc, propertyAddress, propertyRating, bookButton);

                pane.getChildren().add(propertyCard);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void generateUserBooked(FlowPane pane) {
        try{
            String userBookedQuery = "SELECT * FROM booking_details WHERE userId = ?";
            PreparedStatement userBookedStatement = connection.prepareStatement(userBookedQuery);
            userBookedStatement.setInt(1, currentUser.getUserId());
            ResultSet booked = userBookedStatement.executeQuery();

            pane.getChildren().clear();
            while(booked.next()) {
                VBox bookingCard = new VBox();
                bookingCard.setStyle(bookingCardStyle);

                int propertyId = booked.getInt("propertyId");
                int bookingId = booked.getInt("bookingId");

                Label propertyName = new Label(booked.getString("propertyName") == null ? "Deleted Property" : booked.getString("propertyName"));
                Label checkInDate = new Label("Check In: " + booked.getString("checkInDate"));
                Label checkOutDate = new Label("Check Out: " + booked.getString("checkOutDate"));
                Label paymentStatus = new Label("Status: " + booked.getString("paymentStatus"));

                propertyName.setStyle(propertyNameStyle);
                checkInDate.setStyle(propertyAddressStyle);
                checkOutDate.setStyle(propertyAddressStyle);
                paymentStatus.setStyle(propertyAddressStyle);

                bookingCard.getChildren().addAll(propertyName, checkInDate, checkOutDate, paymentStatus);

                if(booked.getString("paymentStatus").equals("Paid")) {
                    Button reviewButton = new Button(booked.getString("reviewStatus").equals("Not Reviewed") ? "Rate this Property" : "Completed");
                    reviewButton.setStyle(bookingMarkAsPaidButtonStyle);
                    reviewButton.setDisable(booked.getString("reviewStatus").equals("Reviewed") || booked.getString("propertyName") == null);
                    reviewButton.setOnMouseClicked((event -> {
                        try{
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("rating.view.fxml"));
                            Parent root = loader.load();

                            RatingController ratingController = loader.getController();
                            ratingController.propertyDetails(propertyId, bookingId);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            window.windowInfo(stage, "TravelFree | Feedback");
                            stage.setScene(scene);
                            stage.show();

                        }catch (Exception ex) {

                        }
                    }));
                    bookingCard.getChildren().add(reviewButton);
                }
                pane.getChildren().add(bookingCard);
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void generateHostProperty(FlowPane pane) {
        try{
            String propertiesQuery = "SELECT * FROM tbl_property WHERE userId = ?";
            PreparedStatement propertiesStatement = connection.prepareStatement(propertiesQuery);
            propertiesStatement.setInt(1, currentUser.getUserId());
            ResultSet properties = propertiesStatement.executeQuery();

            pane.getChildren().clear();
            while(properties.next()) {
                VBox propertyCard = new VBox();
                propertyCard.setStyle(propertyHostCardStyle);

                int id = properties.getInt("propertyId");
                String name = properties.getString("propertyName");
                String address = properties.getString("propertyAddress");
                String description = properties.getString("description");
                String rating = String.valueOf(properties.getDouble("rating"));

                Label propertyName = new Label(name);
                Label propertyDesc = new Label(description);
                Label propertyRating = new Label("Rating: " + rating);

                propertyName.setStyle(propertyHostNameStyle);
                propertyDesc.setStyle(propertyHostDescriptionStyle);
                propertyRating.setStyle(propertyAddressStyle);

                Button editPropertyButton = new Button("Edit Property");
                editPropertyButton.setStyle(propertyHostEditButtonStyle);
                editPropertyButton.setOnMouseClicked((event -> {
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("editProperty.view.fxml"));
                        Parent root = loader.load();

                        EditPropertyController editPropertyController = loader.getController();
                        editPropertyController.propertyDetails(name, address, description, id);

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                }));

                Button deletePropertyButton = new Button("Delete Property");
                deletePropertyButton.setStyle(propertyHostDeleteButtonStyle);
                deletePropertyButton.setOnMouseClicked((event -> {
                    try{
                        String deletePropertyQuery = "DELETE FROM tbl_property WHERE propertyId = ?";
                        PreparedStatement deletePropertyStatement = connection.prepareStatement(deletePropertyQuery);
                        deletePropertyStatement.setInt(1, id);

                        int executeDeleteProperty = deletePropertyStatement.executeUpdate();

                        if(executeDeleteProperty > 0) {
                            System.out.println("Property deleted successfully.");
                            generateHostProperty(pane);
                        }

                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }));

                propertyCard.getChildren().addAll(propertyName, propertyDesc, propertyRating, editPropertyButton, deletePropertyButton);

                pane.getChildren().add(propertyCard);
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void generateHostBooking(FlowPane pane) {
        try{
            String customerBookingQuery = "SELECT * FROM booking_list WHERE hostId = ?";
            PreparedStatement customerBookingStatement = connection.prepareStatement(customerBookingQuery);
            customerBookingStatement.setInt(1, currentUser.getUserId());
            ResultSet bookings = customerBookingStatement.executeQuery();

            pane.getChildren().clear();
            while(bookings.next()) {
                HBox bookingCard = new HBox();
                bookingCard.setStyle(bookingCardCustomerStyle);

                int paymentId = bookings.getInt("paymentId");

                VBox customerDetails = new VBox();
                customerDetails.setStyle("-fx-spacing: 10; -fx-pref-width: 195;");

                Label customerName = new Label("Customer Name: " + bookings.getString("username"));
                Label customerPhoneNumber = new Label("Contact No.: " + bookings.getString("phoneNumber"));
                Label customerPaymentStatus = new Label("Payment Status: " + bookings.getString("paymentStatus"));

                customerName.setStyle(propertyNameStyle);
                customerDetails.getChildren().addAll(customerName, customerPhoneNumber, customerPaymentStatus);

                VBox propertyDetails = new VBox();
                propertyDetails.setStyle("-fx-spacing: 10;");

                Label propertyNameText = new Label(bookings.getString("propertyName"));
                Label checkInDate = new Label("Check In: " + bookings.getDate("checkInDate"));
                Label checkOutDate = new Label("Check Out: " + bookings.getDate("checkOutDate"));

                Button markAsPaidButton = new Button(bookings.getString("paymentStatus").equals("Paid") ? "Completed" : "Mark as Paid");
                markAsPaidButton.setStyle(bookingMarkAsPaidButtonStyle);
                markAsPaidButton.setDisable(bookings.getString("paymentStatus").equals("Paid"));
                markAsPaidButton.setOnMouseClicked((event -> {
                    try{
                        String markAsPaidQuery = "UPDATE tbl_payment SET paymentStatus = ? WHERE paymentId = ?";
                        PreparedStatement markAsPaidStatement = connection.prepareStatement(markAsPaidQuery);
                        markAsPaidStatement.setString(1, "Paid");
                        markAsPaidStatement.setInt(2, paymentId);

                        int executeMarkAsPaid = markAsPaidStatement.executeUpdate();
                        if(executeMarkAsPaid > 0) {
                            System.out.println("Customers booked has been paid.");
                            generateHostBooking(pane);
                        }

                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }));
                propertyNameText.setStyle(propertyNameStyle);
                propertyDetails.getChildren().addAll(propertyNameText, checkInDate, checkOutDate, markAsPaidButton);

                bookingCard.getChildren().addAll(customerDetails, propertyDetails);

                pane.getChildren().add(bookingCard);
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
