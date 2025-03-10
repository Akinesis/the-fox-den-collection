package cutefox.foxden.datagen.recipeTypes;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public class RecipeTagIngredient implements RecipeEntry{

    private String tag;

    public RecipeTagIngredient(String tag) {
        this.tag = tag;
    }

    public RecipeTagIngredient(TagKey<Item> tag) {
        this.tag = ofTag(tag);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String entry) {
        tag = entry;
    }
}
