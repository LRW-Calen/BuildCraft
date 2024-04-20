package buildcraft.datagen.transport;

import buildcraft.transport.BCTransportBlocks;
import buildcraft.transport.BCTransportItems;
import buildcraft.transport.pipe.PipeRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TransportItemModelProvider extends ItemModelProvider {
    public TransportItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        PipeRegistry.INSTANCE.getAllRegisteredPipes().forEach((def) ->
                {
                    getBuilder(def.identifier.getNamespace() + ":pipe_" + def.identifier.getPath() + "_colorless").parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
                    for (DyeColor colour : DyeColor.values()) {
                        getBuilder(def.identifier.getNamespace() + ":pipe_" + def.identifier.getPath() + "_" + colour.getName()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
                    }
                }
        );
        withExistingParent(BCTransportBlocks.filteredBuffer.get().getRegistryName().toString(), "buildcrafttransport:block/filtered_buffer");
        withExistingParent(BCTransportItems.waterproof.get().getRegistryName().toString(), "minecraft:item/generated")
                .texture("layer0", "buildcrafttransport:items/pipewaterproof");
        getBuilder(BCTransportItems.plugBlocker.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
        getBuilder(BCTransportItems.plugPowerAdaptor.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
    }
}
