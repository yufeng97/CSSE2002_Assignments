/**
 * A Player who doesn't modify the map.
 *
 * @author JF
 * @serial exclude
 */
public class Explorer extends Player {
    /**
     * Copy details (but not inventory) from another Player.
     *
     * @param player Player to copy from
     */
    public Explorer(Player player) {
        super(player.getShort(), player.getLong(), player.getHealth());
    }

    /**
     * Create an Explorer with default health.
     * @param shortDescription A short name or description for the Explorer
     * @param longDescription A more detailed description for the Explorer
     */
    public Explorer(String shortDescription, String longDescription) {
        super(shortDescription, longDescription);
    }

    /**
     * Create an Explorer with a set health.
     * @param shortDescription A short name or description for the Explorer
     * @param longDescription A more detailed description for the Explorer
     * @param health Starting health for the Explorer.
     */
    public Explorer(String shortDescription, String longDescription, 
            int health) {
        super(shortDescription, longDescription, health);
    }

    @Override
    public String getDescription() {
        return getLong() + (isAlive()
                ? " with " + getHealth() + " health" : "(fainted)");
    }

    /**
    * @return 1
    * @inheritDoc
    */
    @Override
    public int getDamage() {
        return 1;
    }

    /**
     * Get encoded representation.
     * @return E;H;S;L
     *          (where H=health, S=raw short description,
     *          L=raw long description)
     *          (eg "E;2;doris;There were ** chars but they were replaced")
     */
    @Override
    public String repr() {
        return "E;" + getHealth() + ";" + getShort() + ";" + getLong();
    }

    /**
     * Create an Explorer from a .
     * @param encoded String to decode
     * @return decoded Explorer if decodes successfully;
     *          null if fails.
     *          Failures include: null parameters, empty input or
     *          improperly encoded input.
     */
    public static Explorer decode(String encoded) {
        Explorer explorer = null;
        if (encoded != null) {
            if (!encoded.equals("")) {
                String[] info = encoded.split(";");
                if (info.length == 4) {
                    if (info[0].equals("E")) {
                        try {
                            int health = Integer.parseInt(info[1]);
                            explorer = new Explorer(info[2], info[3], health);
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }
        return explorer;
    }
}
