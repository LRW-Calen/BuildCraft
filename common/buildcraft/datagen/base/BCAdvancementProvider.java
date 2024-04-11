package buildcraft.datagen.base;

import buildcraft.datagen.builders.BuildersAdvancementProvider;
import buildcraft.datagen.core.CoreAdvancementProvider;
import buildcraft.datagen.energy.EnergyAdvancementProvider;
import buildcraft.datagen.factory.FactoryAdvancementProvider;
import buildcraft.datagen.silicon.SiliconAdvancementProvider;
import buildcraft.datagen.transport.TransportAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class BCAdvancementProvider extends AdvancementProvider
{
    public BCAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn)
    {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper)
    {
        CoreAdvancementProvider.registerCoreAdvancements(consumer, fileHelper); // Calen: should be first

        EnergyAdvancementProvider.registerEnergyAdvancements(consumer, fileHelper);
        FactoryAdvancementProvider.registerFactoryAdvancements(consumer, fileHelper);
        SiliconAdvancementProvider.registerSiliconAdvancements(consumer, fileHelper);
        TransportAdvancementProvider.registerTransportAdvancements(consumer, fileHelper);
        BuildersAdvancementProvider.registerBuildersAdvancements(consumer, fileHelper);
    }

    @Override
    public String getName()
    {
        return "BuildCraft Advancement Generator";
    }
}
