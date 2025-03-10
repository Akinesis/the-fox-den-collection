package cutefox.foxden.datagen.recipeTypes;

import net.minecraft.item.Item;

public class RecipeItemIngredient implements RecipeEntry{
    String item;

    public RecipeItemIngredient(String item) {
        this.item = item;
    }

    public RecipeItemIngredient(Item item) {
        this.item = ofItem(item);
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
