import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapWalkerTest {

    private Room a;
    private Room b;
    private Room c;
    private Room d;

    private MapWalker walker;

    @Before
    public void Setup() throws ExitExistsException, NullRoomException {

        a = new Room("a");
        b = new Room("b");
        c = new Room("c");
        d = new Room("d");

        a.addExit("East", b);
        b.addExit("West", a);
        b.addExit("North", c);
        b.addExit("South", d);
        c.addExit("South", b);
        d.addExit("North", b);

        walker = new MapWalker(a);
    }

    @Test
    public void reset() {
    }

    @Test
    public void walk() {
        walker.walk();
    }

    @Test
    public void hasVisited() {
        walker.walk();
        System.out.println(walker.hasVisited(a));
        System.out.println(walker.hasVisited(b));
        System.out.println(walker.hasVisited(c));
        System.out.println(walker.hasVisited(d));
        walker.reset();
        System.out.println(walker.hasVisited(a));
    }

}