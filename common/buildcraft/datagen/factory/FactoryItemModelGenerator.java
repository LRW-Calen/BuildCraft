package buildcraft.datagen.factory;

import buildcraft.datagen.base.BCBaseItemModelGenerator;
import buildcraft.factory.BCFactory;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.factory.BCFactoryItems;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class FactoryItemModelGenerator extends BCBaseItemModelGenerator {
    public FactoryItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BCFactory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        // waterGel
        withExistingParent(BCFactoryItems.waterGel.get().getRegistryName().toString(), GENERATED).texture("layer0", "buildcraftfactory:item/water_gel");
        // gelledWater
        withExistingParent(BCFactoryItems.gelledWater.get().getRegistryName().toString(), GENERATED).texture("layer0", "buildcraftfactory:item/gel");
        // plasticSheet
        withExistingParent(BCFactoryItems.plasticSheet.get().getRegistryName().toString(), GENERATED).texture("layer0", "buildcraftfactory:item/plastic_sheet");
        // heatExchange
        getBuilder(BCFactoryBlocks.heatExchange.get().getRegistryName().toString()).parent(BUILTIN_ENTITY);

        // Block Items
        // miningWell
        withExistingParent(BCFactoryBlocks.miningWell.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/mining_well"));
        // floodGate
        withExistingParent(BCFactoryBlocks.floodGate.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/flood_gate/true_true_true_true_true"));
        // pump
        withExistingParent(BCFactoryBlocks.pump.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/pump"));
        // autoWorkbenchItems
        withExistingParent(BCFactoryBlocks.autoWorkbenchItems.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/autoworkbench_item"));
        // tank
        withExistingParent(BCFactoryBlocks.tank.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/tank"))
                .transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .translation(0, 0, 0)
                .scale(0.625F, 0.625F, 0.625F)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25F, 0.25F, 0.25F)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .rotation(0, 0, 0)
                .translation(0, 0, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .end()
        ;

        // distiller
        withExistingParent(BCFactoryBlocks.distiller.get().getRegistryName().toString(), BLOCK)
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
                .texture("sprite_a", "buildcraftfactory:block/distiller/tank_sprite_a")
                .texture("sprite_b", "buildcraftfactory:block/distiller/tank_sprite_b")
                .texture("power", "buildcraftfactory:block/distiller/power_sprite_a")
        ;

        // chute
        withExistingParent(BCFactoryBlocks.chute.get().getRegistryName().toString(), new ResourceLocation("buildcraftfactory:block/chute"))
                .transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .translation(0, 0, 0)
                .scale(0.625F, 0.625F, 0.625F)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25F, 0.25F, 0.25F)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .rotation(0, 0, 0)
                .translation(0, 0, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5F, 0)
                .scale(0.375F, 0.375F, 0.375F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .translation(0, 0, 0)
                .scale(0.40F, 0.40F, 0.40F)
                .end()
                .end()
        ;
    }

    @Nonnull
    @Override
    public String getName() {
        return "BuildCraft Factory Item Model Generator";
    }
}
