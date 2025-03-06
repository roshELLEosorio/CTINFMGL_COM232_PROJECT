package kevs.dev.airbnb;

public class SessionManager {
    private static SessionManager instance;
    private Users loggedInUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(Users user) {
        this.loggedInUser = user;
    }

    public Users getLoggedInUser() {
        return loggedInUser;
    }
    public void logout() {
        loggedInUser = null;
    }
}
