package cutefox.foxden.block;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.block.entity.CustomEnchantingTableBlockEntity;
import cutefox.foxden.registery.ModBlockEntityType;
import cutefox.foxden.screen.CustomEnchantmentScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Nameable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomEnchantingTable extends BlockWithEntity {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final MapCodec<CustomEnchantingTable> CODEC = createCodec(CustomEnchantingTable::new);

    public static final List<BlockPos> POWER_PROVIDER_OFFSETS = BlockPos.stream(-2, 0, -2, 2, 1, 2).filter((pos) -> {
        return Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2;
    }).map(BlockPos::toImmutable).toList();

    public CustomEnchantingTable(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<CustomEnchantingTable> getCodec() {
        return CODEC;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntityType.CUSTOM_ENCHANTING_TABLE, CustomEnchantingTableBlockEntity::tick) : null;
    }

    public static boolean canAccessPowerProvider(World world, BlockPos tablePos, BlockPos providerOffset) {
        return world.getBlockState(tablePos.add(providerOffset)).isIn(BlockTags.ENCHANTMENT_POWER_PROVIDER) && world.getBlockState(tablePos.add(providerOffset.getX() / 2, providerOffset.getY(), providerOffset.getZ() / 2)).isIn(BlockTags.ENCHANTMENT_POWER_TRANSMITTER);
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    //On right click
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            return ActionResult.CONSUME;
        }
    }

    //Action to do on right click
    @Nullable
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomEnchantingTableBlockEntity) {
            Text text = ((Nameable)blockEntity).getDisplayName();
            return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new CustomEnchantmentScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), text);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomEnchantingTableBlockEntity(pos, state);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
