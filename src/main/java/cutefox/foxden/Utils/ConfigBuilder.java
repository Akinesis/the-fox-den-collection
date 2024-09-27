package cutefox.foxden.Utils;

import java.io.File;

import org.simpleyaml.configuration.file.YamlFile;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigBuilder {

    public static YamlFile yamlConfig;


    public static void createConfigFile(){


        try {
            File configFolder = new File(FabricLoader.getInstance().getConfigDir().resolve("Fox_Den_Collection").toString());
            configFolder.mkdirs();

            File configFile = new File(configFolder.getAbsolutePath() + "/global_config.yml");
            yamlConfig = new YamlFile(configFile);
            if (!yamlConfig.exists()) {
                //init default file
                yamlConfig.createNewFile();

            }

            yamlConfig.load();

            FoxDenDefaultConfig.defaultConfig.entrySet().stream().forEach(
                    entry -> {
                        yamlConfig.addDefault(entry.getKey(), entry.getValue());
                    });

            yamlConfig.save();

        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    public static boolean poutine(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.POUTINE);
    }

    public static boolean upgradeTemplate(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.UPGRADE_TEMPLATE);
    }

    public static boolean knightArmor(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.KNIGHT_ARMOR);
    }

    public static boolean boneArmor(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.BONE_ARMOR);
    }

    public static boolean spaceArmor(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.SPACE_ARMOR);
    }

    public static boolean leavesWall(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.LEAVES_WALL);
    }

    public static boolean randomMobSize(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.RANDOM_MOB_SIZE);
    }

    public static boolean randomNameTag(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.RANDOM_NAME_TAG);
    }

    public static boolean bikeArmor(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.BIKE_ARMOR);
    }

    public static boolean wolfArmor(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.WOLF_ARMOR);
    }

    public static boolean moreAttributes(){
        return yamlConfig.getBoolean(FoxDenDefaultConfig.MORE_ATTRIBUTES);
    }

}
