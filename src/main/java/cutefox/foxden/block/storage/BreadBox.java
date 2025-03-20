package cutefox.foxden.block.storage;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.component.DataComponentTypes;
import org.jetbrains.annotations.Nullable;

public class BreadBox extends StorageBlock {
   public static final MapCodec<BreadBox> CODEC = createCodec(BreadBox::new);
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625D, 0.0D, 0.1875D, 0.9375D, 0.0625D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625D, 0.5D, 0.25D, 0.9375D, 0.5625D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625D, 0.0625D, 0.25D, 0.125D, 0.5D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125D, 0.0625D, 0.75D, 0.875D, 0.5D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625D, 0.0625D, 0.1875D, 0.125D, 0.375D, 0.25D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875D, 0.0625D, 0.25D, 0.9375D, 0.5D, 0.8125D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875D, 0.0625D, 0.1875D, 0.9375D, 0.375D, 0.25D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Direction.Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });

   public BreadBox(Settings settings) {
      super(settings);
   }

   public int size() {
      return 1;
   }

   public Identifier type() {
      return null;
   }

   public Direction[] unAllowedDirections() {
      return new Direction[0];
   }

   public boolean canInsertStack(ItemStack stack) {
      return stack.contains(DataComponentTypes.FOOD) || stack.getItem() instanceof BlockItem;
   }

   public int getSection(Float x, Float y) {
      return 0;
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }
}
