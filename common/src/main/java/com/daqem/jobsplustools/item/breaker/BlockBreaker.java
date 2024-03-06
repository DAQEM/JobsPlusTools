package com.daqem.jobsplustools.item.breaker;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface BlockBreaker {

    EntityDataAccessor<Boolean> BREAKER = SynchedEntityData.defineId(ServerPlayer.class, EntityDataSerializers.BOOLEAN);

    default void breakBlocks(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
    }

    default void breakBlock(ServerPlayer player, BlockPos pos, Level level, BlockState blockState) {
        if (player.getEntityData().hasItem(BREAKER)) {
            player.getEntityData().set(BREAKER, true);
        } else {
            player.getEntityData().define(BREAKER, true);
        }

        boolean bl = level.destroyBlock(pos, false);
        if (bl) {
            blockState.getBlock().destroy(level, pos, blockState);
        }

        if (!player.isCreative()) {
            Vec3 offsetPos = new Vec3(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
            BlockEntity blockEntity = level.getBlockState(pos).hasBlockEntity() ? level.getBlockEntity(pos) : null;

            List<ItemStack> droppedStacks = Block.getDrops(blockState, (ServerLevel) level, pos, blockEntity, player, player.getMainHandItem());

            dropItems(player, level, droppedStacks, offsetPos);
            blockState.spawnAfterBreak((ServerLevel) level, pos, player.getMainHandItem(), true);

            ItemStack itemStack = player.getMainHandItem();
            boolean usingEffectiveTool = player.hasCorrectToolForDrops(blockState);
            itemStack.mineBlock(level, blockState, pos, player);
            if (usingEffectiveTool) {
                player.awardStat(Stats.BLOCK_MINED.get(blockState.getBlock()));
                player.causeFoodExhaustion(0.005F);
            }

        }

        player.getEntityData().set(MultiBlockBreaker.BREAKER, false);

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
}
