package buildcraft.datagen.base;

import buildcraft.api.properties.BuildCraftProperties;
import buildcraft.lib.block.BlockBCBase_Neptune;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.Arrays;

public class BCBlockStateProvider {
    //    private void builtinEntity(Block b)
    public static void builtinEntity(BlockStateProvider provider, Block b) {
//        simpleBlock(b, models().getBuilder(b.getRegistryName().toString())
//                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
//        );
        // Calen: set this in blockstate json to avoid the model loaded by mc
//        simpleBlock(b, new ConfiguredModel(new ModelFile.UncheckedModelFile("minecraft:builtin/entity")));
        provider.simpleBlock(b, new ConfiguredModel(new ModelFile.UncheckedModelFile("minecraft:builtin/entity")));
    }

    public static void builtinEntity(BlockStateProvider provider, Block b, String particle) {
        provider.models().existingFileHelper.trackGenerated(new ResourceLocation("minecraft:builtin/entity"), PackType.CLIENT_RESOURCES, ".json", "models");
        // Calen: set this in blockstate json to avoid the model loaded by mc
        provider.simpleBlock(b, ConfiguredModel.builder().modelFile(
                        provider.models().withExistingParent(b.getRegistryName().toString(), "minecraft:builtin/entity")
                                .texture("particle", particle)
                )
                .build());
    }

    //    private void simple4FacingBlock(Block b, ResourceLocation model)
    public static void simple4FacingBlock(BlockStateProvider provider, Block b, ResourceLocation model) {
//        getVariantBuilder(b).forAllStates(s ->
        provider.getVariantBuilder(b).forAllStates(s ->
        {
            int rotY = switch (s.getValue(BlockBCBase_Neptune.PROP_FACING)) {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                case NORTH -> 0;
                default -> throw new RuntimeException("Only Facing 4!");
            };
            return ConfiguredModel.builder().modelFile(
                            provider.models().withExistingParent(model.toString(), model)
                    )
                    .rotationY(rotY)
                    .build();
        });
    }

    //    private void simple4FacingBlock(Block b, ModelFile model)
    public static void simple4FacingBlock(BlockStateProvider provider, Block b, int east, int south, int west, int north, ModelFile model) {
//        getVariantBuilder(b).forAllStates(s ->
        provider.getVariantBuilder(b).forAllStates(s ->
        {
            int rotY = switch (s.getValue(BlockBCBase_Neptune.PROP_FACING)) {
                case EAST -> east;
                case SOUTH -> south;
                case WEST -> west;
                case NORTH -> north;
                default -> throw new RuntimeException("Only Facing 4!");
            };
            return ConfiguredModel.builder().modelFile(
                            model
                    )
                    .rotationY(rotY)
                    .build();
        });
    }

    public static <T extends Comparable<T>> void add4Facing(MultiPartBlockStateBuilder builder, ModelFile model, Property<T> property, T value) {
        Arrays.stream(Direction.BY_2D_DATA).toList().forEach(direction ->
                {
                    if (property == null || value == null) {
                        builder
                                .part()
                                .modelFile(model)
//                                .rotationY(90)
                                .rotationY((int) direction.getOpposite().toYRot())
                                .addModel()
                                .condition(BuildCraftProperties.BLOCK_FACING, direction)
                                .end();
                    } else {
                        builder
                                .part()
                                .modelFile(model)
//                                .rotationY(90)
                                .rotationY((int) direction.getOpposite().toYRot())
                                .addModel()
                                .condition(BuildCraftProperties.BLOCK_FACING, direction)
                                .condition(property, value)
                                .end();
                    }
                }
        );
    }
}
