package buildcraft.datagen.energy;

import buildcraft.energy.BCEnergy;
import buildcraft.energy.BCEnergyBlocks;
import buildcraft.energy.BCEnergyItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnergyItemModelProvider extends ItemModelProvider
{
    private static final ResourceLocation generated = new ResourceLocation("minecraft", "item/generated");

    public EnergyItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        // Block Items
        getBuilder(BCEnergyBlocks.engineStone.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
        getBuilder(BCEnergyBlocks.engineIron.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));

        // Items
        withExistingParent(BCEnergyItems.globOil.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftenergy:items/glob_oil");
        withExistingParent(BCEnergyItems.oilPlacer.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftenergy:items/glob_oil");
    }
}
