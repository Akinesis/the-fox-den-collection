package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModItemTags;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        TheFoxDenCollection.LOGGER.info("Generating Item tags for : "+TheFoxDenCollection.MOD_ID);

        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.BONE_BOOTS)
                .add(ModItems.STEEL_BOOTS)
                .add(ModItems.SPACE_RANGER_BOOTS);

        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ModItemTags.CHEESE)
                .add(ModItems.CHEESE)
                .add(ModItems.BLUE_CHEESE);

        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.BONE_LEGGINGS)
                .add(ModItems.STEEL_LEGGINGS)
                .add(ModItems.SPACE_RANGER_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.BONE_CHESTPLATE)
                .add(ModItems.STEEL_CHESTPLATE)
                .add(ModItems.SPACE_RANGER_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.BONE_HELMET)
                .add(ModItems.STEEL_HELMET)
                .add(ModItems.SPACE_RANGER_BOOTS);
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.WALLS)
                .add(ModItems.OAK_LEAVES_WALL)
                .add(ModItems.SPRUCE_LEAVES_WALL)
                .add(ModItems.BIRCH_LEAVES_WALL)
                .add(ModItems.ACACIA_LEAVES_WALL)
                .add(ModItems.JUNGLE_LEAVES_WALL)
                .add(ModItems.MANGROVE_LEAVES_WALL)
                .add(ModItems.DARK_OAK_LEAVES_WALL)
                .add(ModItems.CHERRY_LEAVES_WALL)
                .add(ModItems.AZALEA_LEAVES_WALL)
                .add(ModItems.FLOWERING_AZALEA_LEAVES_WALL);;

        getOrCreateTagBuilder(ModItemTags.YEAST)
                .add(ModItems.YEAST)
                .addOptionalTag(Identifier.of("farm_and_charm:yeast"));

        getOrCreateTagBuilder(ModItemTags.EGG)
                .add(Items.EGG)
                .addOptionalTag(Identifier.of("vegandelight:applesauce"));

        getOrCreateTagBuilder(ModItemTags.DOUGHT)
                .addOptional(Identifier.of("farmersdelights:wheat_dought"));

        getOrCreateTagBuilder(ModItemTags.KNIVES)
                .add(ModItems.BREAD_KNIFE);
        getOrCreateTagBuilder(ModItemTags.YEAST)
                .add(ModItems.YEAST)
                .addOptionalTag(Identifier.of("farm_and_charm:yeast"));

        getOrCreateTagBuilder(ModItemTags.BREAD)
                .add(Items.BREAD)
                .add(ModItems.CRUSTY_BREAD)
                .add(ModItems.BAGUETTE)
                .add(ModItems.BRAIDED_BREAD)
                .add(ModItems.BUN).add(ModItems.TOAST)
                .add(ModItems.BREAD);

        getOrCreateTagBuilder(ModItemTags.JAM)
                .add(ModItems.APPLE_JAM)
                .add(ModItems.STRAWBERRY_JAM)
                .add(ModItems.CHOCOLATE_JAM)
                .add(ModItems.GLOWBERRY_JAM)
                .add(ModItems.SWEETBERRY_JAM)
                .addOptionalTag(Identifier.of("c:jams"));

        getOrCreateTagBuilder(ModItemTags.DOUGHT)
                .addOptional(Identifier.of("farmersdelights:wheat_dought"))
                .add(ModItems.DOUGHT);
    }
}
