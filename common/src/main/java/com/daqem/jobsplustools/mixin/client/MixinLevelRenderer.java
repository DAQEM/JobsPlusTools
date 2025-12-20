package com.daqem.jobsplustools.mixin.client;

import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.jobsplustools.item.mode.type.breaker.ConnectedBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.breaker.MultiBlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.placer.MultiBlockPlacerType;
import com.daqem.jobsplustools.item.mode.type.replacer.MultiBlockReplacerType;
import com.daqem.jobsplustools.item.mode.type.replacer.result.ReplaceableResult;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Collections;
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
        if (!(hitResult instanceof BlockHitResult blockHitResult) || hitResult.getType() != HitResult.Type.BLOCK)
            return;

        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeItemComponent = mainHandItem.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
            if (modeItemComponent == null) return;

            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = this.level.getBlockState(blockPos);
            IModeType modeType = modeItemComponent.getModeType();
            if (modeType == null) return;
            IMode selectedMode = modeType.getSelectedMode(modeItemComponent);
            if (selectedMode == null) return;

            // For Placer, we don't need correct tool check, but for others we might
            if (!(modeType instanceof MultiBlockPlacerType) && !mainHandItem.isCorrectToolForDrops(blockState)) {
                if (modeType instanceof MultiBlockReplacerType multiBlockReplacer) {
                    ReplaceableResult result = multiBlockReplacer.isReplaceable(blockState);
                    if (!result.shouldBreak() && !result.shouldPlace()) {
                        return;
                    }
                } else {
                    return;
                }
            }

            Collection<BlockPos> extraBlocks = Collections.emptyList();

            switch (modeType) {
                case MultiBlockBreakerType breaker ->
                        extraBlocks = breaker.getBlocksToMine(selectedMode, player, this.level, blockPos);
                case ConnectedBlockBreakerType breaker ->
                        extraBlocks = breaker.getBlocksToMine(selectedMode, player, this.level, blockPos);
                case MultiBlockReplacerType replacer ->
                        extraBlocks = replacer.getBlocksToMine(selectedMode, player, this.level, blockPos).stream()
                                .filter(pos -> !pos.equals(blockPos))
                                .filter(pos -> {
                                    BlockState state = this.level.getBlockState(pos);
                                    ReplaceableResult result = replacer.isReplaceable(state);
                                    return result.shouldBreak() || result.shouldPlace();
                                })
                                .collect(Collectors.toSet());
                case MultiBlockPlacerType placer ->
                        extraBlocks = placer.getBlocksToPlace(selectedMode, player, this.level, blockPos, blockHitResult.getDirection());
                default -> {
                }
            }

            if (extraBlocks.isEmpty()) return;

            Vec3 cameraPos = levelRenderState.cameraRenderState.pos;
            VertexConsumer linesBuffer = bufferSource.getBuffer(RenderTypes.lines());

            int color = ARGB.color(102, -16777216);

            for (BlockPos pos : extraBlocks) {
                if (pos.equals(blockPos)) continue;

                VoxelShape shape;
                if (modeType instanceof MultiBlockPlacerType) {
                    // Render full cube for placement preview
                    shape = Shapes.block();
                } else {
                    BlockState state = this.level.getBlockState(pos);
                    if (state.isAir() || !this.level.getWorldBorder().isWithinBounds(pos)) continue;
                    shape = state.getShape(this.level, pos, CollisionContext.of(player));
                }

                if (shape.isEmpty()) continue;

                jobsPlusTools$renderHitOutline(poseStack, linesBuffer, cameraPos.x, cameraPos.y, cameraPos.z, pos, shape, color);
            }
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
                color,
                this.minecraft.getWindow().getAppropriateLineWidth()
        );
    }
}