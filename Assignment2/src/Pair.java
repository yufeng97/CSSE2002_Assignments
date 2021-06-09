/**
 * Pair type is used to store the coordinate of each room.
 */
public class Pair {

    /* x coordinate */
    public int x;
    /* y coordinate */
    public int y;

    /**
     * Create the Pair with given coordinate.
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     */
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Check whether two pairs are equal.
     * For pairs p and q, does p.x equal q.x and p.y equal q.y?
     * @param o The object to query
     * @return true, if p.x == q.x && p.y == q.y
     *          else false.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            if (this.x == ((Pair)o).x && this.y == ((Pair)o).y) {
                return true;
            }
        } return false;
    }

    /**
     * Convert the Pair to hashcode.
     * @return a hash code value for the Pair.
     */
    @Override
    public int hashCode() {
        int result;
        int hash = 13;
        int hash2 = 17;
        result = hash * this.x + hash2 * this.y;
        return result;
    }
}

