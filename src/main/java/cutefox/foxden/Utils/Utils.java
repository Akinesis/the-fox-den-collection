package cutefox.foxden.Utils;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

    public static final List<String> RANDOM_NAMES = List.of(
            "Gaston Mike Willson Henri Salade",
            "Connor",
            "Némésis",
            "Panache",
            "Julie",
            "Rocky",
            "Skye",
            "Chase (acab)",
            "Marshall",
            "Rubble",
            "Zuma",
            "Evrest",
            "Tracker",
            "Cici"
            );

    public static Identifier id(String path) {
        return Identifier.of("fox_den", path);
    }

    public static Identifier blockId(String path) {
        return Identifier.of("fox_den", "block/"+path);
    }

    public static Identifier id() {
        return Identifier.of("fox_den");
    }

    public static boolean matchesRecipe(RecipeInput inventory, List<Ingredient> recipe, int startIndex, int endIndex) {
        List<ItemStack> validStacks = new ArrayList();

        for(int i = startIndex; i <= endIndex; ++i) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                validStacks.add(stackInSlot);
            }
        }

        Iterator var10 = recipe.iterator();

        boolean matches;
        do {
            if (!var10.hasNext()) {
                return true;
            }

            Ingredient entry = (Ingredient)var10.next();
            matches = false;
            Iterator var8 = validStacks.iterator();

            while(var8.hasNext()) {
                ItemStack item = (ItemStack)var8.next();
                if (entry.test(item)) {
                    matches = true;
                    validStacks.remove(item);
                    break;
                }
            }
        } while(matches);

        return false;
    }

    public static void spawnSlice(World level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setVelocity(xMotion, yMotion, zMotion);
        level.spawnEntity(entity);
    }
}
