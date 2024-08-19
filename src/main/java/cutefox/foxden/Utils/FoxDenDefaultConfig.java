package cutefox.foxden.Utils;

import java.util.HashMap;
import java.util.Map;

public abstract class FoxDenDefaultConfig {

    public static Map<String, Boolean> defaultConfig = new HashMap<>();

    public static final String POUTINE = "enable_poutine";
    public static final String UPGRADE_TEMPLATE = "enable_upgrade_template";
    public static final String KNIGHT_ARMOR = "enable_knight_armor";
    public static final String BONE_ARMOR = "enable_bone_armor";
    public static final String SPACE_ARMOR = "enable_space_ranger_armor";

    static {
        defaultConfig.put(POUTINE, true);
        defaultConfig.put(UPGRADE_TEMPLATE, true);
        defaultConfig.put(KNIGHT_ARMOR, true);
        defaultConfig.put(BONE_ARMOR, true);
        defaultConfig.put(SPACE_ARMOR, true);
    }

}
