package buildcraft.datagen.builders;

import buildcraft.builders.BCBuildersBlocks;
import buildcraft.builders.BCBuildersItems;
import buildcraft.builders.BCBuildersModBusEventDist;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BuildersItemModelProvider extends ItemModelProvider
{
    private static final ResourceLocation generated = new ResourceLocation("minecraft", "item/generated");

    public BuildersItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        // BlockItems
        withExistingParent(BCBuildersBlocks.builder.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/builder/main"));
        withExistingParent(BCBuildersBlocks.filler.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/filler/main"));
        withExistingParent(BCBuildersBlocks.frame.get().getRegistryName().toString(), new ResourceLocation("block/cube_all"))
                .element().from(4, 0, 4).to(12, 16, 12)
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#all").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#all").cullface(Direction.EAST).end()
                .end()
                .texture("all", new ResourceLocation("buildcraftbuilders:blocks/frame/default"))
        ;
//        withExistingParent(BCBuildersBlocks.quarry.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/quarry/quarry"));
        withExistingParent(BCBuildersBlocks.quarry.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/quarry/false_false_false_false_false_false"));
        withExistingParent(BCBuildersBlocks.replacer.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/replacer"));

        // declared in blockstates in 1.12.2
        withExistingParent(BCBuildersBlocks.library.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/library"));
        withExistingParent(BCBuildersBlocks.architect.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/architect_off"));

        // Items
        // addonFillerPlanner
        withExistingParent(BCBuildersItems.addonFillerPlanner.get().getRegistryName().toString(), new ResourceLocation("block/cube_all"))
                .texture("all", new ResourceLocation("buildcraftbuilders:addons/filler_planner"));
        // schematicSingle
        ResourceLocation schematicSingle = BCBuildersItems.schematicSingle.get().getRegistryName();
        withExistingParent(schematicSingle.toString(), generated)
                .override()
                .model(
                        withExistingParent(schematicSingle.getNamespace() + ":item/" + schematicSingle.getPath() + "/clean", generated)
                                .texture("layer0", "buildcraftbuilders:items/schematic_single/clean")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(schematicSingle.getNamespace() + ":item/" + schematicSingle.getPath() + "/used", generated)
                                .texture("layer0", "buildcraftbuilders:items/schematic_single/used")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 1)
                .end();
        // snapshotBLUEPRINT
        ResourceLocation snapshotBLUEPRINT = BCBuildersItems.snapshotBLUEPRINT.get().getRegistryName();
        withExistingParent(snapshotBLUEPRINT.toString(), generated)
                .override()
                .model(
                        withExistingParent(snapshotBLUEPRINT.getNamespace() + ":item/" + snapshotBLUEPRINT.getPath() + "/clean", generated)
                                .texture("layer0", "buildcraftbuilders:items/blueprint/clean")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(snapshotBLUEPRINT.getNamespace() + ":item/" + snapshotBLUEPRINT.getPath() + "/used", generated)
                                .texture("layer0", "buildcraftbuilders:items/blueprint/used")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 1)
                .end();
        // snapshotTEMPLATE
        ResourceLocation snapshotTEMPLATE = BCBuildersItems.snapshotTEMPLATE.get().getRegistryName();
        withExistingParent(snapshotTEMPLATE.toString(), generated)
                .override()
                .model(
                        withExistingParent(snapshotTEMPLATE.getNamespace() + ":item/" + snapshotTEMPLATE.getPath() + "/clean", generated)
                                .texture("layer0", "buildcraftbuilders:items/template/clean")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(snapshotTEMPLATE.getNamespace() + ":item/" + snapshotTEMPLATE.getPath() + "/used", generated)
                                .texture("layer0", "buildcraftbuilders:items/template/used")
                )
                .predicate(BCBuildersModBusEventDist.PREDICATE_USED, 1)
                .end();

    }

    @Nonnull
    @Override
    public String getName()
    {
        return "Item models: " + modid;
    }
}
