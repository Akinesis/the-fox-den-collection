package cutefox.foxden.datagen.recipeTypes;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

public class CookingPotRecipe {

    private String type = "farmersdelight:cooking";
    private String recipe_book_tab = "misc";
    protected List<RecipeEntry> ingredients;
    private RecipeItemWithCount result;
    private RecipeItemWithCount container = null;
    private int cookingtime = 200;
    private float experience = 1.0f;

    public CookingPotRecipe(Item result){
        ingredients = new ArrayList<>();
        this.result = new RecipeItemWithCount(result,1);
    }

    public CookingPotRecipe(String result, int OutputCount){
        ingredients = new ArrayList<>();
        this.result = new RecipeItemWithCount(result,OutputCount);
        this.cookingtime = cookingtime;
    }

    public CookingPotRecipe(Item result, int OutputCount){
        ingredients = new ArrayList<>();
        this.result = new RecipeItemWithCount(result,OutputCount);
        this.cookingtime = cookingtime;
    }

    public CookingPotRecipe addIngredient(Item item){
        ingredients.add(new RecipeItemIngredient(item));
        return this;
    }

    public CookingPotRecipe addIngredientItem(String item){
        ingredients.add(new RecipeItemIngredient(item));
        return this;
    }

    public CookingPotRecipe addIngredient(TagKey<Item> tag){
        ingredients.add(new RecipeTagIngredient(tag));
        return this;
    }

    public CookingPotRecipe addIngredientTag(String tag){
        ingredients.add(new RecipeTagIngredient(tag));
        return this;
    }

    public RecipeItemWithCount getResult() {
        return result;
    }

    public CookingPotRecipe addContainer(Item container){
        this.container = new RecipeItemWithCount(container,1);
        return this;
    }

    public CookingPotRecipe addContainer(String container) {
        this.container = new RecipeItemWithCount(container,1);
        return this;
    }

    public CookingPotRecipe withCookingTime(int cookingtime){
        this.cookingtime = cookingtime;
        return this;
    }

}
