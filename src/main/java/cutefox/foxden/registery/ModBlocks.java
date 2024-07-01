package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import cutefox.foxden.block.cauldron.CheeseCauldronBlock;
import cutefox.foxden.block.cauldron.MilkCauldronBlock;
import cutefox.foxden.block.cauldron.OilCauldronBlock;
import cutefox.foxden.block.cauldron.YeastMilkCauldronBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {

    public static final Block  MILK_CAULDRON;
    public static final Block  YEAST_MILK_CAULDRON;
    public static final Block  CHEESE_CAULDRON;
    public static final Block  BLUE_CHEESE_CAULDRON;
    public static final Block  OIL_CAULDRON;
    public static final Block  CHEESE_BLOCK;

    public static void registerModBlocks(){
        TheFoxDenCollection.LOGGER.info("Registering mod blocks for : "+ TheFoxDenCollection.MOD_ID);


    }

    static {
        MILK_CAULDRON = register("milk_cauldron", new MilkCauldronBlock(AbstractBlock.Settings.create().requiresTool().strength(2.0F).nonOpaque()));
        YEAST_MILK_CAULDRON = register("yeast_milk_cauldron", new YeastMilkCauldronBlock(AbstractBlock.Settings.create().requiresTool().strength(2.0F).nonOpaque()));
        CHEESE_CAULDRON = register("cheese_cauldron", new CheeseCauldronBlock(AbstractBlock.Settings.create().requiresTool().strength(2.0F).nonOpaque()));
        BLUE_CHEESE_CAULDRON = register("blue_cheese_cauldron", new CheeseCauldronBlock(AbstractBlock.Settings.create().requiresTool().strength(2.0F).nonOpaque()));
        CHEESE_BLOCK = register("cheese_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).sounds(BlockSoundGroup.SLIME).slipperiness(0.9f).strength(1.5f, 3.0f)));
        OIL_CAULDRON = register("oil_cauldron", new OilCauldronBlock(AbstractBlock.Settings.create().requiresTool().strength(2.0F).nonOpaque()));
    }

    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Utils.id(id), block);
    }

}
