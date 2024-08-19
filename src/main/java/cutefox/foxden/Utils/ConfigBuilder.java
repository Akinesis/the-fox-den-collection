package cutefox.foxden.Utils;

import java.io.File;

import app.ccls.yml.YamlHandler;
import app.ccls.yml.YamlHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigBuilder {

    public static Map<String, Boolean> globalConfig;


    public static void createConfigFile(){

        YamlHandler yamlHandler = YamlHandlerFactory.getHandler("nested");

        try {
            File configFolder = new File(FabricLoader.getInstance().getConfigDir().resolve("Fox_Den_Collection").toString());
            configFolder.mkdirs();

            File configFile = new File(configFolder.getAbsolutePath() + "/global_config.yml");
            if (configFile.createNewFile()) {
                //init default file
                yamlHandler.writeYaml(configFile.getPath(), (Map)FoxDenDefaultConfig.defaultConfig);

                //globalConfig = new HashMap<>(FoxDenDefaultConfig.defaultConfig);
                globalConfig = FoxDenDefaultConfig.defaultConfig.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

            }else{
                Map<String, Object> loadedData = yamlHandler.readYaml(configFile.getPath());
                //globalConfig = new HashMap<>(loadedData);
                globalConfig = new HashMap<>();
                loadedData.entrySet().forEach(c -> {
                    globalConfig.put(c.getKey(), Boolean.parseBoolean((String) c.getValue()));
                });
            }
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

}
