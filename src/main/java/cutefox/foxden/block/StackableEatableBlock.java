package cutefox.foxden.block;

import cutefox.foxden.Utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StackableEatableBlock extends Block {
   private static final IntProperty STACK_PROPERTY = IntProperty.of("stack", 1, 8);
   private static final DirectionProperty FACING;
   private final int maxStack;
   private static final VoxelShape SHAPE;

   public StackableEatableBlock(Settings settings, int maxStack) {
      super(settings);
      this.maxStack = maxStack;
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.getStateManager().getDefaultState()).with(STACK_PROPERTY, 1)).with(FACING, Direction.NORTH));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{STACK_PROPERTY});
      builder.add(new Property[]{FACING});
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult blockHitResult) {
      ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
      if (player.isSneaking() && stack.isEmpty()) {
         if (!world.isClient) {
            if ((Integer)state.get(STACK_PROPERTY) > 1) {
               world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) - 1), 3);
            } else {
               world.removeBlock(pos, false);
            }

            player.getHungerManager().eat((new FoodComponent.Builder()).nutrition(3).saturationModifier(0.6F).build());
            world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return ActionResult.success(world.isClient);
         }
      } else if (stack.getItem() == this.asItem()) {
         if ((Integer)state.get(STACK_PROPERTY) < this.maxStack) {
            world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) + 1), 3);
            if (!player.isCreative()) {
               stack.decrement(1);
            }

            return ActionResult.SUCCESS;
         }
      } else if (stack.isEmpty()) {
         if ((Integer)state.get(STACK_PROPERTY) > 1) {
            world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) - 1), 3);
         } else if ((Integer)state.get(STACK_PROPERTY) == 1) {
            world.removeBlock(pos, false);
         }

         Direction direction = player.getFacing().getOpposite();
         double xMotion = (double)direction.getOffsetX() * 0.13D;
         double yMotion = 0.35D;
         double zMotion = (double)direction.getOffsetZ() * 0.13D;
         Utils.spawnSlice(world, new ItemStack(this), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, xMotion, yMotion, zMotion);
         return ActionResult.SUCCESS;
      }

      return super.onUse(state, world, pos, player, blockHitResult);
   }

   static {
      FACING = HorizontalFacingBlock.FACING;
      SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
   }
}
