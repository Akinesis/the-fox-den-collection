package cutefox.foxden.Utils;

import cutefox.foxden.registery.ModEntityAttributes;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BonusDropHelper {

    public static boolean handleBlockBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {

        if (!(world instanceof ServerWorld serverWorld) || !ConfigBuilder.moreAttributes())
            return true;


        if (blockState.isIn(BlockTags.CROPS)) {
            CropBlock cropBlock = (CropBlock) blockState.getBlock();
            handleBonusCropDrop(serverWorld, player, blockPos, blockState, blockEntity, cropBlock);
        }

        if (blockState.isIn(ConventionalBlockTags.ORES)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_MINING));
        }

        if (blockState.isIn(BlockTags.LOGS)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_LOGGING));
        }

        if (blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_SHOVELING));
        }

        //Breaking bonus stack on top of other bonus, offering additional chance of double drop.
        handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_BREAKING));

        return true;
    }

    private static void handleBonusCropDrop(ServerWorld serverWorld, PlayerEntity player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, CropBlock cropBlock) {
        float playerBonusCropDrop = (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_HARVEST);
        Random rand = Random.create();

        if (rand.nextFloat() < playerBonusCropDrop) {
            if (cropBlock.isMature(blockState)) {

                CropBlock.dropStacks(blockState, serverWorld, blockPos, blockEntity, player, player.getMainHandStack());

            }
        }
    }

    private static void handleBonusDrop(ServerWorld serverWorld, PlayerEntity player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, float playerBonusCropDrop) {
        Random rand = Random.create();

        if (rand.nextFloat() < playerBonusCropDrop) {
            Block.dropStacks(blockState, serverWorld, blockPos, blockEntity, player, player.getMainHandStack());
        }
    }

}
