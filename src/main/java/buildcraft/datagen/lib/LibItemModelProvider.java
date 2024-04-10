package buildcraft.datagen.lib;

import buildcraft.lib.BCLibItems;
import buildcraft.transport.BCTransport;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LibItemModelProvider extends ItemModelProvider
{
    public LibItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        withExistingParent(BCLibItems.debugger.get().getRegistryName().toString(), "minecraft:item/generated")
                .texture("layer0", "buildcraftlib:items/debugger");
        withExistingParent(BCLibItems.guide.get().getRegistryName().toString(), "minecraft:item/generated")
                .texture("layer0", "buildcraftlib:items/guide_book");
        withExistingParent(BCLibItems.guideNote.get().getRegistryName().toString(), "minecraft:item/generated")
                .texture("layer0", "buildcraftlib:items/guide_note");

    }
}
