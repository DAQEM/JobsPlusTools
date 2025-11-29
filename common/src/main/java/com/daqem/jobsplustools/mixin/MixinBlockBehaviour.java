package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.breaker.BlockBreaker;
import com.daqem.jobsplustools.item.breaker.ConnectedBlockBreaker;
import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectBlockBreakerModes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(value = BlockBehaviour.class, priority = 900)
public class MixinBlockBehaviour {

    @ModifyExpressionValue(
            method = "getDestroyProgress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"
            )
    )
    private float onGetDestroyProgress(float original, BlockState blockState, Player player) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.isCorrectToolForDrops(blockState)) {
            Level level = player.level();
            if (itemStack.getItem() instanceof MultiBlockBreaker multiBlockBreaker) {
                Set<BlockPos> blocksToMine = multiBlockBreaker.getBlocksToMine(player, level);
                if (blocksToMine.size() > 1) {
                    return jobsplustools$getNewDestroySpeed(original, blockState, player, level, blocksToMine);
                }
            }
            if (itemStack.getItem() instanceof ConnectedBlockBreaker connectedBlockBreaker) {
                if (connectedBlockBreaker.getActiveMode(itemStack) == ConnectBlockBreakerModes.ON) {
                    Set<BlockPos> blocksToMine = connectedBlockBreaker.getBlocksToMine(player, level);
                    if (blocksToMine.size() > 1) {
                        return jobsplustools$getNewDestroySpeed(original, blockState, player, level, blocksToMine);
                    }
                }
            }
        }
        return original;
    }

    @Unique
    private float jobsplustools$getNewDestroySpeed(float original, BlockState blockState, Player player, Level level, Set<BlockPos> blocksToMine) {
        BlockHitResult blockHitResult = BlockBreaker.getBlockHitResult(player, level);
        float targetHardness = blockState.getDestroySpeed(level, blockHitResult.getBlockPos());
        float totalHardness = blocksToMine.stream()
                .map(pos -> level.getBlockState(pos).getDestroySpeed(level, pos))
                .reduce(0.0f, Float::sum);

        if (totalHardness > 0) {
            return (original * targetHardness * 2.0f) / totalHardness;
        }
        return original;
    }
}
