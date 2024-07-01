package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModItemTags;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
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
                .add(ModItems.STEEL_BOOTS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.BONE_LEGGINGS)
                .add(ModItems.STEEL_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.BONE_CHESTPLATE)
                .add(ModItems.STEEL_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.BONE_HELMET)
                .add(ModItems.STEEL_HELMET);
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE);

        getOrCreateTagBuilder(ModItemTags.ENCHANTEMNT_INGREDIENT)
                .add(Items.EXPERIENCE_BOTTLE)
                .add(Items.TURTLE_SCUTE)
                .add(Items.WIND_CHARGE)
                .add(ModItems.ESSENCE_OF_PROTECTION);




    }
}
