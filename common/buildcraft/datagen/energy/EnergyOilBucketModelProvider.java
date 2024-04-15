package buildcraft.datagen.energy;

import buildcraft.energy.BCEnergyFluids;
import buildcraft.energy.event.ChristmasHandler;
import buildcraft.lib.fluid.BCFluid;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicBucketModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class EnergyOilBucketModelProvider extends ItemModelProvider {
    public EnergyOilBucketModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBuckets();
    }

    protected void registerBuckets() {
        for (RegistryObject<BCFluid.Source> fluid : BCEnergyFluids.allStill) {
            registerBucket(fluid.get());
        }
    }

    protected void registerBucket(BCFluid.Source fluid) {
        int density = fluid.getAttributes().getDensity();
        boolean isGaseous = ChristmasHandler.isEnabled() ? (density > 0) : (density < 0);
        withExistingParent(
                fluid.getReg().getBucket().getRegistryName().toString(),
                new ResourceLocation("forge", "item/bucket_drip")
        )
                .customLoader(DynamicBucketModelBuilder::begin)
                .flipGas(isGaseous)
                .fluid(fluid.getSource())
        ;
        withExistingParent(
                fluid.getReg().getBucket().getRegistryName().toString() + "_christmas",
                new ResourceLocation("forge", "item/bucket_drip")
        )
                .customLoader(DynamicBucketModelBuilder::begin)
                .flipGas(false)
                .fluid(fluid.getSource())
        ;
    }
}
