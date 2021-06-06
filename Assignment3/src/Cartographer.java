import javafx.scene.canvas.GraphicsContext;

/**
 * Cartographer responsible for rendering the "map" in the GUI.
 */
public class Cartographer extends javafx.scene.canvas.Canvas {
    private GraphicsContext context;
    private int width;
    private int height;

    /**
     * Constructor of Cartographer
     * @param width the width of Cartographer
     * @param height the height of Cartographer
     */
    public Cartographer(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
        context = getGraphicsContext2D();
    }

    /**
     * Draw a square with side equals 40.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void drawSquare(int x, int y) {
        context.strokeRect(x * 40, y * 40, 40, 40);
    }

    /**
     * Draw a line.
     * @param x x coordinate
     * @param y y coordinate
     * @param exit exit name
     */
    public void drawLine(int x, int y, String exit) {
        double midX;
        double midY;
        switch (exit) {
            case "North":
                midX = x * 1.0 + 0.5;
                midY = y;
                context.strokeLine( midX * 40, midY * 40 - 3, midX * 40, midY * 40 + 3);
                break;
            case "South":
                midX = x * 1.0 + 0.5;
                midY = y + 1;
                context.strokeLine(midX * 40, midY * 40 - 3, midX * 40, midY * 40 + 3);
                break;
            case "East":
                midX = x + 1;
                midY = y * 1.0 + 0.5;
                context.strokeLine(midX * 40 - 3, midY * 40, midX * 40 + 3, midY * 40);
                break;
            case "West":
                midX = x;
                midY = y * 1.0 + 0.5;
                context.strokeLine(midX * 40 - 3, midY * 40, midX * 40 + 3, midY * 40);
                break;
        }
    }

    /**
     * Draw item in the room.
     * @param x x coordinate
     * @param y y coordinate
     * @param thing item
     */
    public void drawStuff(int x, int y, Thing thing) {
        double x1;
        double y1;
        if (thing instanceof Player) {
            x1 = x * 40;
            y1 = y * 40;
            context.fillText("@", x1 + 2, 13 + y1);
        } else if (thing instanceof Treasure) {
            x1 = (x + 0.5) * 40;
            y1 = y * 40;
            context.fillText("$", x1 + 2, 13 + y1);
        } else if (thing instanceof Critter) {
            if (((Critter)thing).isAlive()) {
                x1 = x * 40;
                y1 = (y + 0.5) * 40;
                context.fillText("M", x1 + 2, 13 + y1);
            } else {
                x1 = (x + 0.5)* 40;
                y1 = (y + 0.5)* 40;
                context.fillText("m", x1 + 2, 13 + y1);
            }
        }
    }

    /**
     * Clear the image.
     */
    public void clear() {
        context.clearRect(0, 0, width, height);
    }

}
