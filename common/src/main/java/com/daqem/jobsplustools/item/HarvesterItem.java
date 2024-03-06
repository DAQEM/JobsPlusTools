package com.daqem.jobsplustools.item;

import com.daqem.jobsplustools.JobsPlusTools;
import com.daqem.jobsplustools.item.mode.IMode;
import com.daqem.jobsplustools.item.replacer.MultiBlockReplacer;
import com.daqem.jobsplustools.item.replacer.result.ReplaceableResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HarvesterItem extends HoeItem implements MultiBlockReplacer {

    public HarvesterItem(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties.arch$tab(JobsPlusTools.JOBSPLUS_TOOLS_TAB.get()));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && player instanceof ServerPlayer serverPlayer) switchMode(serverPlayer, player.getItemInHand(hand));
        return super.use(level, player, hand);
    }

    @Override
    public List<IMode> getAvailableModes() {
        return MultiBlockReplacer.generateAvailableModes(getTier());
    }

    @Override
    public ReplaceableResult isReplaceable(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            return ReplaceableResult.breakAndPlace();
        }
        else if (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == NetherWartBlock.MAX_AGE) {
            return ReplaceableResult.breakAndPlace();
        }
        else if (state.getBlock() instanceof StemGrownBlock) {
            return ReplaceableResult.onlyBreak();
        }
        else {
            return ReplaceableResult.none();
        }
    }
}
