package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.entity.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class ModBlockEntityType {

    public static final BlockEntityType<YeastMilkBlockEntity> YEAST_MILK;
    public static final BlockEntityType<OilCauldronBlockEntity> OIL_CAULDRON;
    public static final BlockEntityType<StorageBlockEntity> STORAGE_ENTITY;
    public static final BlockEntityType<StorageBlockEntity> TRAY_ENTITY;
    public static final EntityType<ChairEntity> CHAIR;

    static {

        YEAST_MILK = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Utils.id("yeast_milk"),
                BlockEntityType.Builder.create(YeastMilkBlockEntity::new, ModBlocks.YEAST_MILK_CAULDRON).build()
        );
        OIL_CAULDRON = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Utils.id("oil_cauldron"),
                BlockEntityType.Builder.create(OilCauldronBlockEntity::new, ModBlocks.OIL_CAULDRON).build()
        );

        STORAGE_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Utils.id("storage"),
                BlockEntityType.Builder.create(StorageBlockEntity::new,ModBlocks.getStorageBlocks()).build());
        TRAY_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Utils.id("tray"),
                BlockEntityType.Builder.create(StorageBlockEntity::new, ModBlocks.TRAY).build());
        CHAIR = Registry.register(
                Registries.ENTITY_TYPE, Utils.id("chair"),
                EntityType.Builder.create(ChairEntity::new, SpawnGroup.MISC).dimensions(0.001F, 0.001F).build(Utils.id("chair").toString()));


    }

    public static void registerModBlocksEntities(){
        TheFoxDenCollection.LOGGER.info("Registering mod block entities for : "+ TheFoxDenCollection.MOD_ID);
    }
}
