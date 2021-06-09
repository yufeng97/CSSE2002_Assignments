import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    private Pair zero;
    private Pair one;
    private Pair two;
    private Pair three;

    @Before
    public void Setup() {
        zero = new Pair(0, 0);
        one = new Pair(0,1);
        two = new Pair(0, 1);
        three = new Pair(1, 0);
    }

    @Test
    public void equals() {
        System.out.println(one.equals(two));
        System.out.println(one.equals(three));
    }

    @Test
    public void hashCodeTest() {
        System.out.println(zero.hashCode());
        System.out.println(one.hashCode());
        System.out.println(two.hashCode());
        System.out.println(three.hashCode());
    }
}