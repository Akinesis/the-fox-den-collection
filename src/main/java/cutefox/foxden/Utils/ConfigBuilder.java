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

}
