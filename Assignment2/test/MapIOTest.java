import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MapIOTest {

    /* Instances of Room */
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;
    private Room r5;
    private Room r6;
    private Room r7;
    private Room root;
    /* Instances of Item. */
    private Builder b1;
    private Critter c1;
    private Explorer e1;
    private Treasure gold;
    /* Instance of BoundsMapper. */
    private BoundsMapper walker;

    @Before
    public void setUp() {
        /* Initialize Room */
        r1 = new Room("r1");
        r2 = new Room("r2");
        r3 = new Room("r3");
        r4 = new Room("r4");
        r5 = new Room("r5");
        r6 = new Room("r6");
        r7 = new Room("r7");
        /* Initialize Item. */
        b1 = new Builder("builder1", "a builder1\n", r1);
        c1 = new Critter("critter1", "a critter1\n", 50, 10);
        e1 = new Explorer("explorer1", "a explorer1\n");
        gold = new Treasure("gold\n", 50);
        /* Add Items into Room. */
        r1.enter(new Critter("frog", "a frog", 1, 5));
        r1.enter(new Explorer("doris", "a doris"));
        r2.enter(c1);
        r2.enter(gold);
        r3.enter(new Treasure("iron", 30));
        r3.enter(new Critter("witcher", "a witcher", 80, 30));
        r4.enter(gold);
        r5.enter(c1);
        r6.enter(new Treasure("herb", 20));
        r6.enter(new Critter("boss", "final boss", 150, 50));
        /* Add Exits to each Room r1 to r6, but not r7. */
        try {
            Room.makeExitPair(r1, r2, "North", "South");
            Room.makeExitPair(r1, r3, "East", "West");
            r1.addExit("South", r4);
            Room.makeExitPair(r2, r5, "West", "East");
            r4.addExit("South", r6);
        } catch (ExitExistsException | NullRoomException | NullPointerException ignored) {}
    }

    @Test
    public void testSerializeMapAndDeserializeMap() {
        // deserializeMap wrong file which has incorrect format.
        assertEquals(null, MapIO.deserializeMap(null));
        assertEquals(null, MapIO.deserializeMap("wrongfile"));
        MapIO.serializeMap(r1, "serializedmap1");
        root = MapIO.deserializeMap("serializedmap1");
        // The room from deserializedMap should have the same description,
        // same exits, same contents with start room.
        assertEquals(r1.getDescription(), root.getDescription());
        assertEquals(r1.getExits().keySet(), root.getExits().keySet());
        assertEquals(r1.getContents().size(), root.getContents().size());
        List<Room> r1ExitRoom = getAllRooms(r1);
        List<Room> rootExitRoom = getAllRooms(root);
        // Check the whole map's room.
        for (int i = 0; i < r1ExitRoom.size(); i++) {
            Room roomInR1 = r1ExitRoom.get(i);
            Room roomInRoot = rootExitRoom.get(i);
            for(String key: roomInR1.getExits().keySet()) {
                assertEquals(roomInR1.getExits().get(key).getDescription(),
                        roomInRoot.getExits().get(key).getDescription());
            }
            for (int k = 0; k < roomInR1.getContents().size(); k++) {
                Thing t1 = roomInR1.getContents().get(k);
                Thing t2 = roomInRoot.getContents().get(k);
                assertEquals(t1.repr(), t2.repr());
            }
        }
    }

    @Test
    public void decodeThing() {
        Thing t1 = MapIO.decodeThing("B;builder1;a builder1\n", r1);
        Thing t2 = MapIO.decodeThing("C;50.00000;10;critter1;a critter1\n", r1);
        Thing t3 = MapIO.decodeThing("E;10;explorer1;a explorer1\n", r1);
        Thing t4 = MapIO.decodeThing("$;50.00000;gold\n", r1);
        assertEquals(null, MapIO.decodeThing("$;50.00000;gold\n", null));
        assertEquals(null, MapIO.decodeThing(null, r1));
        assertEquals(null, MapIO.decodeThing("", r1));
        assertEquals(null, MapIO.decodeThing("A", r1));
        assertEquals(null, MapIO.decodeThing("E;10.0;explorer1;a explorer1\n", r1));
        assertEquals(null, MapIO.decodeThing("C;50.00h000;1f0;critter1;a critter1\n", r1));
        assertEquals(b1.repr(), t1.repr());
        assertEquals(c1.repr(), t2.repr());
        assertEquals(e1.repr(), t3.repr());
        assertEquals(gold.repr(), t4.repr());
    }

    @Test
    public void testSaveMapAndLoadMap() {
        // save file with null filename, null room are both incorrect
        assertFalse(MapIO.saveMap(r1, null));
        assertFalse(MapIO.saveMap(null, "mymap"));
        assertFalse(MapIO.saveMap(null, null));
        assertTrue(MapIO.saveMap(r1, "mymap"));
        // load wrong file or filename is null are both not correct.
        assertNull(MapIO.loadMap(null));
        assertNull(MapIO.loadMap("unknownfile"));
        Object[] list = MapIO.loadMap("mymap");
        root = (Room)list[1];
        // Two rooms should have the same description, same exits,
        // with start room.
        assertEquals(root.getDescription(), r1.getDescription());;
        assertEquals(root.getExits().keySet(), r1.getExits().keySet());
        for (String key: root.getExits().keySet()) {
            assertEquals(root.getExits().get(key).getDescription(),
                    r1.getExits().get(key).getDescription());
        }
        boolean foundPlayer = false;
        // If player is in root room,
        // contents size in root room = contents size in r1 room - 1.
        for (Thing thing: r1.getContents()) {
            if (thing instanceof Player) {
                foundPlayer = true;
                Thing player = (Player)list[0];
                assertEquals(thing.repr(), player.repr());
                assertEquals(r1.getContents().size() - 1, root.getContents().size());
                break;
            }
        }
        // If player is not in root room, two rooms should have the
        // same contents.
        if (!foundPlayer) {
            assertEquals(r1.getContents().size(), root.getContents().size());
            for (int i = 0; i < r1.getContents().size(); i++) {
                Thing t1 = r1.getContents().get(i);
                Thing t2 = root.getContents().get(i);
                assertEquals(t1.repr(), t2.repr());
            }
        }
    }

    /* An arithmetic that get all rooms. */
    private List<Room> getAllRooms(Room root) {
        List<Room> todo = new ArrayList<Room>();
        List<Room> allRooms = new ArrayList<Room>();
        todo.add(root);
        while (!todo.isEmpty()) {
            Room room = todo.remove(0);
            if (!allRooms.contains(room)) {
                for (String key: room.getExits().keySet()) {
                    todo.add(room.getExits().get(key));
                }
                allRooms.add(room);
            }
        }
        return allRooms;
    }
}