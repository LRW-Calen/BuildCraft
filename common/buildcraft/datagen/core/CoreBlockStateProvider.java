package buildcraft.datagen.core;

import buildcraft.api.enums.EnumDecoratedBlock;
import buildcraft.api.properties.BuildCraftProperties;
import buildcraft.core.BCCoreBlocks;
import buildcraft.core.block.BlockDecoration;
import buildcraft.datagen.base.BCBlockStateProvider;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CoreBlockStateProvider extends BlockStateProvider {
    public CoreBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        models().withExistingParent("buildcraftcore:block/default_cube", "minecraft:block/cube")
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(10, -45, 170)
                .translation(0, 1.5F, -2.75F)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .end()
        ;
//        getVariantBuilder(BCCoreBlocks.ENGINE_WOOD.get()).forAllStates(state ->
//                new ConfiguredModel[]{new ConfiguredModel(new ModelFile.UncheckedModelFile("buildcraftcore:models/block/engine_redstone.json"))}
//        );
//        getVariantBuilder(BCCoreBlocks.ENGINE_CREATIVE.get()).forAllStates(state ->
//                new ConfiguredModel[]{new ConfiguredModel(new ModelFile.UncheckedModelFile("buildcraftcore:models/block/engine_creative.json"))}
//        );
//        builtinEntity(BCCoreBlocks.ENGINE_WOOD.get(),"buildcraftcore:blocks/engine/wood/back");
        BCBlockStateProvider.builtinEntity(this, BCCoreBlocks.engineWood.get(), "buildcraftcore:blocks/engine/wood/back");
//        builtinEntity(BCCoreBlocks.ENGINE_CREATIVE.get(),"buildcraftcore:blocks/engine/creative/back");
        BCBlockStateProvider.builtinEntity(this, BCCoreBlocks.engineCreative.get(), "buildcraftcore:blocks/engine/creative/back");

        // Spring
        simpleBlock(BCCoreBlocks.springWater.get(), new ConfiguredModel(new ModelFile.ExistingModelFile(new ResourceLocation("minecraft:block/bedrock"), this.models().existingFileHelper)));
        simpleBlock(BCCoreBlocks.springOil.get(), new ConfiguredModel(new ModelFile.ExistingModelFile(new ResourceLocation("minecraft:block/bedrock"), this.models().existingFileHelper)));

        // decorated
        BCCoreBlocks.decoratedMap.values().forEach(decorated ->
                {
                    BlockDecoration block = decorated.get();
                    ResourceLocation rl = block.getRegistryName();
                    EnumDecoratedBlock type = block.DECORATED_TYPE;
                    String texture = switch (type) {
                        case BLUEPRINT -> "buildcraftcore:blocks/blueprint/blue";
                        case DESTROY -> "buildcraftcore:blocks/misc/texture_red_dark";
                        case LASER_BACK -> "buildcraftsilicon:blocks/laser/bottom";
                        case LEATHER -> "buildcraftcore:blocks/misc/leather";
                        case PAPER -> "buildcraftcore:blocks/misc/paper";
                        case TEMPLATE -> "buildcraftcore:blocks/blueprint/black";
                    };
                    getVariantBuilder(block).forAllStates(
                            s -> ConfiguredModel.builder().modelFile(
                                            models().withExistingParent(rl.getNamespace() + ":block/decorated/" + type.getSerializedName(), "minecraft:block/cube_all")
                                                    .texture("all", texture)
                                    )
                                    .build()
                    );

                }
        );

        // markerVolume
        ModelBuilder torch_center_lit = models().getBuilder("buildcraftcore:block/torch_center_lit")
                .texture("particle", "#all")
                .element()
                .from(7, 0, 7)
                .to(9, 9, 9)
                .shade(false)
                .face(Direction.EAST).uvs(2, 5, 4, 14).texture("#all").end()
                .face(Direction.NORTH).uvs(4, 5, 6, 14).texture("#all").end()
                .face(Direction.WEST).uvs(6, 5, 8, 14).texture("#all").end()
                .face(Direction.SOUTH).uvs(0, 5, 2, 14).texture("#all").end()
                .face(Direction.DOWN).uvs(0, 14, 2, 16).texture("#all").end()
                .face(Direction.UP).uvs(0, 3, 2, 5).texture("#all").end()
                .end();
        ModelBuilder markerVolume = models().getBuilder("buildcraftcore:block/marker_volume")
                .parent(torch_center_lit)
                .texture("all", "buildcraftcore:blocks/marker_volume");
        getMultipartBuilder(BCCoreBlocks.markerVolume.get())
                .part()
                .modelFile(markerVolume)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.UP)
                .end()
                .part()
                .modelFile(markerVolume)
                .rotationX(180)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.DOWN)
                .end()
                .part()
                .modelFile(markerVolume)
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.EAST)
                .end()
                .part()
                .modelFile(markerVolume)
                .rotationX(90)
                .rotationY(180)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.SOUTH)
                .end()
                .part()
                .modelFile(markerVolume)
                .rotationX(90)
                .rotationY(270)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.WEST)
                .end()
                .part()
                .modelFile(markerVolume)
                .rotationX(90)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.NORTH)
                .end()
        ;

        // markerPath
        ModelBuilder markerPath = models().getBuilder("buildcraftcore:block/marker_path")
                .parent(torch_center_lit)
                .texture("all", "buildcraftcore:blocks/marker_path");
        getMultipartBuilder(BCCoreBlocks.markerPath.get())
                .part()
                .modelFile(markerPath)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.UP)
                .end()
                .part()
                .modelFile(markerPath)
                .rotationX(180)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.DOWN)
                .end()
                .part()
                .modelFile(markerPath)
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.EAST)
                .end()
                .part()
                .modelFile(markerPath)
                .rotationX(90)
                .rotationY(180)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.SOUTH)
                .end()
                .part()
                .modelFile(markerPath)
                .rotationX(90)
                .rotationY(270)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.WEST)
                .end()
                .part()
                .modelFile(markerPath)
                .rotationX(90)
                .addModel()
                .condition(BuildCraftProperties.BLOCK_FACING_6, Direction.NORTH)
                .end()
        ;

        // power_tester
        getVariantBuilder(BCCoreBlocks.powerTester.get()).forAllStates(s ->
                ConfiguredModel.builder().modelFile(
                                models().withExistingParent("buildcraftcore:block/power_tester", new ResourceLocation("minecraft", "block/cube_all"))
                                        .texture("all", "buildcraftcore:blocks/power_tester")
                        )
                        .build()
        );
    }
}
