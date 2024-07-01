package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        TheFoxDenCollection.LOGGER.info("Generating block model data for : "+TheFoxDenCollection.MOD_ID);

        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHEESE_BLOCK);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        TheFoxDenCollection.LOGGER.info("Generating item model data for : "+TheFoxDenCollection.MOD_ID);

        //region FOOD
        itemModelGenerator.register(ModItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLUE_CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.YEAST, Models.GENERATED);
        itemModelGenerator.register(ModItems.OIL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.FRIES, Models.GENERATED);
        itemModelGenerator.register(ModItems.BROWN_SAUCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.POUTINE, Models.GENERATED);
        //endregion

        //region MISC
        itemModelGenerator.register(ModItems.STEEL_BLEND, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);
        //endregion

        //region ARMOR
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_BOOTS);
        //endregion

    }
}
