import java.util.HashMap;
import java.util.Map;

/**
 * BoundsMapper, the subclass of MapWalker, can walk around and visit
 * all reachable rooms. The visited rooms have the coordinate relative
 * to start room coordinate.
 */
public class BoundsMapper extends MapWalker {

    /* Map Rooms to coordinates. */
    public Map<Room, Pair> coords;
    /* Minimum x coordinate for rooms (root has x=0). */
    public int xMin;
    /* Maximum x coordinate for rooms (root has x=0). */
    public int xMax;
    /* Minimum y coordinate for rooms (root has y=0). */
    public int yMin;
    /* Maximum y coordinate for rooms (root has y=0). */
    public int yMax;

    /**
     * Base constructor for BoundsMapper.
     * @param root The room start to walk.
     */
    public BoundsMapper(Room root) {
        super(root);
        coords = new HashMap<>();
        xMin = xMax = 0;
        yMin = yMax = 0;
    }

    /**
     * Assign room coordinates relative to a neighbour.
     * If room has no known neighbours, give it coordinate (0,0).
     * If your "North" neighbour has coordinates (x,y), then
     * your coodinates should be (x, y-1).
     * If your "South" neighbour has coordinates (x,y), then
     * your coordinates should be (x, y+1).
     * If your "East" neighbour has coordinates (x,y), then
     * your coordinates should be (x+1, y).
     * If your "West" neighbour has coordinates (x,y), then
     * your coordinates should be (x-1, y).
     * Check for known coordinates in order: North, South, East, West.
     * @param room Room to assign coordinates to
     */
    @Override
    protected void visit(Room room) {
        Pair pair;
        if (room != null) {
            if (!coords.containsKey(room)) {
                if (coords.containsKey(room.getExits().get("North"))) {
                    yMin -= 1;
                    pair = new Pair(coords.get(room.getExits().get("North")).x,
                            coords.get(room.getExits().get("North")).y - 1);
                    coords.put(room, pair);
                } else if (coords.containsKey(room.getExits().get("South"))) {
                    yMax += 1;
                    pair = new Pair(coords.get(room.getExits().get("South")).x,
                            coords.get(room.getExits().get("South")).y + 1);
                    coords.put(room, pair);
                } else if (coords.containsKey(room.getExits().get("East"))) {
                    xMin -= 1;
                    pair = new Pair(coords.get(room.getExits().get("East")).x - 1,
                            coords.get(room.getExits().get("East")).y);
                    coords.put(room, pair);
                } else if (coords.containsKey(room.getExits().get("West"))) {
                    xMax += 1;
                    pair = new Pair(coords.get(room.getExits().get("West")).x + 1,
                            coords.get(room.getExits().get("West")).y);
                    coords.put(room, pair);
                } else {
                    pair = new Pair(0, 0);
                    coords.put(room, pair);
                }
            }
        }
    }

    /**
     * Called by walk --- clears any state from previous walks.
     * Call super.reset() internally to ensure that
     * parent state is cleared as well.
     */
    @Override
    public void reset() {
        super.reset();
        coords.clear();
        xMin = xMax = 0;
        yMin = yMax = 0;
    }
}