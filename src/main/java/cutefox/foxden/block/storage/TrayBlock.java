package cutefox.foxden.block.storage;

import cutefox.foxden.Utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.block.AbstractBlock.Settings;

public class TrayBlock extends CakeStandBlock {
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.1875D, 0.75D, 0.0625D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75D, 0.0625D, 0.125D, 0.8125D, 0.3125D, 0.875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875D, 0.0625D, 0.125D, 0.25D, 0.3125D, 0.875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25D, 0.0625D, 0.8125D, 0.75D, 0.3125D, 0.875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25D, 0.0625D, 0.125D, 0.75D, 0.3125D, 0.1875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125D, 0.33375D, -0.121875D, 0.6875D, 0.33375D, 0.003125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125D, -0.04625D, 0.928125D, 0.6875D, -0.04625D, 1.053125D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });

   public TrayBlock(Settings settings) {
      super(settings);
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   public int size() {
      return 5;
   }

   public Direction[] unAllowedDirections() {
      return new Direction[]{Direction.DOWN};
   }

   public int getSection(Float f, Float y) {
      float oneS = 0.11111111F;
      int nSection = (int)(f / oneS);
      return 8 - nSection;
   }
}
