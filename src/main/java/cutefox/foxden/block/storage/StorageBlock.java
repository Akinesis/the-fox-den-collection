package cutefox.foxden.block.storage;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public abstract class StorageBlock extends HorizontalFacingBlock implements BlockEntityProvider {
   public static final SoundEvent event;

   protected StorageBlock(Settings settings) {
      super(settings);
      this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FACING});
   }

   public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new StorageBlockEntity(pos, state, this.size());
   }

   protected ActionResult onUse(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, BlockHitResult blockHitResult) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      if (blockEntity instanceof StorageBlockEntity) {
         StorageBlockEntity shelfBlockEntity = (StorageBlockEntity)blockEntity;
         Optional optional = Utils.getRelativeHitCoordinatesForBlockFace(blockHitResult, (Direction)blockState.get(FACING), this.unAllowedDirections());
         if (optional.isEmpty()) {
            return ActionResult.PASS;
         } else {
            Pair<Float, Float> ff = (Pair)optional.get();
            int i = this.getSection((Float)ff.getLeft(), (Float)ff.getRight());
            if (i != Integer.MIN_VALUE && i < shelfBlockEntity.getInventory().size()) {
               if (!((ItemStack)shelfBlockEntity.getInventory().get(i)).isEmpty()) {
                  this.remove(level, blockPos, player, shelfBlockEntity, i);
                  return ActionResult.success(level.isClient);
               } else {
                  ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
                  if (!stack.isEmpty() && this.canInsertStack(stack)) {
                     this.add(level, blockPos, player, shelfBlockEntity, stack, i);
                     return ActionResult.success(level.isClient);
                  } else {
                     return ActionResult.CONSUME;
                  }
               }
            } else {
               return ActionResult.PASS;
            }
         }
      } else {
         return ActionResult.PASS;
      }
   }

   public void add(World level, BlockPos blockPos, PlayerEntity player, StorageBlockEntity shelfBlockEntity, ItemStack itemStack, int i) {
      if (!level.isClient) {
         SoundEvent soundEvent = this.getAddSound(level, blockPos, player, i);
         shelfBlockEntity.setStack(i, itemStack.split(1));
         level.playSound((PlayerEntity)null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
         if (player.isCreative()) {
            itemStack.increment(1);
         }

         level.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
      }

   }

   public void remove(World level, BlockPos blockPos, PlayerEntity player, StorageBlockEntity shelfBlockEntity, int i) {
      if (!level.isClient) {
         ItemStack itemStack = shelfBlockEntity.removeStack(i);
         SoundEvent soundEvent = this.getRemoveSound(level, blockPos, player, i);
         level.playSound((PlayerEntity)null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
         if (!player.getInventory().insertStack(itemStack)) {
            player.dropItem(itemStack, false);
         }

         level.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
      }

   }

   public SoundEvent getRemoveSound(World level, BlockPos blockPos, PlayerEntity player, int i) {
      return event;
   }

   public SoundEvent getAddSound(World level, BlockPos blockPos, PlayerEntity player, int i) {
      return event;
   }

   public abstract int size();

   public abstract Identifier type();

   public abstract Direction[] unAllowedDirections();

   public abstract boolean canInsertStack(ItemStack var1);

   public abstract int getSection(Float var1, Float var2);

   static {
      event = SoundEvents.BLOCK_WOOD_PLACE;
   }
}
