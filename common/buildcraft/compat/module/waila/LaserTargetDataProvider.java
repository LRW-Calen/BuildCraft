package buildcraft.compat.module.waila;

import buildcraft.api.mj.ILaserTarget;
import buildcraft.api.mj.MjAPI;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

abstract class LaserTargetDataProvider
{

    static class BodyProvider extends BaseWailaDataProvider.BodyProvider
    {
        @Override
        public void getWailaBody(ITooltip currentTip, BlockAccessor accessor, IPluginConfig iPluginConfig)
        {
            BlockEntity tile = accessor.getBlockEntity();
            if (tile instanceof ILaserTarget)
            {
                CompoundTag nbt = accessor.getServerData();
                if (nbt.contains("required_power", Tag.TAG_LONG))
                {
                    long power = nbt.getLong("required_power");
                    if (power > 0L)
                    {
                        currentTip.add(new TranslatableComponent("buildcraft.waila.waiting_for_laser", MjAPI.formatMj(power)));
                    }
                }
            }
//            else
//            {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider
    {
        @Override
        public void getNBTData(CompoundTag nbt, ServerPlayer player, Level world, BlockEntity tile, boolean showDetails)
        {
            if (tile instanceof ILaserTarget target)
            {
                nbt.putLong("required_power", target.getRequiredLaserPower());
            }
        }
    }
}
