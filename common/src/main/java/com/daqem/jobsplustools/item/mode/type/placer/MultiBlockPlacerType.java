package com.daqem.jobsplustools.item.mode.type.placer;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.placer.MultiBlockPlacerMode;
import com.daqem.jobsplustools.item.mode.placer.MultiBlockPlacerModes;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiBlockPlacerType implements IModeType {
    @Override
    public Identifier getId() {
        return JobsPlusTools.API.getId("multi_block_placer");
    }

    @Override
    public Class<? extends IMode> getModeClass() {
        return MultiBlockPlacerModes.class;
    }

    public InteractionResult onPlace(IMode selectedMode, UseOnContext context) {
        if (!(selectedMode instanceof MultiBlockPlacerMode mode)) return InteractionResult.PASS;

        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();

        List<BlockPos> targetPositions = getBlocksToPlace(selectedMode, player, level, clickedPos, clickedFace);
        if (targetPositions.isEmpty()) return InteractionResult.FAIL;

        List<Integer> validSlots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                validSlots.add(i);
            }
        }

        if (validSlots.isEmpty()) return InteractionResult.FAIL;

        boolean success = false;

        for (BlockPos targetPos : targetPositions) {
            // Use a deterministic seed based on the block and player position and the item's damage value to ensure Client/Server sync
            // This prevents the visual flickering where client places Block A and server places Block B
            long seed = targetPos.asLong() + player.blockPosition().asLong() + context.getItemInHand().getDamageValue();
            RandomSource deterministicRandom = RandomSource.create(seed);

            // Start checking from a random slot to maintain the "Random Pattern" feature
            int startIndex = deterministicRandom.nextInt(validSlots.size());

            // Try all valid slots starting from the random index
            for (int i = 0; i < validSlots.size(); i++) {
                int slotIndex = validSlots.get((startIndex + i) % validSlots.size());
                ItemStack stackToPlace = player.getInventory().getItem(slotIndex);

                if (stackToPlace.isEmpty() || !(stackToPlace.getItem() instanceof BlockItem blockItem)) continue;

                // Create a fake HitResult at the center of the target position
                BlockHitResult fakeHitResult = new BlockHitResult(
                        new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5),
                        clickedFace,
                        targetPos,
                        false
                );

                BlockPlaceContext placeContext = new BlockPlaceContext(player, context.getHand(), stackToPlace, fakeHitResult);

                // Use the built-in BlockItem.place method to ensure everything (including data copying and sounds) is triggered correctly
                InteractionResult placeResult = blockItem.place(placeContext);

                if (placeResult.consumesAction()) {
                    // Damage the trowel slightly
                    context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                    success = true;

                    // Successfully placed a block at this position, move to next position
                    break;
                }
            }
        }

        return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public List<BlockPos> getBlocksToPlace(IMode selectedMode, Player player, Level level, BlockPos clickedPos, Direction clickedFace) {
        if (!(selectedMode instanceof MultiBlockPlacerMode mode)) return Collections.emptyList();

        List<BlockPos> blocks = new ArrayList<>();
        int radius = mode.getRadius();

        BlockPos centerPos = clickedPos.relative(clickedFace);

        Direction.Axis axis1;
        Direction.Axis axis2;

        float pitch = player.getXRot();

        if (clickedFace == Direction.UP) {
            if (pitch > 35) { // Looking down -> Floor (Horizontal)
                axis1 = Direction.Axis.X;
                axis2 = Direction.Axis.Z;
            } else { // Looking forward -> Wall (Vertical)
                axis1 = (player.getDirection().getAxis() == Direction.Axis.Z) ? Direction.Axis.X : Direction.Axis.Z;
                axis2 = Direction.Axis.Y;
            }
        } else if (clickedFace == Direction.DOWN) {
            if (pitch < -35) { // Looking up -> Ceiling (Horizontal)
                axis1 = Direction.Axis.X;
                axis2 = Direction.Axis.Z;
            } else { // Looking forward -> Wall (Vertical)
                axis1 = (player.getDirection().getAxis() == Direction.Axis.Z) ? Direction.Axis.X : Direction.Axis.Z;
                axis2 = Direction.Axis.Y;
            }
        } else { // Side faces
            if (pitch > 55) { // Looking straight down -> Floor
                axis1 = Direction.Axis.X;
                axis2 = Direction.Axis.Z;
            } else if (pitch < -55) { // Looking straight up -> Ceiling
                axis1 = Direction.Axis.X;
                axis2 = Direction.Axis.Z;
            } else { // Standard Wall
                if (clickedFace.getAxis() == Direction.Axis.Z) {
                    axis1 = Direction.Axis.X;
                    axis2 = Direction.Axis.Y;
                } else {
                    axis1 = Direction.Axis.Z;
                    axis2 = Direction.Axis.Y;
                }
            }
        }

        for (int u = -radius; u <= radius; u++) {
            for (int v = -radius; v <= radius; v++) {
                BlockPos targetPos = getOffsetPos(centerPos, axis1, axis2, u, v);

                if (level.getWorldBorder().isWithinBounds(targetPos)) {
                    BlockState currentState = level.getBlockState(targetPos);
                    if (currentState.canBeReplaced()) {
                        blocks.add(targetPos);
                    }
                }
            }
        }

        return blocks;
    }

    private BlockPos getOffsetPos(BlockPos origin, Direction.Axis axis1, Direction.Axis axis2, int u, int v) {
        int x = origin.getX();
        int y = origin.getY();
        int z = origin.getZ();

        if (axis1 == Direction.Axis.X) x += u;
        else if (axis1 == Direction.Axis.Y) y += u;
        else if (axis1 == Direction.Axis.Z) z += u;

        if (axis2 == Direction.Axis.X) x += v;
        else if (axis2 == Direction.Axis.Y) y += v;
        else if (axis2 == Direction.Axis.Z) z += v;

        return new BlockPos(x, y, z);
    }
}