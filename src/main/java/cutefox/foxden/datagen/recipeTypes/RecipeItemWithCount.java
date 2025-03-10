package cutefox.foxden.datagen.recipeTypes;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class RecipeItemWithCount {
    private String id;
    private int count;

    public RecipeItemWithCount(String id, int count) {
        this.id = id;
        this.count = count;
    }

    public RecipeItemWithCount(Item id, int count) {
        this.id = Registries.ITEM.getId(id).toString();
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
