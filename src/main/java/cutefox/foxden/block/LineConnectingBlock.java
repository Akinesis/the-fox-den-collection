package cutefox.foxden.block;

import cutefox.foxden.Utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LineConnectingBlock extends Block {
   public static final DirectionProperty FACING;
   public static final EnumProperty<Utils.LineConnectingType> TYPE;

   public LineConnectingBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(TYPE, Utils.LineConnectingType.NONE));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FACING});
      builder.add(new Property[]{TYPE});
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      return ActionResult.PASS;
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext context) {
      Direction facing = context.getHorizontalPlayerFacing().getOpposite();
      BlockState blockState = (BlockState)this.getDefaultState().with(FACING, facing);
      World world = context.getWorld();
      BlockPos clickedPos = context.getBlockPos();
      BlockState state;
      switch(facing) {
      case EAST:
         state = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.south()), world.getBlockState(clickedPos.north())));
         break;
      case SOUTH:
         state = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.west()), world.getBlockState(clickedPos.east())));
         break;
      case WEST:
         state = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.north()), world.getBlockState(clickedPos.south())));
         break;
      default:
         state = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.east()), world.getBlockState(clickedPos.west())));
      }

      return state;
   }

   protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
      if (!world.isClient) {
         Direction facing = (Direction)state.get(FACING);
         Utils.LineConnectingType type;
         switch(facing) {
         case EAST:
            type = this.getType(state, world.getBlockState(pos.south()), world.getBlockState(pos.north()));
            break;
         case SOUTH:
            type = this.getType(state, world.getBlockState(pos.west()), world.getBlockState(pos.east()));
            break;
         case WEST:
            type = this.getType(state, world.getBlockState(pos.north()), world.getBlockState(pos.south()));
            break;
         default:
            type = this.getType(state, world.getBlockState(pos.east()), world.getBlockState(pos.west()));
         }

         if (state.get(TYPE) != type) {
            state = (BlockState)state.with(TYPE, type);
         }

         world.setBlockState(pos, state, 3);
      }

   }

   public Utils.LineConnectingType getType(BlockState state, BlockState left, BlockState right) {
      boolean shape_left_same = left.getBlock() == state.getBlock() && left.get(FACING) == state.get(FACING);
      boolean shape_right_same = right.getBlock() == state.getBlock() && right.get(FACING) == state.get(FACING);
      if (shape_left_same && shape_right_same) {
         return Utils.LineConnectingType.MIDDLE;
      } else if (shape_left_same) {
         return Utils.LineConnectingType.LEFT;
      } else {
         return shape_right_same ? Utils.LineConnectingType.RIGHT : Utils.LineConnectingType.NONE;
      }
   }

   static {
      FACING = Properties.HORIZONTAL_FACING;
      TYPE = Utils.LINE_CONNECTING_TYPE;
   }
}
