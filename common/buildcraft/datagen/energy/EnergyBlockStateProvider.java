package buildcraft.datagen.energy;

import buildcraft.datagen.base.BCBlockStateProvider;
import buildcraft.energy.BCEnergy;
import buildcraft.energy.BCEnergyBlocks;
import buildcraft.energy.BCEnergyFluids;
import buildcraft.lib.fluid.BCFluid;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class EnergyBlockStateProvider extends BlockStateProvider
{
    public EnergyBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
//        builtinEntity(BCEnergyBlocks.ENGINE_STONE.get(),"buildcraftenergy:blocks/engine/stone/back");
        BCBlockStateProvider.builtinEntity(this, BCEnergyBlocks.engineStone.get(), "buildcraftenergy:blocks/engine/stone/back");
//        builtinEntity(BCEnergyBlocks.ENGINE_IRON.get(),"buildcraftenergy:blocks/engine/iron/back");
        BCBlockStateProvider.builtinEntity(this, BCEnergyBlocks.engineIron.get(), "buildcraftenergy:blocks/engine/iron/back");

//        ResourceLocation[][] fromSprites = new ResourceLocation[3][2];
//        for (int h = 0; h < 3; h++) {
//            fromSprites[h][0] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_still");
//            fromSprites[h][1] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_flow");
//        }

        for (RegistryObject<BCFluid.Source> fluid : BCEnergyFluids.allStill)
        {
//            ResourceLocation[] sprites = fromSprites[fluid.get().getHeatValue()];
            simpleBlock(
                    fluid.get().getReg().getBlock(),
                    models().getBuilder(fluid.get().getReg().getBlock().getRegistryName().toString())
                            .texture("particle", fluid.get().getSource().getAttributes().getStillTexture())
            );
        }
    }
}
