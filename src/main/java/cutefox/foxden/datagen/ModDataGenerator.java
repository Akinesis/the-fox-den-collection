package cutefox.foxden.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // Adding a provider example:
        //
        // pack.addProvider(AdvancementsProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(LootProvider::new);
        pack.addProvider(ModItemTagProvider::new);

    }

}
