package cutefox.foxden.block;

import cutefox.foxden.Utils.ChairUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;
import org.jetbrains.annotations.Nullable;

public class ChairBlock extends Block {
   public static final DirectionProperty FACING;
   private final VoxelShape SINGLE_SHAPE = makeSingleShape();
   private static final VoxelShape[] SHAPE;

   public ChairBlock(Settings settings) {
      super(settings);
      this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
   }

   protected static VoxelShape makeSingleShape() {
      VoxelShape top = Block.createCuboidShape(3.0D, 9.0D, 3.0D, 13.0D, 10.0D, 13.0D);
      VoxelShape leg1 = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 5.0D, 9.0D, 5.0D);
      VoxelShape leg2 = Block.createCuboidShape(3.0D, 0.0D, 11.0D, 5.0D, 9.0D, 13.0D);
      VoxelShape leg3 = Block.createCuboidShape(11.0D, 0.0D, 11.0D, 13.0D, 9.0D, 13.0D);
      VoxelShape leg4 = Block.createCuboidShape(11.0D, 0.0D, 3.0D, 13.0D, 9.0D, 5.0D);
      return VoxelShapes.union(top, new VoxelShape[]{leg1, leg2, leg3, leg4});
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      VoxelShape var10000;
      switch((Direction)state.get(FACING)) {
      case WEST:
         var10000 = VoxelShapes.union(this.SINGLE_SHAPE, SHAPE[1]);
         break;
      case SOUTH:
         var10000 = VoxelShapes.union(this.SINGLE_SHAPE, SHAPE[2]);
         break;
      case EAST:
         var10000 = VoxelShapes.union(this.SINGLE_SHAPE, SHAPE[3]);
         break;
      default:
         var10000 = VoxelShapes.union(this.SINGLE_SHAPE, SHAPE[0]);
      }

      return var10000;
   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      return ChairUtil.onUse(state, world, pos, player, hit, 0.1D);
   }

   protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      super.onStateReplaced(state, world, pos, newState, moved);
      ChairUtil.onStateReplaced(world, pos);
   }

   static {
      FACING = Properties.HORIZONTAL_FACING;
      SHAPE = new VoxelShape[]{Block.createCuboidShape(3.0D, 10.0D, 11.0D, 13.0D, 22.0D, 13.0D), Block.createCuboidShape(11.0D, 10.0D, 3.0D, 13.0D, 22.0D, 13.0D), Block.createCuboidShape(3.0D, 10.0D, 3.0D, 13.0D, 22.0D, 5.0D), Block.createCuboidShape(3.0D, 10.0D, 3.0D, 5.0D, 22.0D, 13.0D)};
   }
}
