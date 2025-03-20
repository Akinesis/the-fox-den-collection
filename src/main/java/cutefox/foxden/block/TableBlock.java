package cutefox.foxden.block;

import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.AbstractBlock.Settings;

public class TableBlock extends Block {
   private final VoxelShape SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0.4375D, 0.1875D, 0.4375D, 0.5625D, 0.8125D, 0.5625D), VoxelShapes.cuboid(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D));

   public TableBlock(Settings settings) {
      super(settings);
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.SHAPE;
   }
}
