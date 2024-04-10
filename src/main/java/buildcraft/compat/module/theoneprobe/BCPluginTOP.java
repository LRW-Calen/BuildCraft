package buildcraft.compat.module.theoneprobe;

import buildcraft.api.BCModules;
import buildcraft.api.mj.ILaserTarget;
import buildcraft.api.mj.MjAPI;
import buildcraft.compat.CompatUtils;
import buildcraft.lib.tile.craft.IAssemblyCraft;
import buildcraft.lib.tile.craft.IAutoCraft;
import com.google.common.base.Function;
import mcjty.theoneprobe.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;
import java.util.List;

//@InterfaceList({@Interface(
//        modid = "theoneprobe",
//        iface = "mcjty.theoneprobe.api.IBlockDisplayOverride"
//), @Interface(
//        modid = "theoneprobe",
//        iface = "mcjty.theoneprobe.api.IProbeInfoProvider"
//)})
//public class BCPluginTOP implements Function<ITheOneProbe, Void>, IBlockDisplayOverride, IProbeInfoProvider
public enum BCPluginTOP implements Function<ITheOneProbe, Void>, IBlockDisplayOverride, IProbeInfoProvider
{
    INSTANCE;
    static final String TOP_MOD_ID = "theoneprobe";

    //    @Method(modid = "theoneprobe")
    public Void apply(ITheOneProbe top)
    {
        top.registerBlockDisplayOverride(this);
        top.registerProvider(this);
        return null;
    }

    //    @Method(modid = "theoneprobe")
//    public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, BlockState blockState, IProbeHitData data)
    {
        return false;
    }

    //    @Method(modid = "theoneprobe")
//    public String getID()
    public ResourceLocation getID()
    {
//        return "buildcraftcompat.top";
        return new ResourceLocation("buildcraftcompat.top");
    }

    //    @Method(modid = "theoneprobe")
//    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, BlockState blockState, IProbeHitData data)
    {
        ResourceLocation blockRegistryName = blockState.getBlock().getRegistryName();
        if (blockRegistryName != null && BCModules.isBcMod(blockRegistryName.getNamespace()))
        {
            BlockEntity entity = world.getBlockEntity(data.getPos());
            if (entity instanceof IAutoCraft)
            {
                this.addAutoCraftInfo(probeInfo, (IAutoCraft) entity);
            }

            if (entity instanceof ILaserTarget)
            {
                this.addLaserTargetInfo(probeInfo, (ILaserTarget) entity);
            }

            if (entity instanceof IAssemblyCraft)
            {
                this.addAssemblyInfo(probeInfo, (IAssemblyCraft) entity);
            }
        }

    }

    //    @Method(modid = "theoneprobe")
    private void addAutoCraftInfo(IProbeInfo probeInfo, IAutoCraft crafter)
    {
        if (!crafter.getCurrentRecipeOutput().isEmpty())
        {
            IProbeInfo mainInfo = probeInfo.vertical();
//            mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text("Making: ").item(crafter.getCurrentRecipeOutput());
            mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(new TranslatableComponent("buildcraft.waila.crafting")).item(crafter.getCurrentRecipeOutput());
//            IProbeInfo info = mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text("From: ");
            IProbeInfo info = mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(new TranslatableComponent("buildcraft.waila.crafting_from"));
            List<ItemStack> stacks = CompatUtils.compactInventory(crafter.getInvBlueprint());

            for (ItemStack stack : stacks)
            {
                info.item(stack);
            }
        }
        else
        {
            IProbeInfo mainInfo = probeInfo.vertical();
            mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(new TranslatableComponent("buildcraft.waila.no_recipe"));
        }

    }

    //    @Method(modid = "theoneprobe")
    private void addLaserTargetInfo(IProbeInfo probeInfo, ILaserTarget laserTarget)
    {
        long power = laserTarget.getRequiredLaserPower();
        if (power > 0L)
        {
//            probeInfo.horizontal().text(TextFormatting.WHITE + "Waiting from laser: ").text(TextFormatting.AQUA + MjAPI.formatMj(power)).text(TextFormatting.AQUA + "MJ");
            probeInfo.horizontal().text(new TranslatableComponent("buildcraft.waila.waiting_for_laser", MjAPI.formatMj(power)));
        }
    }

    private void addAssemblyInfo(IProbeInfo probeInfo, IAssemblyCraft assembly)
    {
        ItemStack result = assembly.getAssemblyResult();
        if (!result.isEmpty())
        {
            probeInfo.horizontal().text(new TranslatableComponent("buildcraft.waila.crafting")).item(result);
        }
        else
        {
            IProbeInfo mainInfo = probeInfo.vertical();
            mainInfo.horizontal(mainInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(new TranslatableComponent("buildcraft.waila.no_recipe"));
        }
    }
}
