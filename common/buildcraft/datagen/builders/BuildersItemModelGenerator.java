package buildcraft.datagen.builders;

import buildcraft.builders.BCBuildersBlocks;
import buildcraft.builders.BCBuildersItems;
import buildcraft.builders.client.BuildersItemModelPredicates;
import buildcraft.datagen.base.BCBaseItemModelGenerator;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BuildersItemModelGenerator extends BCBaseItemModelGenerator {
    public BuildersItemModelGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // BlockItems

        // builder
        withExistingParent(BCBuildersBlocks.builder.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/builder/main"));
        // filler
        withExistingParent(BCBuildersBlocks.filler.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/filler/main"));
        // frame
        withExistingParent(BCBuildersBlocks.frame.get().getRegistryName().toString(), CUBE_ALL)
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
        // quarry
        withExistingParent(BCBuildersBlocks.quarry.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/quarry/false_false_false_false_false_false"));
        // replacer
        withExistingParent(BCBuildersBlocks.replacer.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/replacer"));

        // Calen: these were declared in blockstates in 1.12.2, no single file
        // library
        withExistingParent(BCBuildersBlocks.library.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/library"));
        // architect
        withExistingParent(BCBuildersBlocks.architect.get().getRegistryName().toString(), new ResourceLocation("buildcraftbuilders:block/architect_off"));

        // Items

        // addonFillerPlanner
        withExistingParent(BCBuildersItems.addonFillerPlanner.get().getRegistryName().toString(), CUBE_ALL)
                .texture("all", new ResourceLocation("buildcraftbuilders:addons/filler_planner"));

        // schematicSingle
        ResourceLocation schematicSingle = BCBuildersItems.schematicSingle.get().getRegistryName();
        withExistingParent(schematicSingle.toString(), GENERATED)
                .override()
                .model(
                        withExistingParent(schematicSingle.getNamespace() + ":item/" + schematicSingle.getPath() + "/clean", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/schematic_single/clean")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(schematicSingle.getNamespace() + ":item/" + schematicSingle.getPath() + "/used", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/schematic_single/used")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 1)
                .end();

        // snapshotBLUEPRINT
        ResourceLocation snapshotBLUEPRINT = BCBuildersItems.snapshotBLUEPRINT.get().getRegistryName();
        withExistingParent(snapshotBLUEPRINT.toString(), GENERATED)
                .override()
                .model(
                        withExistingParent(snapshotBLUEPRINT.getNamespace() + ":item/" + snapshotBLUEPRINT.getPath() + "/clean", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/blueprint/clean")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(snapshotBLUEPRINT.getNamespace() + ":item/" + snapshotBLUEPRINT.getPath() + "/used", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/blueprint/used")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 1)
                .end();

        // snapshotTEMPLATE
        ResourceLocation snapshotTEMPLATE = BCBuildersItems.snapshotTEMPLATE.get().getRegistryName();
        withExistingParent(snapshotTEMPLATE.toString(), GENERATED)
                .override()
                .model(
                        withExistingParent(snapshotTEMPLATE.getNamespace() + ":item/" + snapshotTEMPLATE.getPath() + "/clean", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/template/clean")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 0)
                .end()
                .override()
                .model(
                        withExistingParent(snapshotTEMPLATE.getNamespace() + ":item/" + snapshotTEMPLATE.getPath() + "/used", GENERATED)
                                .texture("layer0", "buildcraftbuilders:items/template/used")
                )
                .predicate(BuildersItemModelPredicates.PREDICATE_USED, 1)
                .end();

    }

    @Nonnull
    @Override
    public String getName() {
        return "BuildCraft Builders Item Model Generator";
    }
}
