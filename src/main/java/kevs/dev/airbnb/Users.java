package kevs.dev.airbnb;

import java.sql.Date;
import java.time.LocalDate;

public class Users {
    private int userId;
    private String username;
    private long phoneNumber;
    private LocalDate birthdate;
    private String role;
    public Users (int userId, String username, long phoneNumber, LocalDate birthdate, String role) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.role = role;
    }
    public int getUserId () {
        return userId;
    }
    public void setUserId (int userId) {
        this.userId = userId;
    }
    public String getUsername () {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public long getPhoneNumber () {
        return phoneNumber;
    }
    public void setPhoneNumber (long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getBirthdate () {
        return birthdate;
    }
    public void setBirthdate (LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public String getRole() {
        return role;
    }
    public void setRole (String role) {
        this.role = role;
    }
}
