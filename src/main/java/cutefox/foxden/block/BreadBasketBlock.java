package cutefox.foxden.block;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class BreadBasketBlock extends EatableBoxBlock {
   public static final IntProperty BITES = IntProperty.of("bites", 0, 4);
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875D, 0.0625D, 0.125D, 0.8125D, 0.5625D, 0.1875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875D, 0.0625D, 0.8125D, 0.8125D, 0.5625D, 0.875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125D, 0.0625D, 0.1875D, 0.1875D, 0.5625D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125D, 0.0625D, 0.1875D, 0.875D, 0.5625D, 0.8125D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });

   public BreadBasketBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(BITES, 0));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{BITES});
   }

   protected ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
      Hand hand = player.getActiveHand();
      ItemStack itemStack = player.getStackInHand(hand);
      if (world.isClient) {
         if (this.tryEat(world, blockPos, blockState, player).isAccepted()) {
            return ActionResult.SUCCESS;
         }

         if (itemStack.isEmpty()) {
            return ActionResult.CONSUME;
         }
      }

      return this.tryEat(world, blockPos, blockState, player);
   }

   private ActionResult tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_FOX_EAT, SoundCategory.PLAYERS, 0.5F, world.getRandom().nextFloat() * 0.1F + 0.9F);
      player.getHungerManager().eat((new FoodComponent.Builder()).nutrition(6).saturationModifier(0.8F).build());
      int bites = (Integer)state.get(BITES);
      world.emitGameEvent(player, GameEvent.EAT, pos);
      if (bites < 4) {
         world.setBlockState(pos, (BlockState)state.with(BITES, bites + 1), 3);
      } else {
         world.removeBlock(pos, false);
         ItemStack bowlStack = new ItemStack(ModBlocks.TRAY);
         ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, bowlStack);
         world.spawnEntity(itemEntity);
      }

      return ActionResult.SUCCESS;
   }

   public BlockState getAppearance(BlockState state, BlockRenderView renderView, BlockPos pos, Direction side, @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
      return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
   }

   public boolean isEnabled(FeatureSet enabledFeatures) {
      return true;
   }
}
