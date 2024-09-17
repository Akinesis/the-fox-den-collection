package cutefox.foxden.Utils;

import net.minecraft.util.Identifier;

import java.util.List;

public class Utils {

    public static final List<String> RANDOM_NAMES = List.of(
            "Gaston Mike Willson Henri Salade",
            "Connor",
            "Némésis",
            "Panache",
            "Julie",
            "Rocky",
            "Skye",
            "Chase (acab)",
            "Marshall",
            "Rubble",
            "Zuma",
            "Evrest",
            "Tracker"
            );

    public static Identifier id(String path) {
        return Identifier.of("fox_den", path);
    }

    public static Identifier blockId(String path) {
        return Identifier.of("fox_den", "block/"+path);
    }

    public static Identifier id() {
        return Identifier.of("fox_den");
    }
}
