package cutefox.foxden.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cutefox.foxden.datagen.recipeTypes.CookingPotRecipe;
import net.minecraft.item.Item;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FarmerDelightRecipeBuilder {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATA_RECIPE_FOLDER = "F:/Minecraft Fabric Mods/the-fox-den-collection/src/main/resources/data/fox_den/recipe/";
    private static final String CUTTING_BOARD_FOLDER = "cutting_board";
    private static final String COOKING_POT_FOLDER = "cooking";

    public static void resetFolders(){
        try {
            File targetFolder = new File(DATA_RECIPE_FOLDER + CUTTING_BOARD_FOLDER);
            FileUtils.deleteDirectory(targetFolder);
            targetFolder.mkdirs();

            targetFolder = new File(DATA_RECIPE_FOLDER + COOKING_POT_FOLDER);
            FileUtils.deleteDirectory(targetFolder);
            targetFolder.mkdirs();


        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    public static CookingPotRecipe createCookingPotRecipe(Item output){
        CookingPotRecipe cookingPotRecipe = new CookingPotRecipe(output);
        return cookingPotRecipe;
    }

    public static CookingPotRecipe createCookingPotRecipe(Item output,int outputCount){
        CookingPotRecipe cookingPotRecipe = new CookingPotRecipe(output,outputCount);
        return cookingPotRecipe;
    }

    public static void buildRecipe(CookingPotRecipe recipe){

        File targetFolder = new File(DATA_RECIPE_FOLDER + COOKING_POT_FOLDER);
        buildRecipe(targetFolder, "_cooking_pot", recipe);

    }

    private static void buildRecipe(File targetFolder, String craftingType,CookingPotRecipe recipe){
        try {

            String recipeName = recipe.getResult().getId().split(":")[1];
            File recipeFile = new File(targetFolder+"/"+ recipeName+craftingType+".json");

            if (recipeFile.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(recipeFile));

                String jsonString = gson.toJson(recipe);
                writer.write(jsonString);
                writer.close();
            }
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

}
