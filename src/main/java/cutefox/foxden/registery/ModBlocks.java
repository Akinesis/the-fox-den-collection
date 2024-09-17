package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.cauldron.CheeseCauldronBlock;
import cutefox.foxden.block.cauldron.MilkCauldronBlock;
import cutefox.foxden.block.cauldron.OilCauldronBlock;
import cutefox.foxden.block.cauldron.YeastMilkCauldronBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final Block  MILK_CAULDRON;
    public static final Block  YEAST_MILK_CAULDRON;
    public static final Block  CHEESE_CAULDRON;
    public static final Block  BLUE_CHEESE_CAULDRON;
    public static final Block  OIL_CAULDRON;
    public static final Block  CHEESE_BLOCK;
    public static final Block OAK_LEAVES_WALL;
    public static final Block SPRUCE_LEAVES_WALL;
    public static final Block BIRCH_LEAVES_WALL;
    public static final Block JUNGLE_LEAVES_WALL;
    public static final Block MANGROVE_LEAVES_WALL;
    public static final Block ACACIA_LEAVES_WALL;
    public static final Block DARK_OAK_LEAVES_WALL;
    public static final Block CHERRY_LEAVES_WALL;
    public static final Block AZALEA_LEAVES_WALL;
    public static final Block FLOWERING_AZALEA_LEAVES_WALL;
    public static final Block STEEL_BLOCK;


    public static List<Block> LEAVES_WALL = new ArrayList<>();

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
        OAK_LEAVES_WALL = registerLeavesWall("oak_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        SPRUCE_LEAVES_WALL = registerLeavesWall("spruce_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        BIRCH_LEAVES_WALL = registerLeavesWall("birch_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        JUNGLE_LEAVES_WALL = registerLeavesWall("jungle_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        MANGROVE_LEAVES_WALL = registerLeavesWall("mangrove_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        ACACIA_LEAVES_WALL = registerLeavesWall("acacia_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        DARK_OAK_LEAVES_WALL = registerLeavesWall("dark_oak_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.GRASS)));
        CHERRY_LEAVES_WALL = registerLeavesWall("cherry_leaves_wall", new WallBlock(leavesCherryWallSettings()));
        AZALEA_LEAVES_WALL = registerLeavesWall("azalea_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.AZALEA_LEAVES)));
        FLOWERING_AZALEA_LEAVES_WALL = registerLeavesWall("flowering_azalea_leaves_wall", new WallBlock(leavesWallSettings(BlockSoundGroup.AZALEA_LEAVES)));
        STEEL_BLOCK = registerLeavesWall("steel_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY)
                .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                .requiresTool()
                .strength(10.0F, 16.0F)
                .sounds(BlockSoundGroup.METAL)));
    }

    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Utils.id(id), block);
    }

    public static Block registerLeavesWall(String id, Block block){
        Block leavesWall = Registry.register(Registries.BLOCK, Utils.id(id), block);
        LEAVES_WALL.add(leavesWall);
        return leavesWall;
    }

    private static AbstractBlock.Settings leavesWallSettings(BlockSoundGroup sound){
        return AbstractBlock.Settings.create()
                .mapColor(MapColor.DARK_GREEN)
                .strength(0.2F)
                .sounds(sound)
                .nonOpaque()
                .allowsSpawning(Blocks::canSpawnOnLeaves)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never)
                .burnable()
                .solidBlock(Blocks::never);
    }

    private static AbstractBlock.Settings leavesCherryWallSettings(){
        return AbstractBlock.Settings.create()
                .mapColor(MapColor.PINK)
                .strength(0.2F)
                .sounds(BlockSoundGroup.CHERRY_LEAVES)
                .nonOpaque()
                .allowsSpawning(Blocks::canSpawnOnLeaves)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never)
                .burnable()
                .solidBlock(Blocks::never);
    }

}
