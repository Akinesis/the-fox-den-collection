package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

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
                .add(ModItems.FLOWERING_AZALEA_LEAVES_WALL);

    }
}
