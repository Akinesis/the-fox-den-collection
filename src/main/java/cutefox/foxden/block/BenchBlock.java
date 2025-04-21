package cutefox.foxden.block;

import cutefox.foxden.Utils.ChairUtil;
import cutefox.foxden.Utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class BenchBlock extends LineConnectingBlock {
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0D, 0.0D, 0.125D, 1.0D, 0.4375D, 0.875D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });



   public BenchBlock(Settings settings) {
      super(settings);
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      return ChairUtil.onUse(state, world, pos, player, hit, 0.0D);
   }

   protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      ChairUtil.onStateReplaced(world, pos);
   }
}
