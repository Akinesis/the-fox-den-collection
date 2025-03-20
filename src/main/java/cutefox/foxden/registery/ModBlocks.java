package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.*;
import cutefox.foxden.block.cakes.*;
import cutefox.foxden.block.cauldron.CheeseCauldronBlock;
import cutefox.foxden.block.cauldron.MilkCauldronBlock;
import cutefox.foxden.block.cauldron.OilCauldronBlock;
import cutefox.foxden.block.cauldron.YeastMilkCauldronBlock;
import cutefox.foxden.block.storage.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.block.AbstractBlock.Settings;

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
    public static final Block APPLE_CUPCAKE_BLOCK;
    public static final Block SWEETBERRY_CUPCAKE_BLOCK;
    public static final Block STRAWBERRY_CUPCAKE_BLOCK;
    public static final Block CHOCOLATE_COOKIE_BLOCK;
    public static final Block SWEETBERRY_COOKIE_BLOCK;
    public static final Block STRAWBERRY_COOKIE_BLOCK;
    public static final Block JAR;
    public static final Block STRAWBERRY_JAM;
    public static final Block GLOWBERRY_JAM;
    public static final Block SWEETBERRY_JAM;
    public static final Block CHOCOLATE_JAM;
    public static final Block APPLE_JAM;
    public static final Block STRAWBERRY_CAKE;
    public static final Block SWEETBERRY_CAKE;
    public static final Block CHOCOLATE_CAKE;
    public static final Block CHOCOLATE_GATEAU;
    public static final Block BUNDT_CAKE;
    public static final Block LINZER_TART;
    public static final Block APPLE_PIE;
    public static final Block PUDDING;
    public static final Block IRON_TABLE;
    public static final Block IRON_BENCH;
    public static final Block IRON_CHAIR;
    public static final Block KITCHEN_SINK;
    public static final Block BRICK_COUNTER;
    public static final Block STREET_SIGN;
    public static final Block TRAY;
    public static final Block BREAD_CRATE;
    public static final Block CHOCOLATE_BOX;
    public static final Block BUN_BLOCK;
    public static final Block CAKE_STAND;
    public static final Block CAKE_DISPLAY;
    public static final Block CUPCAKE_DISPLAY;
    public static final Block BREADBOX;
    public static final Block WALL_DISPLAY;
    public static final Block CRUSTY_BREAD_BLOCK;
    public static final Block BREAD_BLOCK;
    public static final Block BAGUETTE_BLOCK;
    public static final Block TOAST_BLOCK;
    public static final Block BRAIDED_BREAD_BLOCK;
    public static final Block WAFFLE_BLOCK;
    public static final Block BAKER_STATION;
    public static final Block BLANK_CAKE;

    public static List<Block> LEAVES_WALL = new ArrayList();


    //Bakery
    private static final List<Block> BAKERY_STORAGE = new ArrayList<>();


    public static void registerModBlocks(){
        TheFoxDenCollection.LOGGER.info("Registering mod blocks for : "+ TheFoxDenCollection.MOD_ID);
    }

    private static AbstractBlock.Settings jarSettings() {
        return Settings.copy(Blocks.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS);
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
        BAKER_STATION = registerWithItem("baker_station", new BakerStationBlock(Settings.copy(Blocks.BRICKS)));
        BLANK_CAKE = register("blank_cake", new BlankCakeBlock(Settings.copy(Blocks.CAKE)));
        APPLE_CUPCAKE_BLOCK = register("apple_cupcake_block", new CupcakeBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        SWEETBERRY_CUPCAKE_BLOCK = register("sweetberry_cupcake_block", new CupcakeBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        STRAWBERRY_CUPCAKE_BLOCK = register("strawberry_cupcake_block", new CupcakeBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        CHOCOLATE_COOKIE_BLOCK = register("chocolate_cookie_block", new CookieBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        SWEETBERRY_COOKIE_BLOCK = register("sweetberry_cookie_block", new CookieBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        STRAWBERRY_COOKIE_BLOCK = register("strawberry_cookie_block", new CookieBlock(Settings.copy(Blocks.CAKE).breakInstantly().solid()));
        STRAWBERRY_CAKE = registerWithItem("strawberry_cake", new ModCakeBlock(Settings.copy(Blocks.CAKE), ModItems.STRAWBERRY_CAKE_SLICE));
        SWEETBERRY_CAKE = registerWithItem("sweetberry_cake", new ModCakeBlock(Settings.copy(Blocks.CAKE), ModItems.SWEETBERRY_CAKE_SLICE));
        CHOCOLATE_CAKE = registerWithItem("chocolate_cake", new ModCakeBlock(Settings.copy(Blocks.CAKE), ModItems.CHOCOLATE_CAKE_SLICE));
        CHOCOLATE_GATEAU = registerWithItem("chocolate_gateau", new ModCakeBlock(Settings.copy(Blocks.CAKE), ModItems.CHOCOLATE_GATEAU_SLICE));
        BUNDT_CAKE = register("bundt_cake", new BundtCakeBlock(Settings.copy(Blocks.CAKE), ModItems.BUNDT_CAKE_SLICE));
        LINZER_TART = register("linzer_tart", new LinzerTartBlock(Settings.copy(Blocks.CAKE), ModItems.LINZER_TART_SLICE));
        APPLE_PIE = register("apple_pie", new ApplePieBlock(Settings.copy(Blocks.CAKE), ModItems.APPLE_PIE_SLICE));
        PUDDING = registerWithItem("pudding", new PuddingBlock(Settings.copy(Blocks.CAKE), ModItems.PUDDING_SLICE));
        JAR = register("jar", new StackableBlock(jarSettings(), 3));
        STRAWBERRY_JAM = register("strawberry_jam", new StackableBlock(jarSettings(), 3));
        GLOWBERRY_JAM = register("glowberry_jam", new StackableBlock(jarSettings(), 3));
        SWEETBERRY_JAM = register("sweetberry_jam", new StackableBlock(jarSettings(), 3));
        CHOCOLATE_JAM = register("chocolate_jam", new StackableBlock(jarSettings(), 3));
        APPLE_JAM = register("apple_jam", new StackableBlock(jarSettings(), 3));
        BUN_BLOCK = register("bun_block", new StackableBlock(Settings.copy(Blocks.CAKE), 4));
        IRON_TABLE = registerWithItem("iron_table", new TableBlock(Settings.copy(Blocks.IRON_BLOCK)));
        IRON_BENCH = registerWithItem("iron_bench", new BenchBlock(Settings.copy(Blocks.IRON_BLOCK)));
        IRON_CHAIR = registerWithItem("iron_chair", new ChairBlock(Settings.copy(Blocks.IRON_BLOCK)));
        KITCHEN_SINK = registerWithItem("kitchen_sink", new SinkBlock(Settings.copy(Blocks.IRON_BLOCK).nonOpaque()));
        BRICK_COUNTER = registerWithItem("brick_counter", new LineConnectingBlock(Settings.copy(Blocks.BRICKS)));
        STREET_SIGN = registerWithItem("street_sign", new StreetSignBlock(Settings.copy(Blocks.OAK_PLANKS)));
        TRAY = registerBakeryStorage("tray", new TrayBlock(Settings.copy(Blocks.OAK_PLANKS)));
        CAKE_STAND = registerBakeryStorage("cake_stand", new CakeStandBlock(Settings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.GLASS)));
        CAKE_DISPLAY = registerBakeryStorage("cake_display", new CakeDisplayBlock(Settings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.GLASS)));
        CUPCAKE_DISPLAY = registerBakeryStorage("cupcake_display", new CupcakeDisplayBlock(Settings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.WOOD)));
        BREADBOX = registerBakeryStorage("breadbox", new BreadBox(Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        WALL_DISPLAY = registerBakeryStorage("wall_display", new WallDisplayBlock(Settings.copy(Blocks.OAK_PLANKS)));
        BREAD_CRATE = registerWithItem("bread_crate", new BreadBasketBlock(Settings.copy(Blocks.OAK_PLANKS)));
        CHOCOLATE_BOX = registerWithItem("chocolate_box", new EatableBoxBlock(Settings.copy(Blocks.CAKE)));
        CRUSTY_BREAD_BLOCK = register("crusty_bread_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 3));
        BREAD_BLOCK = register("bread_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 3));
        BAGUETTE_BLOCK = register("baguette_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 4));
        TOAST_BLOCK = register("toast_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 3));
        BRAIDED_BREAD_BLOCK = register("braided_bread_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 3));
        WAFFLE_BLOCK = register("waffle_block", new StackableEatableBlock(Settings.copy(Blocks.CAKE), 4));


    }


    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Utils.id(id), block);
    }

    public static Block registerLeavesWall(String id, Block block){
        Block leavesWall = Registry.register(Registries.BLOCK, Utils.id(id), block);
        LEAVES_WALL.add(leavesWall);
        return leavesWall;
    }

    public static Block registerBakeryStorage(String id, Block block) {
        Block registeredBlock = (Block)Registry.register(Registries.BLOCK, Utils.id(id), block);
        BAKERY_STORAGE.add(registeredBlock);
        ModItems.addBackeryBlockItem(id, registeredBlock);
        return registeredBlock;
    }

    public static Block registerWithItem(String id, Block block) {
        Block registeredBlock = (Block)Registry.register(Registries.BLOCK, Utils.id(id), block);
        ModItems.addBackeryBlockItem(id, registeredBlock);
        return registeredBlock;
    }

    public static Block[] getStorageBlocks() {
        return (Block[])BAKERY_STORAGE.stream().toArray((x$0) -> {
            return new Block[x$0];
        });
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
