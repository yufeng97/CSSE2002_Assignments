import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * The View class creates the GUI view and has methods
 * which can update to the created view.
 */
public class View {
    /* Buttons */
    private Button[] buttons;
    /**
     * The root node of the scene graph, to add all
     * the GUI elements to.
     */
    private BorderPane rootBox;
    /* The text Area to display message */
    private TextArea messageArea;
    /* The map in the GUI */
    private Cartographer map;
    /* The BoundsMapper to get all the room and coords */
    private BoundsMapper walker;
    /* The root room of map */
    private Room root;
    /* The room where player is in */
    private Room currentRoom;
    /* Player */
    private Player player;
    /* The string get from dialog */
    private String item;

    /**
     * Constructor of View
     * @param root The root Room
     * @param player The player
     */
    public View(Room root, Player player) {
        this.root = root;
        currentRoom = root;
        this.player = player;
        rootBox = new BorderPane();
        addComponents();
        displayMessage("you find yourself in the " + currentRoom.getDescription());
    }

    /**
     * Get the Scene of the GUI with the scene graph
     * @return the current scene
     */
    public Scene getScene() {
        return new Scene(rootBox);
    }

    /**
     * Adds the given handler to the given ith button
     * @param handler the handler to add to the button
     */
    public void addButtonHandler(EventHandler<ActionEvent> handler) {
        /*
         * Adds a handler to the setOnAction meaning when buttons is pressed her
         * this handler and its handle method
         */
        for(Button button : buttons) {
            button.setOnAction(handler);
        }
    }

    /**
     * Adds all the GUI elements to the root layout
     * These is where the scene graph is created
     */
    private void addComponents() {
        VBox buttonArea = new VBox();
        addButtonPane(buttonArea);
        HBox canvasContainer = new HBox();
        addMap(canvasContainer);

        messageArea = new TextArea();
        messageArea.setStyle("-fx-border-color: blue");
        messageArea.setEditable(false);

        rootBox.setCenter(canvasContainer);
        rootBox.setRight(buttonArea);
        rootBox.setBottom(messageArea);
    }

    /**
     * Add all the Button to the Button container
     * @param box the container to add the elements to
     */
    private void addButtonPane(VBox box) {
        /* Initialize the Buttons */
        buttons = new Button[10];
        buttons[0] = new Button("North");
        buttons[1] = new Button("South");
        buttons[2] = new Button("East");
        buttons[3] = new Button("West");
        buttons[4] = new Button("Look");
        buttons[5] = new Button("Examine");
        buttons[6] = new Button("Drop");
        buttons[7] = new Button("Take");
        buttons[8] = new Button("Fight");
        buttons[9] = new Button("Save");
        /* Direction Buttons */
        GridPane directionPane = new GridPane();
        directionPane.add(buttons[0], 1, 0);
        directionPane.add(buttons[1], 1, 2);
        directionPane.add(buttons[2], 2, 1);
        directionPane.add(buttons[3], 0, 1);
        /* Function Buttons */
        GridPane functionPane = new GridPane();
        functionPane.add(buttons[4], 0, 0);
        functionPane.add(buttons[5], 1, 0);
        functionPane.add(buttons[6], 0, 1);
        functionPane.add(buttons[7], 1, 1);
        functionPane.add(buttons[8], 0, 2);
        functionPane.add(buttons[9], 0, 3);

        box.getChildren().addAll(directionPane, functionPane);
    }

    /**
     * get the whole map by BoundsMapper
     * add the canvas inside a HBox
     * the HBox (canvasContainer) is used so that border can
     * be added around the canvas
     * @param box the container to add the elements to
     */
    private void addMap(HBox box) {
        currentRoom.enter(player);
        walker = new BoundsMapper(currentRoom);
        walker.walk();
        int width = (walker.xMax - walker.xMin + 3) * 40;
        int height = (walker.yMax - walker.yMin + 3) * 40;
        map = new Cartographer(width, height);
        box.getChildren().add(map);
        box.setAlignment(Pos.CENTER);
    }

    /**
     * Draw the whole map including rooms, exits and stuffs.
     */
    private void drawMap() {
        int x;
        int y;
        for(Room room: walker.coords.keySet()) {
            x = walker.coords.get(room).x - walker.xMin + 1;
            y = walker.coords.get(room).y - walker.yMin + 1;
            map.drawSquare(x, y);
            for (Thing thing: room.getContents()) {
                map.drawStuff(x, y, thing);
            }
            for (String exit: room.getExits().keySet()) {
                map.drawLine(x, y, exit);
            }
        }
    }

    /**
     * Get the current room.
     * @return The room where player is in
     */
    private Room getCurrentRoom() {
        for (Room room: walker.coords.keySet()) {
            if (room.getContents().contains(player)) {
                currentRoom = room;
                break;
            }
        }
        return currentRoom;
    }

    /**
     * show message on the message area
     * @param message The message to show on message area
     */
    private void displayMessage(String message) {
        messageArea.appendText(message + "\n");
    }

    /**
     * control player to move in specified direction
     * @param direction the text on direction button
     */
    public void move(String direction) {
        if (direction.equals("East") || direction.equals("West")
                || direction.equals("South") || direction.equals("North")) {
            if (getCurrentRoom().getExits().containsKey(direction)) {
                if (getCurrentRoom().leave(player)) {
                    getCurrentRoom().getExits().get(direction).enter(player);
                    displayMessage("you enter " + getCurrentRoom().getDescription());
                } else {
                    displayMessage("Something prevents you from leaving");
                }
            } else {
                displayMessage("No door that way");
            }
        }
    }


    /**
     * update the map
     */
    public void update() {
        map.clear();
        drawMap();
    }

    /**
     * control player to look.
     * Show information about the short descriptions of each Thing
     * in the room, the short descriptions of each item in player's
     * inventory and total worth of all items.
     */
    public void look() {
        double totalWorth = 0;
        displayMessage(getCurrentRoom().getDescription() + " - you see:");
        for (Thing thing: getCurrentRoom().getContents()) {
            displayMessage(" " + thing.getShortDescription());
        }
        displayMessage("You are carrying:");
        for (Thing item: player.getContents()) {
            displayMessage(" " + item.getShortDescription());
            totalWorth += ((Lootable)item).getValue();
        }
        displayMessage("worth " + totalWorth + " in total");
    }

    /**
     * examine specified Thing whether exist in the player's
     * inventory or the room.
     * If found, show the long description of Thing;
     * else, show "Nothing found with that name"
     */
    public void examine() {
        dialog("Examine what?");
        boolean findInInventory = false;
        boolean findInRoom = false;
        if (item != null) {
            for (Thing thing : player.getContents()) {
                if (thing.getShortDescription().equals(item)) {
                    displayMessage(thing.getDescription());
                    findInInventory = true;
                    break;
                }
            }
            if (!findInInventory) {
                for (Thing thing : getCurrentRoom().getContents()) {
                    if (thing.getShortDescription().equals(item)) {
                        displayMessage(thing.getDescription());
                        findInRoom = true;
                        break;
                    }
                }
            }
            if (!findInInventory && !findInRoom) {
                displayMessage("Nothing found with that name");
            }
        }
    }

    /**
     * control player to drop something from player's inventory
     * and add it in to the current room.
     * If not in player's inventory,
     * display "Nothing found with that name"
     */
    public void drop() {
        dialog("Item to drop?");
        boolean itemIsDropped = false;
        if (item != null) {
            for (Thing thing : player.getContents()) {
                if (thing.getShortDescription().equals(item)) {
                    player.drop(item);
                    itemIsDropped = true;
                    getCurrentRoom().enter(thing);
                    break;
                }
            }
            if (!itemIsDropped) {
                displayMessage("Nothing found with that name");
            }
        }
    }

    /**
     * control player to take something from room
     * if an attempt is made to pick up a live Mob,
     * or remove items from room fails, take action fails.
     */
    public void take() {
        dialog("Take what");
        if (item != null) {
            for (Thing thing : getCurrentRoom().getContents()) {
                if (thing.getShortDescription().equals(item)) {
                    if (!(thing instanceof Mob)) {
                        if (getCurrentRoom().leave(thing)) {
                            player.add(thing);
                            break;
                        }
                    } else {
                        if (!((Mob)thing).isAlive()) {
                            if (getCurrentRoom().leave(thing)) {
                                player.add(thing);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * control player fight with specified target.
     * The fight will not occurs if there is no matching Critter,
     * or the matching Critter is dead.
     * If fight occurs, show the result of fight.
     * "You win" or "Game over"
     */
    public void fight() {
        dialog("Fight what?");
        for (Thing thing: getCurrentRoom().getContents()) {
            if (thing.getShortDescription().equals(item) &&
                    thing instanceof Critter && ((Mob)thing).isAlive()) {
                player.fight((Mob)thing);
                if (!((Mob)thing).isAlive()) {
                    displayMessage("You win");
                } else {
                    displayMessage("Game over");
                    for (Button button: buttons) {
                        button.setDisable(true);
                    }
                }
            }
        }
    }

    /**
     * save the map
     * if save successfully, show "Saved"
     * else "Unable to save"
     */
    public void save() {
        dialog("Save filename?");
        if (item != null) {
            if (MapIO.saveMap(root, item)) {
                displayMessage("Saved");
            } else {
                displayMessage("Unable to save");
            }
        }
    }

    /**
     * create a dialog box to get the input from user
     * @param title the title of dialog box
     */
    private void dialog(String title) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> item = s);
    }
}
