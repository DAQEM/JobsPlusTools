package com.daqem.jobsplustools.item.breaker;

import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface BlockBreaker {

    default void breakBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
    }

    default void breakBlock(ServerPlayer player, BlockPos pos, Level level) {
        if (player instanceof JobsPlusToolsPlayer extension) {
            BlockState state = level.getBlockState(pos);
            extension.jobsplustools$setBreakingBlock(true);
            player.gameMode.destroyBlock(pos);
            level.levelEvent(2001, pos, Block.getId(state));
            extension.jobsplustools$setBreakingBlock(false);
        }
    }

    private static void dropItems(Player player, Level level, List<ItemStack> stacks, Vec3 pos) {
        for(ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), stack);
                level.addFreshEntity(itemEntity);
            }
        }

        if(!stacks.isEmpty()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
    }

    default BlockHitResult getBlockHitResult(Player player, Level level) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 target = eyePos.add(viewVec.x * 5, viewVec.y * 5, viewVec.z * 5);
        return level.clip(new ClipContext(eyePos, target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}
