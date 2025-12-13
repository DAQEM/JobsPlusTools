package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.PotionStorageItemComponent;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

public class WandItem extends Item {

    private final int maxCapacity;

    public WandItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.tool(toolMaterial, TagKey.create(Registries.BLOCK, JobsPlusTools.getId("mineable/wand")), attackDamage, attackSpeed, 0.0F));
        this.maxCapacity = getCapacityForToolMaterial(toolMaterial);
    }

    private int getCapacityForToolMaterial(ToolMaterial toolMaterial) {
        if (toolMaterial == ToolMaterial.WOOD) return 3;
        if (toolMaterial == ToolMaterial.STONE) return 9;
        if (toolMaterial == ToolMaterial.COPPER) return 9;
        if (toolMaterial == ToolMaterial.IRON) return 27;
        if (toolMaterial == ToolMaterial.GOLD) return 27;
        if (toolMaterial == ToolMaterial.DIAMOND) return 64;
        if (toolMaterial == ToolMaterial.NETHERITE) return 128;
        return 3;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        Player player = context.getPlayer();

        if (player != null && player.isCrouching() && blockEntity instanceof BrewingStandBlockEntity brewingStand) {
            ItemStack stack = context.getItemInHand();
            boolean changed = false;
            
            for (int i = 0; i < 3; i++) {
                PotionStorageItemComponent component = stack.getOrDefault(
                    JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), 
                    new PotionStorageItemComponent(PotionContents.EMPTY, 0, maxCapacity, false)
                );

                ItemStack potionStack = brewingStand.getItem(i);
                if (potionStack.isEmpty()) continue;

                boolean isSplash = potionStack.getItem() == Items.SPLASH_POTION || potionStack.getItem() == Items.POTION;
                boolean isLingering = potionStack.getItem() == Items.LINGERING_POTION;

                if (!isSplash && !isLingering) continue;

                PotionContents potionContents = potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                if (potionContents == PotionContents.EMPTY) continue;

                if (component.addPotion(stack, potionContents, isLingering) == InteractionResult.SUCCESS) {
                    potionStack.shrink(1);
                    brewingStand.setItem(i, potionStack);
                    changed = true;
                }
            }

            if (changed) {
                level.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        
        if (player.isCrouching()) {
            return InteractionResult.PASS;
        }

        PotionStorageItemComponent component = stack.get(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get());

        if (component != null && component.charges() > 0) {
            if (!level.isClientSide()) {
                ItemStack projectileStack = new ItemStack(component.isLingering() ? Items.LINGERING_POTION : Items.SPLASH_POTION);
                projectileStack.set(DataComponents.POTION_CONTENTS, component.contents());

                Entity entity = (component.isLingering() ? EntityType.LINGERING_POTION : EntityType.SPLASH_POTION).create(level, EntitySpawnReason.EVENT);
                if (entity instanceof ThrowableItemProjectile thrownPotion) {
                    thrownPotion.setItem(projectileStack);
                    thrownPotion.setOwner(player);
                    thrownPotion.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                    thrownPotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
                    level.addFreshEntity(thrownPotion);
                }
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!player.getAbilities().instabuild) {
                if (component.consumeCharge(stack) == InteractionResult.CONSUME) {
                    player.setItemInHand(usedHand, stack);
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(level, player, usedHand);
    }
}
