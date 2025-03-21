package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import cutefox.foxden.registery.ModModels;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        TheFoxDenCollection.LOGGER.info("Generating block model data for : "+TheFoxDenCollection.MOD_ID);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STEEL_BLOCK);

        createLeavesWallModel(blockStateModelGenerator, ModBlocks.OAK_LEAVES_WALL, Blocks.OAK_LEAVES, ModItems.OAK_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.SPRUCE_LEAVES_WALL, Blocks.SPRUCE_LEAVES, ModItems.SPRUCE_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.ACACIA_LEAVES_WALL, Blocks.ACACIA_LEAVES, ModItems.ACACIA_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.JUNGLE_LEAVES_WALL, Blocks.JUNGLE_LEAVES, ModItems.JUNGLE_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.BIRCH_LEAVES_WALL, Blocks.BIRCH_LEAVES, ModItems.BIRCH_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.DARK_OAK_LEAVES_WALL, Blocks.DARK_OAK_LEAVES, ModItems.DARK_OAK_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.MANGROVE_LEAVES_WALL, Blocks.MANGROVE_LEAVES, ModItems.MANGROVE_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.CHERRY_LEAVES_WALL, Blocks.CHERRY_LEAVES, ModItems.CHERRY_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.AZALEA_LEAVES_WALL, Blocks.AZALEA_LEAVES, ModItems.AZALEA_LEAVES_WALL);
        createLeavesWallModel(blockStateModelGenerator, ModBlocks.FLOWERING_AZALEA_LEAVES_WALL, Blocks.FLOWERING_AZALEA_LEAVES, ModItems.FLOWERING_AZALEA_LEAVES_WALL);

        //region SHIPS
        blockStateModelGenerator.registerLog(ModBlocks.SHIP_MAST).log(ModBlocks.SHIP_MAST);
        //endregion

    }

    private void createLeavesWallModel(BlockStateModelGenerator blockStateModelGenerator,Block leavesWall, Block textureBlock, Item wallItem){

        TextureMap textureMap = TextureMap.all(textureBlock);

        Identifier post = ModModels.TEMPLATE_LEAVES_WALL_POST.upload(leavesWall, textureMap, blockStateModelGenerator.modelCollector);
        Identifier low = ModModels.TEMPLATE_LEAVES_WALL_SIDE.upload(leavesWall, textureMap, blockStateModelGenerator.modelCollector);
        Identifier tall = ModModels.TEMPLATE_LEAVES_WALL_SIDE_TALL.upload(leavesWall, textureMap, blockStateModelGenerator.modelCollector);


        blockStateModelGenerator.blockStateCollector
                .accept(BlockStateModelGenerator.createWallBlockState(leavesWall,post, low, tall));
        blockStateModelGenerator
                .registerParentedItemModel(wallItem,ModModels.LEAVES_WALL_INVENTORY.upload(leavesWall, textureMap, blockStateModelGenerator.modelCollector));

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
        itemModelGenerator.register(ModItems.ROTTEN_LEATHER, Models.GENERATED);
        //endregion

        itemModelGenerator.register(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);

        //region ARMOR
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.STEEL_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_RANGER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_RANGER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_RANGER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_RANGER_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.BIKE_HELMET);
        //endregion

        //region WOLF ARMORS
        itemModelGenerator.registerWolfArmor(ModItems.IRON_WOLF_ARMOR);
        itemModelGenerator.registerWolfArmor(ModItems.DIAMOND_WOLF_ARMOR);
        //endregion

        //region BAKERY
        itemModelGenerator.register(ModItems.BAKING_STATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.CROISSANT_RAW, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BAGUETTE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CORNET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BUN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BREAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_TOAST, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BRAIDED_BREAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_WAFFLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CRUSTY_BREAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_LINZER_TART, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_BUNDT_CAKE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_APPLE_PIE, Models.GENERATED);
        //endregion

        //region SHIPS

        //endregion

    }
}
