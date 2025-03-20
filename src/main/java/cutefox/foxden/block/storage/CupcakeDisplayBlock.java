package cutefox.foxden.block.storage;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CupcakeDisplayBlock extends StorageBlock {
   public static final MapCodec<CupcakeDisplayBlock> CODEC = createCodec(CupcakeDisplayBlock::new);
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375D, 0.0D, 0.4375D, 1.0D, 0.875D, 0.5625D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0D, 0.0D, 0.4375D, 0.0625D, 0.875D, 0.5625D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625D, 0.0625D, 0.3125D, 0.9375D, 0.375D, 0.6875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625D, 0.4375D, 0.3125D, 0.9375D, 0.75D, 0.6875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625D, 0.8125D, 0.4375D, 0.9375D, 0.875D, 0.5625D));
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });

   public CupcakeDisplayBlock(Settings settings) {
      super(settings);
   }

   public int size() {
      return 6;
   }

   public Identifier type() {
      return Utils.id("cupcake_display_block");
   }

   public Direction[] unAllowedDirections() {
      return new Direction[]{Direction.DOWN, Direction.UP};
   }

   public boolean canInsertStack(ItemStack stack) {
      return !(stack.getItem() instanceof BlockItem);
   }

   public int getSection(Float x, Float y) {
      int i = y >= 0.5F ? 0 : 1;
      int j = getXSection(x);
      return j + i * 3;
   }

   private static int getXSection(float f) {
      if (f < 0.375F) {
         return 2;
      } else {
         return f < 0.6875F ? 1 : 0;
      }
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
   }

   protected BlockState rotate(BlockState state, BooleanBiFunction rotation) {
      return (BlockState)state.with(FACING, (Direction)state.get(FACING));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }
}
