package cutefox.foxden.block.entity;

import cutefox.foxden.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomEnchantingTableBlockEntity extends BlockEntity implements Nameable {

    @Nullable
    private Text customName;

    public CustomEnchantingTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.ENCHANTING_TABLE, pos, state);
        //setCustomName(Text.of(Utils.id("custom_enchanting_table")));
    }

    public Text getName() {
        return (Text)(this.customName != null ? this.customName : Text.translatable("container.enchant"));
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        this.customName = (Text)components.get(DataComponentTypes.CUSTOM_NAME);
    }

    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CUSTOM_NAME, this.customName);
    }

    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        nbt.remove("CustomName");
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Text.Serialization.toJsonString(this.customName, registryLookup));
        }

    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("CustomName", 8)) {
            this.customName = tryParseCustomName(nbt.getString("CustomName"), registryLookup);
        }

    }

    public static void tick(World world, BlockPos pos, BlockState state, CustomEnchantingTableBlockEntity blockEntity) {

        PlayerEntity playerEntity = world.getClosestPlayer((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 3.0, false);
        if (playerEntity != null) {
            double d = playerEntity.getX() - ((double) pos.getX() + 0.5);
            double e = playerEntity.getZ() - ((double) pos.getZ() + 0.5);

            float i = 0.2F;

        }
    }
}
