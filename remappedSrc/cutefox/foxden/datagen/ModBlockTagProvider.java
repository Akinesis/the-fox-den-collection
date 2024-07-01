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
                .add(ModBlocks.OIL_CAULDRON);

        getOrCreateTagBuilder(BlockTags.CAULDRONS)
                .add(ModBlocks.MILK_CAULDRON)
                .add(ModBlocks.YEAST_MILK_CAULDRON)
                .add(ModBlocks.CHEESE_CAULDRON)
                .add(ModBlocks.BLUE_CHEESE_CAULDRON)
                .add(ModBlocks.OIL_CAULDRON);

    }
}
