/**
 * Player type which can dig new rooms and avoid fights.
 * Note: instances of this class are linked to a particular start room.
 *
 * @author JF
 * @serial exclude
 */
public class Builder extends Player {

    // Start room for this map
    private Room root;

    /**
     * Base constructor for Builder.
     *
     * @param shortDescription Short name for this builder
     * @param longDescription  Longer description for this builder
     * @param root      Start room for this map
     */
    public Builder(String shortDescription, String longDescription, Room root) {
        super(shortDescription, longDescription);
        this.root = root;
    }

    /**
    * @return 1000
    * @inheritDoc
    */
    @Override
    public int getDamage() {
        return 1000;
    }

    /**
     * Attempt to damage this Mob. Note: for this type, it will be ignored
     *
     * @param amount amount of damage 
     */
    @Override
    public void takeDamage(int amount) {
    }

    /**
     * Get encoded representation.
     * @return B;S;L
     *          (where S=raw short description, L=raw long description.)
     *          (eg "B;robert;There were ** chars but they were replaced")
     */
    @Override
    public String repr() {
        return "B;" + getShort() + ";" + getLong();
    }

    /**
     * Create Builder from a String and Room root.
     * @param encoded String to decode.
     * @param root Start room for this map.
     * @return decoded Builder if decodes successfully;
     *          null if fails.
     *          Failures include: null parameters, empty input or
     *          improperly encoded input.
     */
    public static Builder decode(String encoded, Room root) {
        Builder builder = null;
        if (encoded != null) {
            if (!encoded.equals("")) {
                if (root != null) {
                    String[] info = encoded.split(";");
                    if (info.length == 3) {
                        if (info[0].equals("B")) {
                            builder = new Builder(info[1], info[2], root);
                        }
                    }
                }
            }
        }
        return builder;
    }

}
