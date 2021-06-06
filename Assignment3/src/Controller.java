import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * The Controller class for the GUI
 * Used to control the View depending on user input
 */
public class Controller {
    private View view;

    /**
     * Constructor of Constroller
     * @param view Gui view
     */
    public Controller(View view) {
        this.view = view;
        view.update();
        view.addButtonHandler(new ButtonDoer());
    }

    /**
     * ButtonHandler deal with button action.
     */
    private class ButtonDoer implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            /* Get the button which was just pressed */
            Button pressedButton = (Button)event.getSource();
            String function = pressedButton.getText();

            /*
             * Control player to do the given action.
             * Use view to implement action,
             * view has methods which can do this.
             */
            switch (function) {
                case "East":
                case "West":
                case "South":
                case "North":
                    view.move(function);
                    view.update();
                    break;
                case "Look":
                    view.look();
                    view.update();
                    break;
                case "Examine":
                    view.examine();
                    view.update();
                    break;
                case "Drop":
                    view.drop();
                    view.update();
                    break;
                case "Take":
                    view.take();
                    view.update();
                    break;
                case "Fight":
                    view.fight();
                    view.update();
                    break;
                case "Save":
                    view.save();
                    break;
            }
        }
    }
}
