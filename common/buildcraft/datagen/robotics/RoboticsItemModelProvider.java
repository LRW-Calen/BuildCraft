package buildcraft.datagen.robotics;

import buildcraft.robotics.BCRoboticsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class RoboticsItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation generated = new ResourceLocation("minecraft", "item/generated");

    public RoboticsItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(BCRoboticsBlocks.zonePlanner.get().getRegistryName().toString(), new ResourceLocation("buildcraftrobotics:block/zone_planner"));
    }
}
