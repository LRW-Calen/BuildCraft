package buildcraft.datagen.factory;

import buildcraft.factory.BCFactoryBlocks;
import buildcraft.factory.BCFactoryItems;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FactoryItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation generated = new ResourceLocation("minecraft", "item/generated");

    public FactoryItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        withExistingParent(BCFactoryItems.waterGel.get().getRegistryName().toString(), generated).texture("layer0", "buildcraftfactory:items/water_gel");
        withExistingParent(BCFactoryItems.gelledWater.get().getRegistryName().toString(), generated).texture("layer0", "buildcraftfactory:items/gel");
        withExistingParent(BCFactoryItems.plasticSheet.get().getRegistryName().toString(), generated).texture("layer0", "buildcraftfactory:items/plastic_sheet");
        getBuilder(BCFactoryBlocks.heatExchange.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));

        // Block Items
        withExistingParent(BCFactoryBlocks.miningWell.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/mining_well"));
        withExistingParent(BCFactoryBlocks.floodGate.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/flood_gate/true_true_true_true_true"));
        withExistingParent(BCFactoryBlocks.pump.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/pump"));
        withExistingParent(BCFactoryBlocks.autoWorkbenchItems.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/autoworkbench_item"));
        withExistingParent(BCFactoryBlocks.tank.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/tank"))
                .transforms()
                .transform(ItemTransforms.TransformType.GUI)
                .rotation(30, 225, 0)
                .translation(0, 0, 0)
                .scale(0.625F, 0.625F, 0.625F)
                .end()
                .transform(ItemTransforms.TransformType.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25F, 0.25F, 0.25F)
                .end()
                .transform(ItemTransforms.TransformType.FIXED)
                .rotation(0, 0, 0)
                .translation(0, 0, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .end()
        ;
        withExistingParent(BCFactoryBlocks.distiller.get().getRegistryName().toString(), new ResourceLocation("minecraft:block/block"))
                // Side Tank
                .element()
                .from(0, 0, 4)
                .to(8, 16, 12)
                .face(Direction.UP).uvs(8, 0, 16, 8).texture("#sprite_a").end()
                .face(Direction.DOWN).uvs(8, 0, 16, 8).texture("#sprite_a").end()
                .face(Direction.NORTH).uvs(0, 0, 8, 16).texture("#sprite_a").end()
                .face(Direction.SOUTH).uvs(0, 0, 8, 16).texture("#sprite_a").end()
                .face(Direction.EAST).uvs(0, 0, 8, 16).texture("#sprite_a").end()
                .face(Direction.WEST).uvs(0, 0, 8, 16).texture("#sprite_a").end()
                .end()
                // Bottom Tank
                .element()
                .from(8, 0, 0)
                .to(16, 8, 16)
                .face(Direction.UP).uvs(0, 0, 16, 8).texture("#sprite_b").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                .face(Direction.DOWN).uvs(0, 0, 16, 8).texture("#sprite_b").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                .face(Direction.NORTH).uvs(8, 8, 16, 16).texture("#sprite_a").end()
                .face(Direction.SOUTH).uvs(8, 8, 16, 16).texture("#sprite_a").end()
                .face(Direction.EAST).uvs(0, 8, 16, 16).texture("#sprite_b").end()
                .face(Direction.WEST).uvs(0, 8, 16, 16).texture("#sprite_b").end()
                .end()
                // Top Tank
                .element()
                .from(8, 8, 0)
                .to(16, 16, 16)
                .face(Direction.UP).uvs(0, 0, 16, 8).texture("#sprite_b").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                .face(Direction.DOWN).uvs(0, 0, 16, 8).texture("#sprite_b").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                .face(Direction.NORTH).uvs(8, 8, 16, 16).texture("#sprite_a").end()
                .face(Direction.SOUTH).uvs(8, 8, 16, 16).texture("#sprite_a").end()
                .face(Direction.EAST).uvs(0, 8, 16, 16).texture("#sprite_b").end()
                .face(Direction.WEST).uvs(0, 8, 16, 16).texture("#sprite_b").end()
                .end()
                // Right
                .element()
                .from(0, 0, 12)
                .to(8, 4, 16)
                .face(Direction.UP).uvs(4, 0, 12, 4).texture("#power").end()
                .face(Direction.DOWN).uvs(4, 0, 12, 4).texture("#power").end()
                .face(Direction.NORTH).uvs(4, 4, 12, 8).texture("#power").end()
                .face(Direction.SOUTH).uvs(4, 4, 12, 8).texture("#power").end()
                .face(Direction.EAST).uvs(0, 4, 4, 8).texture("#power").end()
                .face(Direction.WEST).uvs(0, 4, 4, 8).texture("#power").end()
                .end()
                // Left
                .element()
                .from(0, 0, 0)
                .to(8, 4, 4)
                .face(Direction.UP).uvs(4, 0, 12, 4).texture("#power").end()
                .face(Direction.DOWN).uvs(4, 0, 12, 4).texture("#power").end()
                .face(Direction.NORTH).uvs(4, 4, 12, 8).texture("#power").end()
                .face(Direction.SOUTH).uvs(4, 4, 12, 8).texture("#power").end()
                .face(Direction.EAST).uvs(0, 4, 4, 8).texture("#power").end()
                .face(Direction.WEST).uvs(0, 4, 4, 8).texture("#power").end()
                .end()
                .texture("sprite_a", "buildcraftfactory:blocks/distiller/tank_sprite_a")
                .texture("sprite_b", "buildcraftfactory:blocks/distiller/tank_sprite_b")
                .texture("power", "buildcraftfactory:blocks/distiller/power_sprite_a")
        ;
        withExistingParent(BCFactoryBlocks.chute.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/chute"))
                .transforms()
                .transform(ItemTransforms.TransformType.GUI)
                .rotation(30, 225, 0)
                .translation(0, 0, 0)
                .scale(0.625F, 0.625F, 0.625F)
                .end()
                .transform(ItemTransforms.TransformType.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25F, 0.25F, 0.25F)
                .end()
                .transform(ItemTransforms.TransformType.FIXED)
                .rotation(0, 0, 0)
                .translation(0, 0, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .end()
        ;

    }
}
