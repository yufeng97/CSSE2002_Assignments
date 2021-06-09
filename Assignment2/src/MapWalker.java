import java.util.ArrayList;
import java.util.List;

/**
 * An Object which can walk around to visit all the reachable rooms.
 */
public class MapWalker {

    /* Start room for Mapwalker. */
    private Room start;
    /* A list to store the rooms need visiting. */
    private List<Room> roomToVisit;
    /* A list to store the rooms which can reach. */
    private List<Room> reachableRoom;

    /**
     * Choose start room but Do not start the walk process.
     * @param start The room which starts to walk.
     */
    public MapWalker(Room start) {
        this.start = start;
        roomToVisit = new ArrayList<>();
        reachableRoom = new ArrayList<>();
    }

    /**
     * Clears any state from previous walks.
     */
    protected void reset() {
        roomToVisit.clear();
        reachableRoom.clear();
    }

    /**
     * Visit all reachable rooms and call visit().
     * Note that code related to tracking which rooms have been visited
     * should be included here and not in visit().
     * Call reset() at the beginning of this routine.
     */
    public void walk() {
        reset();
        roomToVisit.add(start);
        while (!roomToVisit.isEmpty()) {
            Room room = roomToVisit.remove(0);
            if (!reachableRoom.contains(room)) {
                for (String key: room.getExits().keySet())
                    roomToVisit.add(room.getExits().get(key));
                visit(room);
                reachableRoom.add(room);
            }
        }
    }

    /**
     * Check whether the room has been visited.
     * @param room Room to query.
     * @return true, if room has been processed;
     *          else false, if not processed.
     */
    public boolean hasVisited(Room room) {
        return reachableRoom.contains(room) && !roomToVisit.contains(room);
    }

    /**
     * Process a room override to customise behaviour.
     * @param room Room to deal with.
     */
    protected void visit(Room room) {}
}
