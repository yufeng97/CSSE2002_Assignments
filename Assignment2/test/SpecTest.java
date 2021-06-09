import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 PLEASE NOTE:

 THESE TESTS GUARANTEE NO MARKS.
 THESE TESTS ARE NOT GUARANTEED TO BE CORRECT.
 THESE TESTS DO NOT FOLLOW STYLE GUIDE.

 USE AT YOUR OWN RISK.

 NO STAFF MEMBER WILL BE RESPONSIBLE FOR ANYTHING RELATED TO THIS FILE.
 NO CLARIFICATIONS WILL BE POSTED ABOUT THIS FILE.
 NO UPDATES WILL BE MADE.


 USAGE:

 Place this in your test folder and run this test. You will need to have made
 every file for each test to run. Good luck.

 */
public class SpecTest {

    @Test
    public void testBoundsMapper() throws NoSuchMethodException, NoSuchFieldException {
        Class<BoundsMapper> c = BoundsMapper.class;

        // Constructors
        Constructor<BoundsMapper> con = c.getDeclaredConstructor(Room.class);
        assertEquals("public BoundsMapper(Room)", con.toString() );

        // Extends
        assertEquals(c.getSuperclass(), MapWalker.class);

        // methods
        Method m = c.getDeclaredMethod("reset");
        assertEquals("public void BoundsMapper.reset()", m.toString());
        m = c.getDeclaredMethod("visit", Room.class);
        assertEquals("protected void BoundsMapper.visit(Room)", m.toString());

        // Fields
        Field f = c.getField("coords");
        assertEquals("public java.util.Map BoundsMapper.coords", f.toString());
        f = c.getField("xMax");
        assertEquals("public int BoundsMapper.xMax", f.toString());
        f = c.getField("xMin");
        assertEquals("public int BoundsMapper.xMin", f.toString());
        f = c.getField("yMax");
        assertEquals("public int BoundsMapper.yMax", f.toString());
        f = c.getField("yMin");
        assertEquals("public int BoundsMapper.yMin", f.toString());
    }

    @Test
    public void testBuilder() throws NoSuchMethodException {
        Class<Builder> c = Builder.class;

        // Constructors
        Constructor con = c.getDeclaredConstructor(String.class, String.class, Room.class);
        assertEquals("public Builder(java.lang.String,java.lang.String,Room)", con.toString());

        // Extends
        assertEquals(c.getSuperclass(), Player.class);

        // methods
        Method m = c.getDeclaredMethod("getDamage");
        assertEquals("public int Builder.getDamage()", m.toString());
        m = c.getDeclaredMethod("takeDamage", int.class);
        assertEquals("public void Builder.takeDamage(int)", m.toString());
        m = c.getDeclaredMethod("repr");
        assertEquals("public java.lang.String Builder.repr()", m.toString());
        m = c.getDeclaredMethod("decode", String.class, Room.class);
        assertEquals("public static Builder Builder.decode(java.lang.String,Room)", m.toString());
    }

    @Test
    public void testCrawlException() {
        Class c = CrawlException.class;
        assertEquals(Exception.class, c.getSuperclass());
    }

    @Test
    public void testCritter() throws NoSuchMethodException {
        Class<Critter> c = Critter.class;

        // Constructors
        Constructor<Critter> con = c.getDeclaredConstructor(String.class, String.class, double.class, int.class);
        assertEquals("public Critter(java.lang.String,java.lang.String,double,int)", con.toString());

        // Extends
        assertEquals(Thing.class, c.getSuperclass());

        // Implements
        assertTrue(Arrays.asList(c.getInterfaces()).contains(Mob.class));
        assertTrue(Arrays.asList(c.getInterfaces()).contains(Lootable.class));

        // methods
        Method m = c.getDeclaredMethod("getDescription");
        assertEquals("public java.lang.String Critter.getDescription()", m.toString());
        m = c.getDeclaredMethod("takeDamage", int.class);
        assertEquals("public void Critter.takeDamage(int)", m.toString());
        m = c.getDeclaredMethod("getDamage");
        assertEquals("public int Critter.getDamage()", m.toString());
        m = c.getDeclaredMethod("getValue");
        assertEquals("public double Critter.getValue()", m.toString());
        m = c.getDeclaredMethod("canLoot", Thing.class);
        assertEquals("public boolean Critter.canLoot(Thing)", m.toString());
        m = c.getDeclaredMethod("fight", Mob.class);
        assertEquals("public void Critter.fight(Mob)", m.toString());
        m = c.getDeclaredMethod("wantsToFight", Mob.class);
        assertEquals("public boolean Critter.wantsToFight(Mob)", m.toString());
        m = c.getDeclaredMethod("isAlive");
        assertEquals("public boolean Critter.isAlive()", m.toString());
        m = c.getDeclaredMethod("setAlive", boolean.class);
        assertEquals("public void Critter.setAlive(boolean)", m.toString());
        m = c.getDeclaredMethod("getHealth");
        assertEquals("public int Critter.getHealth()", m.toString());
        m = c.getDeclaredMethod("repr");
        assertEquals("public java.lang.String Critter.repr()", m.toString());
        m = c.getDeclaredMethod("decode", String.class);
        assertEquals("public static Critter Critter.decode(java.lang.String)", m.toString());
    }

    @Test
    public void testExitExistsException() {
        Class c = ExitExistsException.class;
        assertEquals(CrawlException.class, c.getSuperclass());
    }

    @Test
    public void testExplorer() throws NoSuchMethodException {
        Class<Explorer> c = Explorer.class;

        // Constructors
        Constructor<Explorer> con = c.getDeclaredConstructor(Player.class);
        assertEquals("public Explorer(Player)", con.toString());
        con = c.getDeclaredConstructor(String.class, String.class);
        assertEquals("public Explorer(java.lang.String,java.lang.String)", con.toString());
        con = c.getDeclaredConstructor(String.class, String.class, int.class);
        assertEquals("public Explorer(java.lang.String,java.lang.String,int)", con.toString());

        // Extends
        assertEquals(c.getSuperclass(), Player.class);

        // methods
        Method m = c.getDeclaredMethod("getDescription");
        assertEquals("public java.lang.String Explorer.getDescription()", m.toString());
        m = c.getDeclaredMethod("getDamage");
        assertEquals("public int Explorer.getDamage()", m.toString());
        m = c.getDeclaredMethod("repr");
        assertEquals("public java.lang.String Explorer.repr()", m.toString());
        m = c.getDeclaredMethod("decode", String.class);
        assertEquals("public static Explorer Explorer.decode(java.lang.String)", m.toString());
    }

    @Test
    public void testLootable() throws NoSuchMethodException {
        Class<Lootable> c = Lootable.class;

        // methods
        Method m = c.getDeclaredMethod("getValue");
        assertEquals("public abstract double Lootable.getValue()", m.toString());
        m = c.getDeclaredMethod("canLoot", Thing.class);
        assertEquals("public abstract boolean Lootable.canLoot(Thing)", m.toString());
    }

    @Test
    public void testMapIO() throws NoSuchMethodException {
        Class<MapIO> c = MapIO.class;

        // Constructors
        Constructor<MapIO> con = c.getDeclaredConstructor();
        assertEquals(con.toString(), "public MapIO()" );

        // methods
        Method m = c.getDeclaredMethod("serializeMap", Room.class, String.class);
        assertEquals("public static boolean MapIO.serializeMap(Room,java.lang.String)", m.toString());
        m = c.getDeclaredMethod("deserializeMap", String.class);
        assertEquals("public static Room MapIO.deserializeMap(java.lang.String)", m.toString());
        m = c.getDeclaredMethod("saveMap", Room.class, String.class);
        assertEquals("public static boolean MapIO.saveMap(Room,java.lang.String)", m.toString());
        m = c.getDeclaredMethod("decodeThing", String.class, Room.class);
        assertEquals("public static Thing MapIO.decodeThing(java.lang.String,Room)", m.toString());
        m = c.getDeclaredMethod("loadMap", String.class);
        assertEquals("public static java.lang.Object[] MapIO.loadMap(java.lang.String)", m.toString());
    }

    @Test
    public void testMapWalker() throws NoSuchMethodException {
        Class<MapWalker> c = MapWalker.class;

        // Constructors
        Constructor<MapWalker> con = c.getDeclaredConstructor(Room.class);
        assertEquals("public MapWalker(Room)", con.toString());

        // methods
        Method m = c.getDeclaredMethod("reset");
        assertEquals("protected void MapWalker.reset()", m.toString());
        m = c.getDeclaredMethod("walk");
        assertEquals("public void MapWalker.walk()", m.toString());
        m = c.getDeclaredMethod("hasVisited", Room.class);
        assertEquals("public boolean MapWalker.hasVisited(Room)", m.toString());
        m = c.getDeclaredMethod("visit", Room.class);
        assertEquals("protected void MapWalker.visit(Room)", m.toString());
    }

    @Test
    public void testMob() throws NoSuchMethodException {
        Class<Mob> c = Mob.class;

        // Methods
        Method m = c.getMethod("fight", Mob.class);
        assertEquals("public abstract void Mob.fight(Mob)", m.toString());
        m = c.getMethod("getDamage");
        assertEquals("public abstract int Mob.getDamage()", m.toString());
        m = c.getMethod("isAlive");
        assertEquals("public abstract boolean Mob.isAlive()", m.toString());
        m = c.getMethod("setAlive", boolean.class);
        assertEquals("public abstract void Mob.setAlive(boolean)", m.toString());
        m = c.getMethod("takeDamage", int.class);
        assertEquals("public abstract void Mob.takeDamage(int)", m.toString());
        m = c.getMethod("wantsToFight", Mob.class);
        assertEquals("public abstract boolean Mob.wantsToFight(Mob)", m.toString());
    }


    @Test
    public void testNullRoomException() {
        Class<NullRoomException> c = NullRoomException.class;
        assertEquals(c.getSuperclass(), CrawlException.class);
    }

    @Test
    public void testPair() throws Exception {
        Class<Pair> p = Pair.class;

        //constructor
        Constructor<Pair> con = p.getConstructor(int.class, int.class);
        assertEquals("public Pair(int,int)", con.toString());

        // fields
        Field f = p.getField("x");
        assertEquals("public int Pair.x", f.toString());
        f = p.getField("y");
        assertEquals("public int Pair.y", f.toString());

        // methods
        Method m = p.getDeclaredMethod("equals", Object.class);
        assertEquals("public boolean Pair.equals(java.lang.Object)", m.toString());

        m = p.getDeclaredMethod("hashCode");
        assertEquals("public int Pair.hashCode()", m.toString());

    }

    @Test
    public void testPlayer() throws Exception {
        Class<Player> p = Player.class;

        // Extends
        assertEquals(p.getSuperclass(), Thing.class);

        // interfaces
        assertTrue(Arrays.asList(p.getInterfaces()).contains(Mob.class));

        // constructor
        Constructor<Player> con = p.getConstructor(String.class, String.class);
        assertEquals("public Player(java.lang.String,java.lang.String)", con.toString());

        con = p.getConstructor(String.class, String.class, int.class);
        assertEquals("public Player(java.lang.String,java.lang.String,int)", con.toString());

        // methods
        Method m = p.getDeclaredMethod("add", Thing.class);
        assertEquals("public void Player.add(Thing)", m.toString());

        m  = p.getDeclaredMethod("drop", Thing.class);
        assertEquals("public void Player.drop(Thing)", m.toString());

        m = p.getDeclaredMethod("drop", String.class);
        assertEquals("public Thing Player.drop(java.lang.String)", m.toString());

        m = p.getDeclaredMethod("fight", Mob.class);
        assertEquals("public void Player.fight(Mob)", m.toString());

        m = p.getDeclaredMethod("getContents");
        assertEquals("public java.util.List Player.getContents()", m.toString());

        m = p.getDeclaredMethod("getHealth");
        assertEquals("public int Player.getHealth()", m.toString());

        m = p.getDeclaredMethod("isAlive");
        assertEquals("public boolean Player.isAlive()", m.toString());

        m = p.getDeclaredMethod("setAlive", boolean.class);
        assertEquals("public void Player.setAlive(boolean)", m.toString());

        m = p.getDeclaredMethod("takeDamage", int.class);
        assertEquals("public void Player.takeDamage(int)", m.toString());

        m = p.getDeclaredMethod("wantsToFight", Mob.class);
        assertEquals("public boolean Player.wantsToFight(Mob)", m.toString());
    }

    @Test
    public void testRoom() throws Exception {
        Class<Room> r = Room.class;

        // constructor
        Constructor<Room> c = r.getConstructor(String.class);
        assertEquals("public Room(java.lang.String)", c.toString());

        // methods
        Method m = r.getDeclaredMethod("addExit", String.class, Room.class);
        //exception
        assertTrue(Arrays.asList(m.getExceptionTypes()).contains(ExitExistsException.class));
        assertTrue(Arrays.asList(m.getExceptionTypes()).contains(NullRoomException.class));
        // modifier and return type
        assertTrue(Modifier.isPublic(m.getModifiers()));
        assertTrue(Arrays.asList(m.getReturnType(), new Class[]{}).contains(void.class));

        m = r.getDeclaredMethod("enter", Thing.class);
        assertEquals("public void Room.enter(Thing)", m.toString());

        m = r.getDeclaredMethod("getContents");
        assertEquals("public java.util.List Room.getContents()", m.toString());

        m = r.getDeclaredMethod("getDescription");
        assertEquals("public java.lang.String Room.getDescription()", m.toString());

        m = r.getDeclaredMethod("getExits");
        assertEquals("public java.util.Map Room.getExits()", m.toString());

        m = r.getDeclaredMethod("leave", Thing.class);
        assertEquals("public boolean Room.leave(Thing)", m.toString());

        m = r.getDeclaredMethod("makeExitPair", Room.class, Room.class, String.class, String.class);
        assertTrue(Arrays.asList(m.getExceptionTypes()).contains(ExitExistsException.class));
        assertTrue(Arrays.asList(m.getExceptionTypes()).contains(NullRoomException.class));
        assertTrue(Modifier.isStatic(m.getModifiers()));
        // this is probably unnecessary :P
        assertTrue(Arrays.asList(m.getReturnType(), new Class[]{}).contains(void.class));
        assertTrue(Arrays.asList(m.getParameterTypes()).contains(Room.class));
        assertTrue(Arrays.asList(m.getParameterTypes()).contains(String.class));
        assertEquals(4, m.getParameterCount());

        m = r.getDeclaredMethod("removeExit", String.class);
        assertEquals("public void Room.removeExit(java.lang.String)", m.toString());

        m = r.getDeclaredMethod("setDescription", String.class);
        assertEquals("public void Room.setDescription(java.lang.String)", m.toString());
    }

    @Test
    public void testThing() throws Exception {
        Class<Thing> t = Thing.class;

        // is abstract
        assertTrue(Modifier.isAbstract(t.getModifiers()));

        // constructor
        Constructor<Thing> c = t.getConstructor(String.class, String.class);
        assertEquals("public Thing(java.lang.String,java.lang.String)", c.toString());

        // methods
        Method m = t.getDeclaredMethod("getDescription");
        assertEquals("public java.lang.String Thing.getDescription()", m.toString());
        m = t.getDeclaredMethod("getLong");
        assertEquals("protected java.lang.String Thing.getLong()", m.toString());

        m = t.getDeclaredMethod("getShort");
        assertEquals("protected java.lang.String Thing.getShort()", m.toString());

        m = t.getDeclaredMethod("getShortDescription");
        assertEquals("public java.lang.String Thing.getShortDescription()", m.toString());

        m = t.getDeclaredMethod("repr");
        assertEquals("public abstract java.lang.String Thing.repr()", m.toString());

        m = t.getDeclaredMethod("setLong", String.class);
        assertEquals("protected void Thing.setLong(java.lang.String)", m.toString());

        m = t.getDeclaredMethod("setShort", String.class);
        assertEquals("protected void Thing.setShort(java.lang.String)", m.toString());
    }

    @Test
    public void testTreasure() throws Exception {
        Class<Treasure> t = Treasure.class;

        // Extends
        assertEquals(t.getSuperclass(), Thing.class);

        // Implements
        assertTrue(Arrays.asList(t.getInterfaces()).contains(Lootable.class));

        // constructors
        Constructor<Treasure> c = t.getConstructor(String.class, double.class);
        assertEquals("public Treasure(java.lang.String,double)", c.toString());

        // methods
        Method m = t.getDeclaredMethod("canLoot", Thing.class);
        assertEquals(m.toString(), "public boolean Treasure.canLoot(Thing)");
        m = t.getDeclaredMethod("decode", String.class);
        assertEquals(m.toString(), "public static Treasure Treasure.decode(java.lang.String)");
        m = t.getDeclaredMethod("getValue");
        assertEquals(m.toString(), "public double Treasure.getValue()");
        m = t.getDeclaredMethod("repr");
        assertEquals(m.toString(), "public java.lang.String Treasure.repr()");
    }
}