import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Main CrawGui class which starts the JavaFX application
 */
public class CrawlGui extends javafx.application.Application {
    private static Player player;
    private static Room root;

    public static void main(String[] args) {
        load(args);
        launch(args);
    }

    /**
     * This is where the JavaFX windows starts and stage is passed as argument
     * @param stage The primary stage of the JavaFX application
     */
    @Override
    public void start(Stage stage){
        if (player instanceof Explorer) {
            stage.setTitle("Crawl - " + "Explorer");
        }
        View view = new View(root, player);
        Controller controller = new Controller(view);
        stage.setScene(view.getScene());
        stage.show();
    }

    /**
     * Load map file,
     * if argument is missing,
     * print message "Usage: java CrawGui mapname";
     * if argument is present but can not load map
     * print message "Unable to load file".
     * @param args map filename
     */
    private static void load(String[] args) {
        if (args != null) {
            String file = Arrays.toString(args);
            file = file.substring(1, file.length() - 1);
            if (MapIO.loadMap(file) == null) {
                System.err.println("Unable to load file");
                System.exit(2);
            } else {
                Object[] list = MapIO.loadMap(file);
                player = (Player)list[0];
                root = (Room)list[1];
            }
        } else {
            System.err.println("Usage: java CrawGui mapname");
            System.exit(1);
        }
    }

}
