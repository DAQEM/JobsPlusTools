package com.daqem.jobsplustools.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.daqem.jobsplustools.item.component.ExperienceItemComponent;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.mode.type.BlockBreakerType;
import com.daqem.jobsplustools.item.mode.type.IModeType;
import com.daqem.jobsplustools.item.mode.type.replacer.BlockReplacerType;
import com.daqem.jobsplustools.util.ExperienceHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> arg, Item.TooltipContext arg2, TooltipDisplay arg3, Consumer<Component> consumer, TooltipFlag arg4);

    @Inject(
            method = "addDetailsToTooltip",
            at = @At(value = "HEAD")
    )
    private void jobsplustools$addDetailsToTooltip(Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Player player, TooltipFlag tooltipFlag, Consumer<Component> consumer, CallbackInfo ci) {
        this.addToTooltip(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get(), tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        this.addToTooltip(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get(), tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        this.addToTooltip(JobsPlusToolsDataComponentTypes.POTION_STORAGE_ITEM_COMPONENT.get(), tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }

    @ModifyExpressionValue(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"
            )
    )
    private InteractionResult jobsplustools$modifyUseInteractionResult(InteractionResult original, Level level, Player player, InteractionHand interactionHand) {
        if (!original.consumesAction() && player instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = (ItemStack) (Object) this;
            if (interactionHand == InteractionHand.MAIN_HAND && itemStack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
                ModeItemComponent modeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
                if (modeItemComponent != null) {
                    if (serverPlayer.isCrouching()) {
                        if (modeItemComponent.switchMode(itemStack)) {
                            ModeItemComponent newModeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());
                            if (newModeItemComponent != null) {
                                IModeType modeType = newModeItemComponent.getModeType();
                                IMode selectedMode = modeType.getSelectedMode(newModeItemComponent);
                                serverPlayer.sendSystemMessage(selectedMode.getName().withStyle(ChatFormatting.GREEN), true);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    } else if (modeItemComponent.getModeType() instanceof BlockReplacerType blockReplacer) {
                        IMode selectedMode = blockReplacer.getSelectedMode(modeItemComponent);
                        BlockHitResult blockHitResult = BlockBreakerType.getBlockHitResult(player, level);
                        blockReplacer.replaceBlocks(selectedMode, serverPlayer, player.level(), blockHitResult.getBlockPos());
                    }
                }
            }
            if (itemStack.has(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get())) {
                ExperienceItemComponent experienceItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.EXPERIENCE_ITEM_COMPONENT.get());
                if (experienceItemComponent != null) {
                    if (player.isCrouching()) {
                        int totalExperienceForNextLevel = serverPlayer.getXpNeededForNextLevel();
                        float progressToNextLevel = serverPlayer.experienceProgress;
                        int experienceNeededForNextLevel = Math.max(totalExperienceForNextLevel - Mth.floor(progressToNextLevel * totalExperienceForNextLevel), 1);
                        int experienceInJar = experienceItemComponent.experience();
                        int experienceToExtract = Math.min(experienceInJar, experienceNeededForNextLevel);
                        experienceItemComponent.extractExperience(itemStack, experienceToExtract);
                        serverPlayer.giveExperiencePoints(experienceToExtract);
                    } else {
                        int totalExperience = (int) (ExperienceHandler.getExperienceForLevel(serverPlayer.experienceLevel) + (serverPlayer.experienceProgress * serverPlayer.getXpNeededForNextLevel()));
                        int jarCapacity = experienceItemComponent.capacity();
                        int experienceInJar = experienceItemComponent.experience();
                        int spaceInJar = jarCapacity - experienceInJar;
                        int experienceToInsert = Math.min(totalExperience, spaceInJar);
                        experienceItemComponent.insertExperience(itemStack, experienceToInsert);
                        serverPlayer.giveExperiencePoints(-experienceToInsert);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return original;
    }
}
