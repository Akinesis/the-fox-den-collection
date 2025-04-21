package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {

    @Override
    public void generate() {
        TheFoxDenCollection.LOGGER.info("Generating Loot table data for : "+TheFoxDenCollection.MOD_ID);

        addDrop(ModBlocks.BLUE_CHEESE_CAULDRON, Items.CAULDRON);
        addDrop(ModBlocks.BLUE_CHEESE_CAULDRON, new ItemStack(ModItems.BLUE_CHEESE, 3).getItem());
        addDrop(ModBlocks.CHEESE_CAULDRON, Items.CAULDRON);
        addDrop(ModBlocks.CHEESE_CAULDRON, new ItemStack(ModItems.CHEESE, 3).getItem());
        addDrop(ModBlocks.MILK_CAULDRON, Items.CAULDRON);
        addDrop(ModBlocks.YEAST_MILK_CAULDRON, Items.CAULDRON);
        addDrop(ModBlocks.OIL_CAULDRON, Items.CAULDRON);

        addDrop(ModBlocks.STEEL_BLOCK, ModItems.STEEL_BLOCK);

        //Leaves wall
        addDrop(ModBlocks.OAK_LEAVES_WALL, ModItems.OAK_LEAVES_WALL);
        addDrop(ModBlocks.SPRUCE_LEAVES_WALL, ModItems.SPRUCE_LEAVES_WALL);
        addDrop(ModBlocks.BIRCH_LEAVES_WALL, ModItems.BIRCH_LEAVES_WALL);
        addDrop(ModBlocks.MANGROVE_LEAVES_WALL, ModItems.MANGROVE_LEAVES_WALL);
        addDrop(ModBlocks.JUNGLE_LEAVES_WALL, ModItems.JUNGLE_LEAVES_WALL);
        addDrop(ModBlocks.ACACIA_LEAVES_WALL, ModItems.ACACIA_LEAVES_WALL);
        addDrop(ModBlocks.DARK_OAK_LEAVES_WALL, ModItems.DARK_OAK_LEAVES_WALL);
        addDrop(ModBlocks.CHERRY_LEAVES_WALL, ModItems.CHERRY_LEAVES_WALL);
        addDrop(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL, ModItems.FLOWERING_AZALEA_LEAVES_WALL);
        addDrop(ModBlocks.AZALEA_LEAVES_WALL, ModItems.AZALEA_LEAVES_WALL);

        addDrop(ModBlocks.EASTER_EGG, ModItems.EASTER_EGG);
        addDrop(ModBlocks.CHOCOLATE_RABBIT, ModItems.CHOCOLATE_RABBIT);
        addDrop(ModBlocks.CHOCOLATE_CHICKEN, ModItems.CHOCOLATE_CHICKEN);
        addDrop(ModBlocks.WHITE_CHOCOLATE_RABBIT, ModItems.WHITE_CHOCOLATE_RABBIT);
    }

    protected ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }
}
