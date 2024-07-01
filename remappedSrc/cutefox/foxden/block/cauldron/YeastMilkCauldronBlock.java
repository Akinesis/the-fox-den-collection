package cutefox.foxden.block.cauldron;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.block.entity.YeastMilkBlockEntity;
import cutefox.foxden.registery.ModBlockEntityType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static cutefox.foxden.block.cauldron.MilkCauldronBlock.FILL_WITH_MILK;
import static net.minecraft.block.cauldron.CauldronBehavior.BEHAVIOR_MAPS;
import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;

public class YeastMilkCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider {

    public static final CauldronBehavior.CauldronBehaviorMap YEAST_MILK_CAULDRON_BEHAVIOR = createMap("milk");

    public YeastMilkCauldronBlock(Settings settings) {
        super(settings, YEAST_MILK_CAULDRON_BEHAVIOR);

        CauldronBehavior.registerBucketBehavior(this.behaviorMap.map());

        Map<Item, CauldronBehavior> behaviorMap = this.behaviorMap.map();

        behaviorMap.put(Items.MILK_BUCKET, FILL_WITH_MILK);

        behaviorMap.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), (statex) -> {
                return true;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return null;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    static CauldronBehavior.CauldronBehaviorMap createMap(String name) {
        Object2ObjectOpenHashMap<Item, CauldronBehavior> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
        object2ObjectOpenHashMap.defaultReturnValue((state, world, pos, player, hand, stack) -> {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        });
        CauldronBehavior.CauldronBehaviorMap cauldronBehaviorMap = new CauldronBehavior.CauldronBehaviorMap(name, object2ObjectOpenHashMap);
        BEHAVIOR_MAPS.put(name, cauldronBehaviorMap);
        return cauldronBehaviorMap;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new YeastMilkBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return validateTicker(type, ModBlockEntityType.YEAST_MILK, YeastMilkBlockEntity::tick);
        }
        return null;
    }

    @Nullable
    private <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<A> ticker) {
        return expectedType == givenType ? ticker : null;
    }
}
