package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        TheFoxDenCollection.LOGGER.info("Generating Block tags for : "+TheFoxDenCollection.MOD_ID);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.MILK_CAULDRON)
                .add(ModBlocks.YEAST_MILK_CAULDRON)
                .add(ModBlocks.CHEESE_CAULDRON)
                .add(ModBlocks.BLUE_CHEESE_CAULDRON)
                .add(ModBlocks.OIL_CAULDRON)
                .add(ModBlocks.STEEL_BLOCK);

        getOrCreateTagBuilder(BlockTags.CAULDRONS)
                .add(ModBlocks.MILK_CAULDRON)
                .add(ModBlocks.YEAST_MILK_CAULDRON)
                .add(ModBlocks.CHEESE_CAULDRON)
                .add(ModBlocks.BLUE_CHEESE_CAULDRON)
                .add(ModBlocks.OIL_CAULDRON);

        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.OAK_LEAVES_WALL)
                .add(ModBlocks.SPRUCE_LEAVES_WALL)
                .add(ModBlocks.BIRCH_LEAVES_WALL)
                .add(ModBlocks.MANGROVE_LEAVES_WALL)
                .add(ModBlocks.JUNGLE_LEAVES_WALL)
                .add(ModBlocks.ACACIA_LEAVES_WALL)
                .add(ModBlocks.DARK_OAK_LEAVES_WALL)
                .add(ModBlocks.CHERRY_LEAVES_WALL)
                .add(ModBlocks.AZALEA_LEAVES_WALL)
                .add(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(ModBlocks.OAK_LEAVES_WALL)
                .add(ModBlocks.SPRUCE_LEAVES_WALL)
                .add(ModBlocks.BIRCH_LEAVES_WALL)
                .add(ModBlocks.MANGROVE_LEAVES_WALL)
                .add(ModBlocks.JUNGLE_LEAVES_WALL)
                .add(ModBlocks.ACACIA_LEAVES_WALL)
                .add(ModBlocks.DARK_OAK_LEAVES_WALL)
                .add(ModBlocks.CHERRY_LEAVES_WALL)
                .add(ModBlocks.AZALEA_LEAVES_WALL)
                .add(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL);

        getOrCreateTagBuilder(BlockTags.LEAVES)
                .add(ModBlocks.OAK_LEAVES_WALL)
                .add(ModBlocks.SPRUCE_LEAVES_WALL)
                .add(ModBlocks.BIRCH_LEAVES_WALL)
                .add(ModBlocks.MANGROVE_LEAVES_WALL)
                .add(ModBlocks.JUNGLE_LEAVES_WALL)
                .add(ModBlocks.ACACIA_LEAVES_WALL)
                .add(ModBlocks.DARK_OAK_LEAVES_WALL)
                .add(ModBlocks.CHERRY_LEAVES_WALL)
                .add(ModBlocks.AZALEA_LEAVES_WALL)
                .add(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL);

    }
}
