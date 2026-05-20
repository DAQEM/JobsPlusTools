package com.daqem.jobsplustools.fabric.mixin;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.breaker.connected.ConnectedBlockBreakerModes;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.jobsplustools.item.mode.type.breaker.ConnectedBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.breaker.MultiBlockBreakerType;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
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
    private float onGetDestroyProgress(float original, BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.isCorrectToolForDrops(blockState) && itemStack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
            if (modeItemComponent == null) return original;
            IModeType modeType = modeItemComponent.getModeType();
            IMode selectedMode = modeType.getSelectedMode(modeItemComponent);
            Level level = player.level();
            if (modeType instanceof MultiBlockBreakerType multiBlockBreaker) {
                Set<BlockPos> blocksToMine = multiBlockBreaker.getBlocksToMine(selectedMode, player, level, blockPos);
                if (blocksToMine.size() > 1) {
                    return jobsplustools$getNewDestroySpeed(original, blockState, player, level, blocksToMine);
                }
            }
            if (modeType instanceof ConnectedBlockBreakerType connectedBlockBreaker) {
                if (selectedMode == ConnectedBlockBreakerModes.ON) {
                    Set<BlockPos> blocksToMine = connectedBlockBreaker.getBlocksToMine(selectedMode, player, level, blockPos);
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
        BlockHitResult blockHitResult = BlockBreakerType.getBlockHitResult(player, level);
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
