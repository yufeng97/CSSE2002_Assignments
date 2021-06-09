import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Static routines to save and load Rooms.
 */
public class MapIO {

    public MapIO() {}

    /**
     * Write rooms to a new file (using Java serialisation)
     * @param root Start room to explore from.
     * @param filename Filename to write to.
     * @return true, if successful;
     *          else false, if not successful.
     */
    public static boolean serializeMap(Room root, String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(root);
            output.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Read serialised Rooms from a file.
     * @param filename Filename to read Rooms from.
     * @return Start Room, if successful;
     *          else, null on failure
     */
    public static Room deserializeMap(String filename) {
        Room room = null;
        if (filename != null) {
            try {
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream input = new ObjectInputStream(file);
                try {
                    room = (Room) input.readObject();
                } catch (ClassNotFoundException ignored) {
                } finally {
                    try {
                        input.close();
                    } catch (IOException ignored) {}
                }
            } catch (IOException ignored) {}
        }
        return room;
    }

    /**
     * Write Rooms to a new file (using encoded String form).
     * Details:
     * The structure of a file is:
     * 1. The number of rooms in the file
     * 2. Room descriptions
     * 3. Room exits
     * 4. Room contents
     * 5. Rooms are always listed in the same order with the start room first.
     * Note that this format does not preserver player inventory.
     * @param root Start room
     * @param filename Filename to write to
     * @return true, if successful;
     *          else false, if fail.
     */
    public static boolean saveMap(Room root, String filename) {
        if ((root != null) && filename != null) {
            List<Room> todo = new ArrayList<Room>();
            List<Room> allRooms = new ArrayList<Room>();
            todo.add(root);
            while (!todo.isEmpty()) {
                Room room = todo.remove(0);
                if (!allRooms.contains(room)) {
                    for (String key : room.getExits().keySet()) {
                        todo.add(room.getExits().get(key));
                    }
                    allRooms.add(room);
                }
            }
            try {
                PrintWriter pw = new PrintWriter(filename);
                // The number of rooms in the file
                pw.println(allRooms.size());
                // Room descriptions.
                for (Room room : allRooms) {
                    pw.println(room.getDescription());
                }
                // Room exits.
                for (Room room : allRooms) {
                    // The number of exits for each room.
                    pw.println(room.getExits().size());
                    // The index of room and exit's name.
                    for (String key : room.getExits().keySet()) {
                        pw.println(allRooms.indexOf(room.getExits().get(key)) + " " + key);
                    }
                }
                // Room contents.
                for (Room room : allRooms) {
                    // The number of items in room.
                    pw.println(room.getContents().size());
                    // Each item in encoded form.
                    for (Thing thing : room.getContents()) {
                        pw.println(thing.repr());
                    }
                }
                pw.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Decode a String into a Thing.
     * (Need to be able to decode, Treasure, Critter, Builder and Explorer)
     * @param encoded String to decode
     * @param root start room for the map
     * @return Decoded Thing, if successful
     *          else, null on failure.
     *          (null arguments or incorrectly encoded input)
     */
    public static Thing decodeThing(String encoded, Room root) {
        Thing thing = null;
        if (encoded != null) {
            if (!encoded.equals("")) {
                if (root != null) {
                    if (encoded.charAt(0) == 'B') {
                        thing = Builder.decode(encoded, root);
                    } else if (encoded.charAt(0) == 'C') {
                        thing = Critter.decode(encoded);
                    } else if (encoded.charAt(0) == 'E') {
                        thing = Explorer.decode(encoded);
                    } else if (encoded.charAt(0) == '$') {
                        thing = Treasure.decode(encoded);
                    }
                }
            }
        }
        return thing;
    }

    /**
     * Read information from a file created with saveMap.
     * @param filename Filename to read from.
     * @return An array of two Objects, if successful;
     *          else, null if unsuccessful.
     *          [0] being the Player object (if found) and
     *          [1] being the start room.
     */
    public static Object[] loadMap(String filename) {
        Object[] list = new Object[2];
        List<Room> roomList = new ArrayList<>();
        List<String> fileContents = new ArrayList<>();
        String[] data;
        String line;
        if (filename == null) {
            return null;
        } else {
            try {
                FileReader input = new FileReader(filename);
                BufferedReader br = new BufferedReader(input);
                // Add every line of file into fileContents List.
                while ((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
                data = new String[fileContents.size()];
                // Convert the fileContents List to an array.
                fileContents.toArray(data);
                input.close();
                br.close();

                // The number of rooms in the file.
                int totalRooms = Integer.parseInt(data[0]);

                // Add room into roomList.
                for (int i = 1; i <= totalRooms; i++) {
                    roomList.add(new Room(data[i]));
                }

                // The number of exits for each room.
                int exitsNumber;
                int totalExits = 0;
                for (int i = 1; i <= totalRooms; i++) {
                    try {
                        exitsNumber = Integer.parseInt(data[totalRooms + totalExits + i]);
                        // Add exits for each room.
                        for (int k = 1; k <= exitsNumber; k++) {
                            String[] exit = data[totalRooms + totalExits + i + k].split(" ");
                            roomList.get(i - 1).addExit(exit[1], roomList.get(Integer.parseInt(exit[0])));
                        }
                        totalExits += exitsNumber;
                    } catch (ExitExistsException | NullRoomException ignored) {
                    }
                }

                // Find the where the last line of exit is.
                int exitsEndRow = totalRooms * 2;
                for (Room r : roomList) {
                    exitsEndRow += r.getExits().size();
                }

                // The number of items for each room.
                int contentsNumber;
                int totalItems = 0;
                for (int i = 1; i <= totalRooms; i++) {
                    contentsNumber = Integer.parseInt(data[exitsEndRow + totalItems + i]);
                    // Add Things to each room.
                    for (int k = 1; k <= contentsNumber; k++) {
                        if (data[exitsEndRow + totalItems + i + k].charAt(0) == 'C') {
                            roomList.get(i - 1).enter(Critter.decode(data[exitsEndRow + totalItems + i + k]));
                        } else if (data[exitsEndRow + totalItems + i + k].charAt(0) == '$') {
                            roomList.get(i - 1).enter(Treasure.decode(data[exitsEndRow + totalItems + i + k]));
                        } else if (data[exitsEndRow + totalItems + i + k].charAt(0) == 'E') {
                            list[0] = Explorer.decode(data[exitsEndRow + totalItems + i + k]);
                        } else if (data[exitsEndRow + totalItems + i + k].charAt(0) == 'B') {
                            list[0] = Builder.decode(data[exitsEndRow + totalItems + i + k], roomList.get(0));
                        }
                    }
                    totalItems += contentsNumber;
                }
                //[1] being the start room.
                list[1] = roomList.get(0);
                return list;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
