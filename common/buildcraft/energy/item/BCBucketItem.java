package buildcraft.energy.item;

import buildcraft.lib.fluid.BCFluidAttributes;
import buildcraft.lib.registry.CreativeTabManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BCBucketItem extends BucketItem {
    public BCBucketItem(Supplier<? extends Fluid> supplier, Properties properties) {
//        super(supplier, properties);
        super(supplier, properties.tab(CreativeTabManager.getTab("vanilla.misc")));
    }

    @Override
    public Component getName(ItemStack stack) {
//        return new TextComponent(I18n.get("item.buildcraft.bucket_filled", ((BCFluidAttributes)getFluid().getAttributes()).getDisplayName().getString()));
        return new TranslatableComponent("item.buildcraft.bucket_filled", ((BCFluidAttributes) getFluid().getAttributes()).getDisplayName().getString());
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        if (this.getClass() == BCBucketItem.class)
            return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
        else
            return super.initCapabilities(stack, nbt);
    }
}
