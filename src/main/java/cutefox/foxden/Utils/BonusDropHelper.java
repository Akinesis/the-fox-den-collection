package cutefox.foxden.Utils;

import cutefox.foxden.registery.ModEntityAttributes;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class BonusDropHelper {

    public static boolean handleBlockBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {

        if (!(world instanceof ServerWorld serverWorld) || !ConfigBuilder.moreAttributes() || player.isInCreativeMode())
            return true;

        boolean canDoubleDrop = false;

        if (blockState.isIn(BlockTags.CROPS) || blockState.getBlock().equals(Blocks.MELON)) {
            CropBlock cropBlock = (CropBlock) blockState.getBlock();
            if (cropBlock.isMature(blockState)) {
                handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_HARVEST));
                canDoubleDrop = true;
            }
        }

        if (blockState.isIn(ConventionalBlockTags.ORES)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_MINING));
            canDoubleDrop = true;
        }

        if (blockState.isIn(BlockTags.LOGS)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_LOGGING));
            canDoubleDrop = true;
        }

        if (blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
            handleBonusDrop(serverWorld, player, blockPos, blockState, blockEntity, (float) player.getAttributeValue(ModEntityAttributes.PLAYER_BONUS_SHOVELING));
            canDoubleDrop = true;
        }

        //Breaking bonus stack on top of other bonus, offering additional chance of double drop.
        if(canDoubleDrop)
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

    private static void handleBonusDrop(ServerWorld serverWorld, PlayerEntity player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, float playerBonusDrop) {
        Random rand = Random.create();

        ItemStack mainHandStack = player.getMainHandStack();

        //If tool has silk touch, don't apply double drop chance (to limit item dupping)
        RegistryEntry<Enchantment> silkTouch = serverWorld.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.SILK_TOUCH).get();
        if(EnchantmentHelper.hasEnchantments(mainHandStack) && mainHandStack.getEnchantments().getEnchantments().contains(silkTouch))
            return;

        if (rand.nextBetween(0,100) < playerBonusDrop) {
            Block.dropStacks(blockState, serverWorld, blockPos, blockEntity, player, mainHandStack);
        }
    }

}
