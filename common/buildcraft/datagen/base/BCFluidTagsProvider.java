package buildcraft.datagen.base;

import buildcraft.energy.BCEnergy;
import buildcraft.energy.BCEnergyFluids;
import buildcraft.lib.fluid.BCFluid;
import buildcraft.lib.oredicttag.OreDictTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BCFluidTagsProvider extends FluidTagsProvider
{

    public BCFluidTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, BCEnergy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        // Provide an empty fluid blacklist
        tag(OreDictTags.OIL)
                .add(BCEnergyFluids.getAllStill().stream().map(r -> r.get()).toArray(BCFluid.Source[]::new))
                .add(BCEnergyFluids.getAllFlow().stream().map(r -> r.get()).toArray(BCFluid.Flowing[]::new))
        ;
    }

    @Override
    public String getName()
    {
        return "BuildCraft Fluid Tags";
    }
}
