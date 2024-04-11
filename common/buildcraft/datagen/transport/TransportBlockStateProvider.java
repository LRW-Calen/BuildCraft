package buildcraft.datagen.transport;

import buildcraft.datagen.base.BCBlockStateProvider;
import buildcraft.transport.BCTransport;
import buildcraft.transport.BCTransportBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TransportBlockStateProvider extends BlockStateProvider
{
    public TransportBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        getVariantBuilder(BCTransportBlocks.filteredBuffer.get()).forAllStates(s ->
        {
            return ConfiguredModel.builder().modelFile(
                            models().withExistingParent(BCTransportBlocks.filteredBuffer.get().getRegistryName().toString(), new ResourceLocation("minecraft", "block/cube_all"))
                                    .texture("all", "buildcrafttransport:blocks/filtered_buffer/default")
                                    .texture("particle", "buildcrafttransport:blocks/filtered_buffer/default")
                    )
                    .build();
        });
//        builtinEntity(BCTransportBlocks.pipeHolder.get());
        BCBlockStateProvider.builtinEntity(this, BCTransportBlocks.pipeHolder.get());
    }
}
