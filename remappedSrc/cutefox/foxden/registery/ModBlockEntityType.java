package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import cutefox.foxden.block.entity.CustomEnchantingTableBlockEntity;
import cutefox.foxden.block.entity.OilCauldronBlockEntity;
import cutefox.foxden.block.entity.YeastMilkBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityType {

    public static final BlockEntityType<YeastMilkBlockEntity> YEAST_MILK;
    public static final BlockEntityType<OilCauldronBlockEntity> OIL_CAULDRON;
    public static final BlockEntityType<CustomEnchantingTableBlockEntity> CUSTOM_ENCHANTING_TABLE;

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
        CUSTOM_ENCHANTING_TABLE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Utils.id("custom_enchanting_table"),
                BlockEntityType.Builder.create(CustomEnchantingTableBlockEntity::new, ModBlocks.CUSTOM_ENCHANTING_TABLE).build()
        );
    }

    public static void registerModBlocksEntities(){
        TheFoxDenCollection.LOGGER.info("Registering mod block entities for : "+ TheFoxDenCollection.MOD_ID);
    }
}
