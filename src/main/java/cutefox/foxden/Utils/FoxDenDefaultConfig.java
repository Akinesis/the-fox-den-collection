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
    public static final String LEAVES_WALL = "enable_leaves_wall";
    public static final String RESPAWN_DRAGON_EGG = "respawn_dragon_egg";
    public static final String RANDOM_MOB_SIZE = "random_mob_size";
    public static final String RANDOM_NAME_TAG = "random_name_tag";
    public static final String BIKE_ARMOR = "bike_armor";
    public static final String WOLF_ARMOR = "wolf_armor";
    public static final String MORE_ATTRIBUTES = "more_attributes";

    static {
        defaultConfig.put(POUTINE, true);
        defaultConfig.put(UPGRADE_TEMPLATE, true);
        defaultConfig.put(KNIGHT_ARMOR, true);
        defaultConfig.put(BONE_ARMOR, true);
        defaultConfig.put(SPACE_ARMOR, true);
        defaultConfig.put(LEAVES_WALL, true);
        defaultConfig.put(RESPAWN_DRAGON_EGG, true);
        defaultConfig.put(RANDOM_MOB_SIZE, true);
        defaultConfig.put(RANDOM_NAME_TAG, true);
        defaultConfig.put(BIKE_ARMOR, true);
        defaultConfig.put(WOLF_ARMOR, true);
        defaultConfig.put(MORE_ATTRIBUTES, true);
    }

}
