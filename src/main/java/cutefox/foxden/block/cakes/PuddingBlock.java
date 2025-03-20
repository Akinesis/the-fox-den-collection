package cutefox.foxden.block.cakes;

import cutefox.foxden.Utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class PuddingBlock extends PieBlock {
   private static final Supplier<VoxelShape> fullShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.5D, 0.3125D, 0.6875D, 0.5625D, 0.6875D));
      return shape;
   };
   public static final Map<Direction, VoxelShape> FULL_SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)fullShapeSupplier.get()));
      }

   });
   private static final Supplier<VoxelShape> threeShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.5D, 0.75D, 0.5D, 0.75D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.5D, 0.5D, 0.6875D, 0.5625D, 0.6875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.25D, 0.5D, 0.5D, 0.5D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.5D, 0.3125D, 0.5D, 0.5625D, 0.5D));
      return shape;
   };
   public static final Map<Direction, VoxelShape> THREE_SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)threeShapeSupplier.get()));
      }

   });
   private static final Supplier<VoxelShape> halfShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.5D, 0.75D, 0.5D, 0.75D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.5D, 0.5D, 0.6875D, 0.5625D, 0.6875D));
      return shape;
   };
   public static final Map<Direction, VoxelShape> HALF_SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)halfShapeSupplier.get()));
      }

   });
   private static final Supplier<VoxelShape> quarterShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.0D, 0.5D, 0.5D, 0.5D, 0.75D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.5D, 0.5D, 0.5D, 0.5625D, 0.6875D));
      return shape;
   };
   public static final Map<Direction, VoxelShape> QUARTER_SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)quarterShapeSupplier.get()));
      }

   });

   public PuddingBlock(Settings settings, Item slice) {
      super(settings, slice);
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      int cuts = (Integer)state.get(CUTS);
      Map var10000;
      switch(cuts) {
      case 1:
         var10000 = THREE_SHAPE;
         break;
      case 2:
         var10000 = HALF_SHAPE;
         break;
      case 3:
         var10000 = QUARTER_SHAPE;
         break;
      default:
         var10000 = FULL_SHAPE;
      }

      Map<Direction, VoxelShape> shape = var10000;
      Direction direction = (Direction)state.get(FACING);
      return (VoxelShape)shape.get(direction);
   }
}
