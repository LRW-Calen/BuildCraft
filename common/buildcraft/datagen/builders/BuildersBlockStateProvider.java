package buildcraft.datagen.builders;

import buildcraft.api.enums.EnumOptionalSnapshotType;
import buildcraft.api.properties.BuildCraftProperties;
import buildcraft.builders.BCBuilders;
import buildcraft.builders.BCBuildersBlocks;
import buildcraft.builders.block.BlockArchitectTable;
import buildcraft.builders.block.BlockBuilder;
import buildcraft.datagen.base.BCBlockStateProvider;
import buildcraft.lib.block.BlockBCBase_Neptune;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BuildersBlockStateProvider extends BlockStateProvider
{
    public BuildersBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        // Builder
//        ModelBuilder builder_main = models().getBuilder("buildcraftbuilders:block/builder/main");
        ModelBuilder<BlockModelBuilder> builder_main =
                models().withExistingParent("buildcraftbuilders:block/builder/main", new ResourceLocation("minecraft", "block/cube"))
                        .texture("particle", "buildcraftbuilders:blocks/builder/side")
                        .texture("down", "buildcraftbuilders:blocks/builder/bottom")
                        .texture("up", "buildcraftbuilders:blocks/builder/top")
                        .texture("north", "buildcraftbuilders:blocks/builder/front")
                        .texture("east", "buildcraftbuilders:blocks/builder/side")
                        .texture("south", "buildcraftbuilders:blocks/builder/back")
                        .texture("west", "buildcraftbuilders:blocks/builder/side");
        ModelBuilder<BlockModelBuilder> slot_empty =
                models().getBuilder("buildcraftbuilders:block/builder/slot_empty")
                        .texture("all", "buildcraftbuilders:blocks/builder/slot_empty")
                        .element()
                        .from(16, 6, 13)
                        .to(6, 4, 3)
                        .face(Direction.DOWN).texture("#all").uvs(3, 3, 13, 13).end()
                        .face(Direction.UP).texture("#all").uvs(3, 3, 13, 13).end()
                        .face(Direction.NORTH).texture("#all").uvs(3, 0, 13, 2).end()
                        .face(Direction.SOUTH).texture("#all").uvs(3, 0, 13, 2).end()
                        .face(Direction.WEST).texture("#all").uvs(3, 0, 13, 2).end()
                        .face(Direction.EAST).texture("#all").uvs(3, 0, 13, 2).end()
                        .end();
        ModelBuilder<BlockModelBuilder> slot_blueprint =
                models().getBuilder("buildcraftbuilders:block/builder/slot_blueprint")
                        .texture("slot", "buildcraftbuilders:blocks/builder/slot_blueprint")
                        .element()
                        .from(3, 4, -2)
                        .to(13, 6, 8)
                        .face(Direction.DOWN).texture("#slot").uvs(3, 3, 13, 13).end()
                        .face(Direction.UP).texture("#slot").uvs(3, 3, 13, 13).end()
                        .face(Direction.NORTH).texture("#slot").uvs(3, 0, 13, 2).end()
                        .face(Direction.SOUTH).texture("#slot").uvs(3, 0, 13, 2).end()
                        .face(Direction.WEST).texture("#slot").uvs(3, 0, 13, 2).end()
                        .face(Direction.EAST).texture("#slot").uvs(3, 0, 13, 2).end()
                        .end();
        ModelBuilder<BlockModelBuilder> slot_template =
                models().getBuilder("buildcraftbuilders:block/builder/slot_template")
                        .texture("template", "buildcraftbuilders:blocks/builder/slot_template")
                        .element()
                        .from(3, 4, -2)
                        .to(13, 6, 8)
                        .face(Direction.DOWN).texture("#template").uvs(3, 3, 13, 13).end()
                        .face(Direction.UP).texture("#template").uvs(3, 3, 13, 13).end()
                        .face(Direction.NORTH).texture("#template").uvs(3, 0, 13, 2).end()
                        .face(Direction.SOUTH).texture("#template").uvs(3, 0, 13, 2).end()
                        .face(Direction.WEST).texture("#template").uvs(3, 0, 13, 2).end()
                        .face(Direction.EAST).texture("#template").uvs(3, 0, 13, 2).end()
                        .end();
        MultiPartBlockStateBuilder multiPartBuilder_block_builder = getMultipartBuilder(BCBuildersBlocks.builder.get());
        BCBlockStateProvider.add4Facing(multiPartBuilder_block_builder, builder_main,null,null);
        BCBlockStateProvider.add4Facing(multiPartBuilder_block_builder, slot_empty,BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.NONE);
        BCBlockStateProvider.add4Facing(multiPartBuilder_block_builder, slot_blueprint,BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.BLUEPRINT);
        BCBlockStateProvider.add4Facing(multiPartBuilder_block_builder, slot_template,BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.TEMPLATE);

//        getVariantBuilder(BCBuildersBlocks.builder.get())
//                .forAllStates(state ->
//                {
//                    int yRot = (int) state.getValue(BuildCraftProperties.BLOCK_FACING).getOpposite().toYRot(); // Calen: getOpposite().toYRot() matches the yRot in the blockstate json of Builder in 1.18.2
//                    return switch (state.getValue(BlockBuilder.SNAPSHOT_TYPE))
//                    {
//                        case NONE ->
//                                ConfiguredModel.builder().modelFile(builder_main).rotationY(yRot).nextModel().modelFile(slot_empty).rotationY(yRot).build();
//                        case TEMPLATE ->
//                                ConfiguredModel.builder().modelFile(builder_main).rotationY(yRot).nextModel().modelFile(slot_template).rotationY(yRot).build();
//                        case BLUEPRINT ->
//                                ConfiguredModel.builder().modelFile(builder_main).rotationY(yRot).nextModel().modelFile(slot_blueprint).rotationY(yRot).build();
//                    };
//                });
//        getMultipartBuilder(BCBuildersBlocks.builder.get())
//                .part()
//                .modelFile(builder_main)
//                .rotationY(90)
//                .addModel()
//                .condition(BuildCraftProperties.BLOCK_FACING, Direction.EAST)
//                .end()
//
//                .part()
//                .modelFile(builder_main)
//                .rotationY(180)
//                .addModel()
//                .condition(BuildCraftProperties.BLOCK_FACING, Direction.SOUTH)
//                .end()
//
//                .part()
//                .modelFile(builder_main)
//                .rotationY(270)
//                .addModel()
//                .condition(BuildCraftProperties.BLOCK_FACING, Direction.WEST)
//                .end()
//
//                .part()
//                .modelFile(builder_main)
//                .rotationY(0)
//                .addModel()
//                .condition(BuildCraftProperties.BLOCK_FACING, Direction.NORTH)
//                .end()
//
//                .part()
//                .modelFile(slot_empty)
//                .addModel()
//                .condition(BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.NONE)
//                .end()
//
//                .part()
//                .modelFile(slot_blueprint)
//                .addModel()
//                .condition(BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.BLUEPRINT)
//                .end()
//
//                .part()
//                .modelFile(slot_template)
//                .addModel()
//                .condition(BlockBuilder.SNAPSHOT_TYPE, EnumOptionalSnapshotType.TEMPLATE)
//                .end()
//        ;
        // Frame
        ModelBuilder connection = models().withExistingParent("buildcraftbuilders:block/frame/connection", "block/cube_all")
                .element()
                .from(4, 4, 0)
                .to(12, 12, 4)
                .face(Direction.DOWN).texture("#all").end()
                .face(Direction.UP).texture("#all").end()
//                .face(Direction.NORTH).texture("#all").end()
//                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .end()
                .element()
                .from(12, 12, 4)
                .to(4, 4, 0)
                .face(Direction.DOWN).texture("#all").end()
                .face(Direction.UP).texture("#all").end()
//                .face(Direction.NORTH).texture("#all").end()
//                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .end()
                .texture("all", "buildcraftbuilders:blocks/frame/default");
        getMultipartBuilder(BCBuildersBlocks.frame.get())
                .part()
                .modelFile(
                        models().withExistingParent("buildcraftbuilders:block/frame/base", "block/cube_all")
                                .element()
                                .from(4, 4, 4)
                                .to(12, 12, 12)
                                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                                .face(Direction.SOUTH).texture("#all").cullface(Direction.SOUTH).end()
                                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                                .face(Direction.EAST).texture("#all").cullface(Direction.EAST).end()
                                .end()
                                .element()
                                .from(12, 12, 12)
                                .to(4, 4, 4)
                                .face(Direction.DOWN).texture("#all").cullface(Direction.UP).end()
                                .face(Direction.UP).texture("#all").cullface(Direction.DOWN).end()
                                .face(Direction.NORTH).texture("#all").cullface(Direction.SOUTH).end()
                                .face(Direction.SOUTH).texture("#all").cullface(Direction.NORTH).end()
                                .face(Direction.WEST).texture("#all").cullface(Direction.EAST).end()
                                .face(Direction.EAST).texture("#all").cullface(Direction.WEST).end()
                                .end()
                                .texture("all", "buildcraftbuilders:blocks/frame/default")
                )
                .addModel()
                .end()

                .part()
                .modelFile(connection)
                .rotationX(270)
                .rotationY(0)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_UP, true)
                .end()

                .part()
                .modelFile(connection)
                .rotationX(90)
                .rotationY(0)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_DOWN, true)
                .end()

                .part()
                .modelFile(connection)
                .rotationX(0)
                .rotationY(90)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_EAST, true)
                .end()

                .part()
                .modelFile(connection)
                .rotationX(180)
                .rotationY(90)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_WEST, true)
                .end()

                .part()
                .modelFile(connection)
                .rotationX(0)
                .rotationY(0)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_NORTH, true)
                .end()

                .part()
                .modelFile(connection)
                .rotationX(180)
                .rotationY(0)
                .addModel()
                .condition(BuildCraftProperties.CONNECTED_SOUTH, true)
                .end()
        ;

        // Quarry
        ResourceLocation quarry = BCBuildersBlocks.quarry.get().getRegistryName();
        ResourceLocation normal_top = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/normal/top");
        ResourceLocation normal_bottom = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/normal/bottom");
        ResourceLocation normal_side = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/normal/side");
        ResourceLocation normal_front = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/normal/front");
        ResourceLocation normal_back = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/normal/back");

        ResourceLocation connected_top = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/connected/top");
        ResourceLocation connected_bottom = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/connected/bottom");
        ResourceLocation connected_side = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/connected/side");
        ResourceLocation connected_front = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/connected/front");
        ResourceLocation connected_back = new ResourceLocation(BCBuilders.MOD_ID, "blocks/quarry/connected/back");
        getVariantBuilder(BCBuildersBlocks.quarry.get()).forAllStates(s ->
        {
            int rotY = switch (s.getValue(BlockBCBase_Neptune.PROP_FACING))
            {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                case NORTH -> 0;
                default -> throw new RuntimeException("Only Facing 4!");
            };
            boolean connected_up = s.getValue(BuildCraftProperties.CONNECTED_UP);
            boolean connected_down = s.getValue(BuildCraftProperties.CONNECTED_DOWN);
            boolean connected_east = s.getValue(BuildCraftProperties.CONNECTED_EAST);
            boolean connected_west = s.getValue(BuildCraftProperties.CONNECTED_WEST);
            boolean connected_north = s.getValue(BuildCraftProperties.CONNECTED_NORTH);
            boolean connected_south = s.getValue(BuildCraftProperties.CONNECTED_SOUTH);
            return ConfiguredModel.builder().modelFile(
                            models().withExistingParent(
                                            quarry.getNamespace() + ":block/" + quarry.getPath()
                                                    + "/" + connected_up + "_" + connected_down + "_" + connected_east + "_" + connected_west + "_" + connected_north + "_" + connected_south
                                            ,
//                                            new ResourceLocation(NameSpaces.BUILDCRAFT_BUILDERS, "block/quarry/quarry"))
                                            new ResourceLocation("block/cube"))
                                    .texture("particle", normal_side)
                                    .texture("up", connected_up ? connected_top : normal_top)
                                    .texture("down", connected_down ? connected_bottom : normal_bottom)
                                    .texture("east", connected_east ? connected_side : normal_side)
                                    .texture("west", connected_west ? connected_side : normal_side)
                                    .texture("north", connected_north ? connected_front : normal_front)
                                    .texture("south", connected_south ? connected_back : normal_back)
                    )
                    .rotationY(rotY)
                    .build();
        });

        // Architect
        ResourceLocation architect_on = new ResourceLocation("buildcraftbuilders:architect_on");
        ResourceLocation architect_off = new ResourceLocation("buildcraftbuilders:architect_off");
        getVariantBuilder(BCBuildersBlocks.architect.get()).forAllStates(s ->
        {
            Direction direction = s.getValue(BlockBCBase_Neptune.PROP_FACING);
            int rotY = switch (direction)
            {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                case NORTH -> 0;
                default -> throw new RuntimeException("Only Facing 4!");
            };
            boolean valid = s.getValue(BlockArchitectTable.PROP_VALID);
            if (valid)
            {
                return ConfiguredModel.builder().modelFile(
                                models().withExistingParent(architect_on.toString(), new ResourceLocation("minecraft", "block/orientable"))
                                        .texture("particle", "buildcraftbuilders:blocks/architect/back")
                                        .texture("down", "buildcraftbuilders:blocks/architect/bottom")
                                        .texture("up", "buildcraftbuilders:blocks/architect/top")
                                        .texture("north", "buildcraftbuilders:blocks/architect/front_on")
                                        .texture("east", "buildcraftbuilders:blocks/architect/left")
                                        .texture("south", "buildcraftbuilders:blocks/architect/back")
                                        .texture("west", "buildcraftbuilders:blocks/architect/right")
                        )
                        .rotationY(rotY)
                        .build();
            }
            else
            {
                return ConfiguredModel.builder().modelFile(
                                models().withExistingParent(architect_off.toString(), new ResourceLocation("minecraft", "block/orientable"))
                                        .texture("particle", "buildcraftbuilders:blocks/architect/back")
                                        .texture("down", "buildcraftbuilders:blocks/architect/bottom")
                                        .texture("up", "buildcraftbuilders:blocks/architect/top")
                                        .texture("north", "buildcraftbuilders:blocks/architect/front_off")
                                        .texture("east", "buildcraftbuilders:blocks/architect/left")
                                        .texture("south", "buildcraftbuilders:blocks/architect/back")
                                        .texture("west", "buildcraftbuilders:blocks/architect/right")
                        )
                        .rotationY(rotY)
                        .build();
            }
//            return ConfiguredModel.builder().modelFile(
//                            models().withExistingParent((valid ? architect_on : architect_off).toString(), new ResourceLocation("minecraft", "block/orientable"))
//                    )
//                    .rotationY(rotY)
//                    .build();
        });

        // Filler
//        ResourceLocation filler_main = new ResourceLocation("buildcraftbuilders:block/filler/main");
        BCBlockStateProvider.simple4FacingBlock(
                this,
                BCBuildersBlocks.filler.get(),
                90,
                180,
                270,
                0,
                models().withExistingParent("buildcraftbuilders:block/filler/main", new ResourceLocation("minecraft", "block/cube"))
                        .texture("particle", "buildcraftbuilders:blocks/filler/side")
                        .texture("down", "buildcraftbuilders:blocks/filler/bottom")
                        .texture("up", "buildcraftbuilders:blocks/filler/top")
                        .texture("north", "buildcraftbuilders:blocks/filler/front")
                        .texture("east", "buildcraftbuilders:blocks/filler/side")
                        .texture("south", "buildcraftbuilders:blocks/filler/side")
                        .texture("west", "buildcraftbuilders:blocks/filler/side")
        );

        // Library
//        ResourceLocation library = new ResourceLocation("buildcraftbuilders:library");
        BCBlockStateProvider.simple4FacingBlock(
                this,
                BCBuildersBlocks.library.get(),
                90,
                180,
                270,
                0,
                models().withExistingParent(BCBuildersBlocks.library.get().getRegistryName().toString(), new ResourceLocation("minecraft", "block/cube"))
                        .transforms()
                        .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                        .rotation(0, 135, 0)
                        .translation(0, 0, 0)
                        .scale(0.40F, 0.40F, 0.40F)
                        .end()
                        .end()
                        .texture("particle", "buildcraftbuilders:blocks/library/back")
                        .texture("down", "buildcraftbuilders:blocks/library/bottom")
                        .texture("up", "buildcraftbuilders:blocks/library/top")
                        .texture("north", "buildcraftbuilders:blocks/library/front")
                        .texture("east", "buildcraftbuilders:blocks/library/left")
                        .texture("south", "buildcraftbuilders:blocks/library/back")
                        .texture("west", "buildcraftbuilders:blocks/library/right")
        );

        // Replacer
//        ResourceLocation replacer = new ResourceLocation("buildcraftbuilders:replacer");
        BCBlockStateProvider.simple4FacingBlock(
                this,
                BCBuildersBlocks.replacer.get(),
                90,
                180,
                270,
                0,
                models().withExistingParent(BCBuildersBlocks.replacer.get().getRegistryName().toString(), new ResourceLocation("minecraft", "block/cube"))
                        .texture("particle", "buildcraftbuilders:blocks/replacer/side")
                        .texture("down", "buildcraftbuilders:blocks/replacer/bottom")
                        .texture("up", "buildcraftbuilders:blocks/replacer/top")
                        .texture("north", "buildcraftbuilders:blocks/replacer/front")
                        .texture("east", "buildcraftbuilders:blocks/replacer/side")
                        .texture("south", "buildcraftbuilders:blocks/replacer/side")
                        .texture("west", "buildcraftbuilders:blocks/replacer/side")
        );

    }
}
