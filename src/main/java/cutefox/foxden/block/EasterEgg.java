package cutefox.foxden.block;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EasterEgg extends HorizontalFacingBlock {

    public static final MapCodec<EasterEgg> CODEC = createCodec(EasterEgg::new);
            private static final VoxelShape SHAPE = Block.createCuboidShape(6,0,6,12,10,12);

    public EasterEgg(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        ItemStack easterEgg = new ItemStack( ModItems.EASTER_EGG);

        if(state.getBlock().equals(ModBlocks.EASTER_EGG))
            easterEgg = new ItemStack( ModItems.EASTER_EGG);

        if(state.getBlock().equals(ModBlocks.CHOCOLATE_CHICKEN))
            easterEgg = new ItemStack( ModItems.CHOCOLATE_CHICKEN);

        if(state.getBlock().equals(ModBlocks.CHOCOLATE_RABBIT))
            easterEgg = new ItemStack( ModItems.CHOCOLATE_RABBIT);

        if(state.getBlock().equals(ModBlocks.WHITE_CHOCOLATE_RABBIT))
            easterEgg = new ItemStack( ModItems.WHITE_CHOCOLATE_RABBIT);


        Direction direction = player.getFacing().getOpposite();
        double xMotion = (double)direction.getOffsetX() * 0.06D;
        double yMotion = 0.15D;
        double zMotion = (double)direction.getOffsetZ() * 0.06D;
        Utils.spawnSlice(world, easterEgg, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, xMotion, yMotion, zMotion);

        world.removeBlock(pos,false);
        return ActionResult.CONSUME;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
