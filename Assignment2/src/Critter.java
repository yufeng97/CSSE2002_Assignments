import sun.util.resources.cldr.zh.CalendarData_zh_Hans_CN;

/**
 * A non-player Lootable Mob.
 *
 * @author JF
 * @serial exclude
 */
public class Critter extends Thing implements Lootable, Mob {
    // What is the value of this Mob once looted?
    private double value;
    // Current health of this Mob.
    private int health;
    // Default health (used if alive is set back to true).
    private static final int MAX_HEALTH = 10;

    /**
     * Critter - A non-player Lootable Mob.
     * @param shortDescription Name or short description for this Mob
     * @param longDescription  Longer description for this Mob
     * @param value     Worth of Mob when looted
     * @param health    Starting health of Mob
     */
    public Critter(String shortDescription, String longDescription,
                   double value, int health) {
        super(shortDescription, longDescription);
        this.value = value;
        this.health = health;
    }

    /**
     * Long desctription of Mob.
     *
     * @return Long description, immediately followed by "(fainted)" iff the
     *         critter is not alive.
     */
    @Override
    public String getDescription() {
        return isAlive() ? getLong() : getLong() + "(fainted)";
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    /**
     * @return 2
     * @inheritDoc
     */
    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public double getValue() {
        return (health > 0) ? 0 : value;
    }

    /**
     * @return true iff looter is a Player and your health is zero
     * @inheritDoc
     */
    @Override
    public boolean canLoot(Thing looter) {
        return (looter instanceof Player) && (health == 0);
    }

    /**
     * @inheritDoc
     * @see Mob#fight(Mob)
     */
    @Override
    public void fight(Mob mob) {
        while (this.isAlive() && mob.isAlive()) {
            int damage = getDamage();
            mob.takeDamage(damage);
            if (mob.isAlive()) {
                damage = mob.getDamage();
                this.takeDamage(damage);
            }
        }
    }

    /**
     * Returns whether the mob is an Explorer.
     * @return true if mob is an Explorer
     */
    @Override
    public boolean wantsToFight(Mob mob) {
        return mob instanceof Explorer;
    }

    /**
     * Is the current Mob health above zero?
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * If true, set health to MAX_HEALTH.
     * @see Critter#Critter(String, String, double, int)
     */
    @Override
    public void setAlive(boolean alive) {
        health = (alive ? MAX_HEALTH : 0);
    }

    /**
     * Returns the current health.
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get encoded representation.
     * Note: when representing doubles, use String.format("%.5f", value)
     * @return C;V;H;S;L
     *          (where V=value, H=health, S=raw short description,
     *          L=raw long description)
     *          (eg "C;14.50000;2;cat;a cat")
     */
    @Override
    public String repr() {
        return "C;" + String.format("%.5f", value) + ";" + getHealth() + ";" +
                getShort() + ";" + getLong();
    }

    /**
     * Create Critter from a String.
     * @param encoded String to decode.
     * @return decoded Critter if decodes successfully;
     *          null if fails.
     *          Failures include: null parameters, empty input or
     *          improperly encoded input.
     */
    public static Critter decode(String encoded) {
        Critter critter = null;
        if (encoded != null) {
            if (!encoded.equals("")) {
                String[] info = encoded.split(";");
                if (info.length == 5) {
                    if (info[0].equals("C")) {
                        if (strIsDouble(info[1]) &&
                                (info[1].indexOf('.') == info[1].length() - 6)) {
                            try {
                                int health = Integer.parseInt(info[2]);
                                critter = new Critter(info[3], info[4],
                                        Double.parseDouble(info[1]), health);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }
        return critter;
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
