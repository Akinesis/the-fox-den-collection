package cutefox.foxden.datagen.recipeTypes;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public interface RecipeEntry {

    default String ofItem(Item item){
        return Registries.ITEM.getId(item).toString();
    }

    default String ofTag(TagKey<Item> tag){
        return tag.id().toString();
    }
}
