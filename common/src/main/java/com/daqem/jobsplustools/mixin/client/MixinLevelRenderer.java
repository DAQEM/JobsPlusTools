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
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Inject(at = @At("TAIL"), method = "submitBlockOutline")
    private void submitBlockOutline(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LevelRenderState levelRenderState, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        ClientLevel level = minecraft.level;
        if (player == null || level == null) return;

        BlockOutlineRenderState state = levelRenderState.blockOutlineRenderState;
        if (state == null) return;

        HitResult hitResult = minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult blockHitResult) || hitResult.getType() != HitResult.Type.BLOCK)
            return;

        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeItemComponent = mainHandItem.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
            if (modeItemComponent == null) return;

            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
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
                        extraBlocks = breaker.getBlocksToMine(selectedMode, player, level, blockPos);
                case ConnectedBlockBreakerType breaker ->
                        extraBlocks = breaker.getBlocksToMine(selectedMode, player, level, blockPos);
                case MultiBlockReplacerType replacer ->
                        extraBlocks = replacer.getBlocksToMine(selectedMode, player, level, blockPos).stream()
                                .filter(pos -> !pos.equals(blockPos))
                                .filter(pos -> {
                                    BlockState stateAtPos = level.getBlockState(pos);
                                    ReplaceableResult result = replacer.isReplaceable(stateAtPos);
                                    return result.shouldBreak() || result.shouldPlace();
                                })
                                .collect(Collectors.toSet());
                case MultiBlockPlacerType placer ->
                        extraBlocks = placer.getBlocksToPlace(selectedMode, player, level, blockPos, blockHitResult.getDirection());
                default -> {
                }
            }

            if (extraBlocks.isEmpty()) return;

            Vec3 cameraPos = levelRenderState.cameraRenderState.pos;
            int color = state.highContrast() ? -11010079 : ARGB.black(102);
            float lineWidth = minecraft.getWindow().getAppropriateLineWidth();
            boolean afterTerrain = state.isTranslucent();

            for (BlockPos pos : extraBlocks) {
                if (pos.equals(blockPos)) continue;

                VoxelShape shape;
                if (modeType instanceof MultiBlockPlacerType) {
                    // Render full cube for placement preview
                    shape = Shapes.block();
                } else {
                    BlockState stateAtPos = level.getBlockState(pos);
                    if (stateAtPos.isAir() || !level.getWorldBorder().isWithinBounds(pos)) continue;
                    shape = stateAtPos.getShape(level, pos, CollisionContext.of(player));
                }

                if (shape.isEmpty()) continue;

                poseStack.pushPose();
                poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
                submitNodeCollector.submitShapeOutline(poseStack, shape, RenderTypes.lines(), color, lineWidth, afterTerrain);
                poseStack.popPose();
            }
        }
    }
}