package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.entity.JobsPlusToolsFishingHook;
import com.daqem.jobsplustools.item.component.JobsPlusToolsDataComponentTypes;
import com.daqem.jobsplustools.item.component.ModeItemComponent;
import com.daqem.jobsplustools.item.mode.fishing.MultiBobberModes;
import com.daqem.jobsplustools.item.mode.type.fishing.MultiBobberType;
import com.daqem.jobsplustools.player.JobsPlusToolsPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FishingRodItem extends net.minecraft.world.item.FishingRodItem {

    public FishingRodItem(ToolMaterial toolMaterial, Properties properties) {
        super(properties.durability(toolMaterial.durability()).enchantable(1));
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) return InteractionResult.PASS;
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.has(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get())) {
            ModeItemComponent modeItemComponent = itemStack.get(JobsPlusToolsDataComponentTypes.MODE_ITEM_COMPONENT.get());

            if (modeItemComponent != null && modeItemComponent.getModeType() instanceof MultiBobberType && player instanceof JobsPlusToolsPlayer jobsPlusToolsPlayer) {
                List<JobsPlusToolsFishingHook> fishingHooks = jobsPlusToolsPlayer.jobsplustools$getFishingHooks();

                if (!fishingHooks.isEmpty()) {
                    if (!level.isClientSide()) {
                        int damage = 0;
                        boolean anyHookHasCatch = fishingHooks.stream().anyMatch(hook -> hook.nibble > 0);
                        for (int i = fishingHooks.size() - 1; i >= 0; i--) {
                            JobsPlusToolsFishingHook fishingHook = fishingHooks.get(i);
                            if (fishingHook.nibble > 0 || !anyHookHasCatch) {
                                damage += fishingHook.retrieve(itemStack);
                            }
                        }
                        itemStack.hurtAndBreak(damage, player, hand.asEquipmentSlot());
                    }

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.FISHING_BOBBER_RETRIEVE,
                            SoundSource.NEUTRAL,
                            1.0F,
                            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
                    );
                    player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                }
                else {
                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.FISHING_BOBBER_THROW,
                            SoundSource.NEUTRAL,
                            0.5F,
                            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
                    );
                    if (level instanceof ServerLevel serverLevel) {
                        int modeIndex = modeItemComponent.availableModes().get(modeItemComponent.selectedMode());
                        MultiBobberModes selectedMode = MultiBobberModes.values()[modeIndex];
                        int bobberAmount = selectedMode.getAmount();
                        int lure = (int) (EnchantmentHelper.getFishingTimeReduction(serverLevel, itemStack, player) * 20.0F);
                        int luck = EnchantmentHelper.getFishingLuckBonus(serverLevel, itemStack, player);
                        for (int i = 0; i < bobberAmount; i++) {
                            boolean canFishInLava = itemStack.is(JobsPlusToolsItems.NETHERITE_FISHING_ROD.get());
                            JobsPlusToolsFishingHook fishingHook = new JobsPlusToolsFishingHook(player, level, luck, lure, canFishInLava, i, bobberAmount);
                            Projectile.spawnProjectile(fishingHook, serverLevel, itemStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.gameEvent(GameEvent.ITEM_INTERACT_START);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}