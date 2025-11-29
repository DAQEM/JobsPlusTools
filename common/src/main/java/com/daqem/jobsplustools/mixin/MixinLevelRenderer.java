package com.daqem.jobsplustools.mixin;

import com.daqem.jobsplustools.item.breaker.ConnectedBlockBreaker;
import com.daqem.jobsplustools.item.breaker.MultiBlockBreaker;
import com.daqem.jobsplustools.item.replacer.MultiBlockReplacer;
import com.daqem.jobsplustools.item.replacer.result.ReplaceableResult;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ClientLevel level;

    @Inject(at = @At("TAIL"), method = "renderBlockOutline")
    private void renderBlockOutline(MultiBufferSource.BufferSource bufferSource, PoseStack poseStack, boolean bl, LevelRenderState levelRenderState, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player == null || this.level == null) return;

        HitResult hitResult = this.minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult blockHitResult) || hitResult.getType() != HitResult.Type.BLOCK) return;

        ItemStack mainHandItem = player.getMainHandItem();
        Item item = mainHandItem.getItem();
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.level.getBlockState(blockPos);

        if (!mainHandItem.isCorrectToolForDrops(blockState)) {
            if (item instanceof MultiBlockReplacer multiBlockReplacer) {
                ReplaceableResult result = multiBlockReplacer.isReplaceable(blockState);
                if (!result.shouldBreak() && !result.shouldPlace()) {
                    return;
                }
            } else {
                return;
            }
        }

        Set<BlockPos> extraBlocks = Collections.emptySet();

        switch (item) {
            case MultiBlockBreaker breaker -> extraBlocks = breaker.getBlocksToMine(player, this.level);
            case ConnectedBlockBreaker breaker -> extraBlocks = breaker.getBlocksToMine(player, this.level);
            case MultiBlockReplacer replacer -> extraBlocks = replacer.getBlocksToReplace(player, blockPos).stream()
                    .filter(pos -> !pos.equals(blockPos))
                    .filter(pos -> {
                        BlockState state = this.level.getBlockState(pos);
                        ReplaceableResult result = replacer.isReplaceable(state);
                        return result.shouldBreak() || result.shouldPlace();
                    })
                    .collect(Collectors.toSet());
            default -> {
            }
        }

        if (extraBlocks.isEmpty()) return;

        Vec3 cameraPos = levelRenderState.cameraRenderState.pos;
        VertexConsumer linesBuffer = bufferSource.getBuffer(RenderType.lines());

        // Standard vanilla selection color (Black with ~40% opacity)
        int color = ARGB.color(102, -16777216);

        for (BlockPos pos : extraBlocks) {
            // Skip the block specifically being looked at, as vanilla already renders it
            if (pos.equals(blockPos)) continue;

            BlockState state = this.level.getBlockState(pos);
            if (state.isAir() || !this.level.getWorldBorder().isWithinBounds(pos)) continue;

            VoxelShape shape = state.getShape(this.level, pos, CollisionContext.of(player));
            if (shape.isEmpty()) continue;

            jobsPlusTools$renderHitOutline(poseStack, linesBuffer, cameraPos.x, cameraPos.y, cameraPos.z, pos, shape, color);
        }
    }

    @Unique
    private void jobsPlusTools$renderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, double camX, double camY, double camZ, BlockPos pos, VoxelShape shape, int color) {
        ShapeRenderer.renderShape(
                poseStack,
                vertexConsumer,
                shape,
                pos.getX() - camX,
                pos.getY() - camY,
                pos.getZ() - camZ,
                color
        );
    }
}