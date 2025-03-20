package cutefox.foxden.block.cauldron;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.block.entity.OilCauldronBlockEntity;
import cutefox.foxden.registery.ModBlockEntityType;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.minecraft.block.cauldron.CauldronBehavior.BEHAVIOR_MAPS;
import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;

public class OilCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider {

    public static final CauldronBehavior FILL_WITH_OIL = (state, world, pos, player, hand, stack) -> {
        return CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.OIL_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
    };

    public static final CauldronBehavior.CauldronBehaviorMap OIL_CAULDRON_BEHAVIOR = createMap("oil");

    public OilCauldronBlock(Settings settings) {
        super(settings, OIL_CAULDRON_BEHAVIOR);

        CauldronBehavior.registerBucketBehavior(this.behaviorMap.map());

        Map<Item, CauldronBehavior> behaviorMap = this.behaviorMap.map();

        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map().put(ModItems.OIL_BUCKET, FILL_WITH_OIL);
        CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.map().put(ModItems.OIL_BUCKET, FILL_WITH_OIL);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ModItems.OIL_BUCKET, FILL_WITH_OIL);
        CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.map().put(ModItems.OIL_BUCKET, FILL_WITH_OIL);
        MilkCauldronBlock.MILK_CAULDRON_BEHAVIOR.map().put(ModItems.OIL_BUCKET, FILL_WITH_OIL);

        behaviorMap.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(ModItems.OIL_BUCKET), (statex) -> {
                return true;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });

        behaviorMap.put(Items.POTATO, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                if(world.getBlockEntity(pos) instanceof OilCauldronBlockEntity blockEntity){
                    if(blockEntity.isBoiling()){
                        Item item = stack.getItem();
                        player.incrementStat(Stats.USE_CAULDRON);
                        player.incrementStat(Stats.USED.getOrCreateStat(item));
                        stack.decrementUnlessCreative(1, player);
                        player.giveItemStack(new ItemStack(ModItems.FRIES, 1));
                        world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }

            return ItemActionResult.success(world.isClient);

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

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (this.isEntityTouchingFluid(state, pos, entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (world.getBlockEntity(pos) instanceof OilCauldronBlockEntity blockEntity) {
                    if (blockEntity.isBoiling()) {
                        livingEntity.damage(entity.getDamageSources().lava(), 1f);
                    }
                }
            }
        }
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
        return new OilCauldronBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return validateTicker(type, ModBlockEntityType.OIL_CAULDRON, OilCauldronBlockEntity::tick);
        }
        return null;
    }

    @Nullable
    private <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<A> ticker) {
        return expectedType == givenType ? ticker : null;
    }
}
