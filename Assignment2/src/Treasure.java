/**
 * Lootable object which doesn't fight.
 *
 * @author JF
 * @serial exclude
 */
public class Treasure extends Thing implements Lootable {
    // Worth of this treasure.
    private double value;

    /**
     * Make a treasure.
     * Note: Character replacement rules from Thing apply
     * @param shortDescription short name for this item. (This will also be
     *                         used as the long description)
     * @param value worth of this item
     */
    public Treasure(String shortDescription, double value) {
        super(shortDescription, shortDescription);
        this.value = value;
    }

    /**
     * Gets the worth of the item.
     * @return the worth of this item
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Can the looter loot me.
     * @return true if looter is an instance of Player; else false
     * @inheritDoc
     */
    @Override
    public boolean canLoot(Thing looter) {
        return looter instanceof Player;
    }

    /**
     * Get encoded representation.
     * Note: when representing doubles, use String.format("%.5f", value)
     * @return $;V;S
     *          where V=value, S=raw short description
     *          (eg "$;14.50000;box")
     */
    @Override
    public String repr() {
        return "$;" + String.format("%.5f", getValue()) + ";" + getShort();
    }

    /**
     * Create Treasure from a String.
     * @param encoded String to decode.
     * @return decoded Treasure if decodes successfully;
     *          null if fails.
     *          Failures include: null parameters, empty input or
     *          improperly encoded input.
     */
    public static Treasure decode(String encoded) {
        Treasure treasure = null;
        String[] info = encoded.split(";");
        if (!encoded.equals("")) {
            if (info.length == 3) {
                if (info[0].equals("$")) {
                    if (strIsDouble(info[1]) && (info[1].indexOf('.') == info[1].length() - 6)) {
                        treasure = new Treasure(info[2], Double.parseDouble(info[1]));
                    }
                }
            }
        }
        return treasure;
    }

    /*
     * A method to check whether the String given can convert to Double.
     * return true, if String can convert;
     * else false, if String can not convert.
     */
    private static boolean strIsDouble(String s) {
        if (s.isEmpty() || (s.charAt(0) != '-' && !Character.isDigit(s.charAt(0)))) {
            return false;
        }
        boolean foundADecimal = false;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!foundADecimal && c == '.') {
                foundADecimal = true;
                continue;
            }
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
