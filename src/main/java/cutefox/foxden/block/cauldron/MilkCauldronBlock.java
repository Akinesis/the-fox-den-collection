package cutefox.foxden.block.cauldron;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import cutefox.foxden.registery.ModStatusEffects;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.minecraft.block.cauldron.CauldronBehavior.BEHAVIOR_MAPS;
import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;

public class MilkCauldronBlock extends AbstractCauldronBlock{

    public static final CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> {
        return CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.MILK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
    };

    public static final CauldronBehavior.CauldronBehaviorMap MILK_CAULDRON_BEHAVIOR = createMap("milk");

    public MilkCauldronBlock(Settings settings) {
        super(settings, MILK_CAULDRON_BEHAVIOR);

        CauldronBehavior.registerBucketBehavior(this.behaviorMap.map());

        Map<Item, CauldronBehavior> behaviorMap = this.behaviorMap.map();


        if(ConfigBuilder.globalConfig.get(FoxDenDefaultConfig.POUTINE)){
            CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map().put(Items.MILK_BUCKET, FILL_WITH_MILK);
            CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.map().put(Items.MILK_BUCKET, FILL_WITH_MILK);
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(Items.MILK_BUCKET, FILL_WITH_MILK);
            CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.map().put(Items.MILK_BUCKET, FILL_WITH_MILK);
            OilCauldronBlock.OIL_CAULDRON_BEHAVIOR.map().put(Items.MILK_BUCKET, FILL_WITH_MILK);
        }

        behaviorMap.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), (statex) -> {
                return true;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });

        behaviorMap.put(ModItems.YEAST, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                stack.decrementUnlessCreative(1, player);
                world.setBlockState(pos, ModBlocks.YEAST_MILK_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
            if(entity instanceof LivingEntity livingEntity){
                List<StatusEffectInstance> modEffects = new ArrayList<>();
                for(StatusEffectInstance effect : livingEntity.getStatusEffects()){
                    if(effect.getEffectType() == ModStatusEffects.SKELETON_LOVE){ //Don't remove amor status effects
                        modEffects.add(new StatusEffectInstance(effect));
                    }
                }
                livingEntity.clearStatusEffects();
                for(StatusEffectInstance modEffect : modEffects){
                    livingEntity.addStatusEffect(modEffect);
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
}
