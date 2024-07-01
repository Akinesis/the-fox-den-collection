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
    }

    protected ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }
}
