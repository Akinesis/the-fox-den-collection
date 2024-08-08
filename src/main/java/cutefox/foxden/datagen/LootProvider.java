package cutefox.foxden.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class LootProvider extends SimpleFabricLootTableProvider {

    public LootProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {



        /*LootTable.Builder bulider = new LootTable.Builder();
        bulider.pool(LootPool.builder()
                .with(ItemEntry.builder(Items.DIAMOND))
                .with(ItemEntry.builder(ModItems.BONE_LEGGINGS))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1))));

        lootTableBiConsumer.accept(EntityType.CHICKEN.getLootTableId(), bulider);*/
    }
}
