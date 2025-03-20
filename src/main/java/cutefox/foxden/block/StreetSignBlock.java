package cutefox.foxden.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.util.Formatting;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.text.Text;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;

public class StreetSignBlock extends HorizontalFacingBlock {
   public static final MapCodec<StreetSignBlock> CODEC = createCodec(StreetSignBlock::new);
   private static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 14.0D, 15.0D, 14.0D);

   public StreetSignBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FACING});
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
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

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
   }
}
