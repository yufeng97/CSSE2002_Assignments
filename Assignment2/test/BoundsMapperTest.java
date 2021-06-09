import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoundsMapperTest {

    /* Instances of Room */
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;
    private Room r5;
    private Room r6;
    private Room r7;
    /* Instance of BoundsMapper */
    private BoundsMapper walker;

    @Before
    public void setUp() {
        /* Initialise Room */
        r1 = new Room("r1");
        r2 = new Room("r2");
        r3 = new Room("r3");
        r4 = new Room("r4");
        r5 = new Room("r5");
        r6 = new Room("r6");
        r7 = new Room("r7");
        /* Initialise BoundsMapper */
        walker = new BoundsMapper(r1);
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
    public void visit() {
        // Before walking the coords should be zero.
        assertEquals(0, walker.coords.size());
        // And all the room should not be visited.
        assertFalse(walker.hasVisited(r1));
        assertFalse(walker.hasVisited(r2));
        assertFalse(walker.hasVisited(r3));
        assertFalse(walker.hasVisited(r4));
        assertFalse(walker.hasVisited(r5));
        assertFalse(walker.hasVisited(r6));
        assertFalse(walker.hasVisited(r7));
        // xMin = xMax = yMin = yMax = 0
        assertEquals(0, walker.xMin);
        assertEquals(0, walker.xMax);
        assertEquals(0, walker.yMin);
        assertEquals(0, walker.yMax);
        walker.walk();
        // After walking the number of coords should be
        // the number of all reachable rooms.
        assertEquals(6, walker.coords.size());
        // r1 to r6 should be visited.
        assertTrue(walker.hasVisited(r1));
        assertTrue(walker.hasVisited(r2));
        assertTrue(walker.hasVisited(r3));
        assertTrue(walker.hasVisited(r4));
        assertTrue(walker.hasVisited(r5));
        assertTrue(walker.hasVisited(r6));
        assertFalse(walker.hasVisited(r7));
        // Check the coordinate
        assertEquals(-1, walker.xMin);
        assertEquals(1, walker.xMax);
        assertEquals(0, walker.yMin);
        assertEquals(1, walker.yMax);
    }

    @Test
    public void reset() {
        // Check the status after walking before resetting.
        walker.walk();
        assertEquals(6, walker.coords.size());
        assertTrue(walker.hasVisited(r1));
        assertTrue(walker.hasVisited(r2));
        assertTrue(walker.hasVisited(r3));
        assertTrue(walker.hasVisited(r4));
        assertTrue(walker.hasVisited(r5));
        assertTrue(walker.hasVisited(r6));
        assertFalse(walker.hasVisited(r7));
        assertEquals(-1, walker.xMin);
        assertEquals(1, walker.xMax);
        assertEquals(0, walker.yMin);
        assertEquals(1, walker.yMax);
        walker.reset();
        // After resetting, it should go back to initial status.
        assertEquals(0, walker.coords.size());
        assertFalse(walker.hasVisited(r1));
        assertFalse(walker.hasVisited(r2));
        assertFalse(walker.hasVisited(r3));
        assertFalse(walker.hasVisited(r4));
        assertFalse(walker.hasVisited(r5));
        assertFalse(walker.hasVisited(r6));
        assertFalse(walker.hasVisited(r7));
        assertEquals(0, walker.xMin);
        assertEquals(0, walker.xMax);
        assertEquals(0, walker.yMin);
        assertEquals(0, walker.yMax);
    }
}