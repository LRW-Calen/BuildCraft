package buildcraft.compat.module.waila;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

//class BaseWailaDataProvider implements IWailaDataProvider
interface BaseWailaDataProvider
{

    static abstract class BodyProvider implements IComponentProvider
    {
        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig)
        {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(ITooltip iTooltip, BlockAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProvider implements IServerDataProvider<BlockEntity>
    {
        @Override
        public void appendServerData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails)
        {
            getNBTData(tag, player, blockAccessor, blockEntity, showDetails);
        }

        abstract void getNBTData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails);
    }
}