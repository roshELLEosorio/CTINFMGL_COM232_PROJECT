package kevs.dev.airbnb;

import javafx.scene.input.MouseEvent;
import kevs.dev.airbnb.Window;

public class GetStartedController {
    Window window = new Window();
    public void continueToLogin (MouseEvent event){
        window.goTo("login.view", "TravelFree | Login", event);
    }
}
