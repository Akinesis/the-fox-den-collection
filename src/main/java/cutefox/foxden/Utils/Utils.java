package cutefox.foxden.Utils;

import net.minecraft.util.Identifier;

public class Utils {

    public static Identifier id(String path) {
        return Identifier.of("fox_den", path);
    }

    public static Identifier id() {
        return Identifier.of("fox_den");
    }
}
