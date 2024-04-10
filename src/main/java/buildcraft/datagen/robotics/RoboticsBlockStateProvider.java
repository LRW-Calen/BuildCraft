package buildcraft.datagen.robotics;

import buildcraft.datagen.base.BCBlockStateProvider;
import buildcraft.robotics.BCRobotics;
import buildcraft.robotics.BCRoboticsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class RoboticsBlockStateProvider extends BlockStateProvider
{
    public RoboticsBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        // zonePlanner
        BCBlockStateProvider.simple4FacingBlock(
                this,
                BCRoboticsBlocks.zonePlanner.get(),
                90,
                180,
                270,
                0,
                models().withExistingParent(BCRoboticsBlocks.zonePlanner.get().getRegistryName().toString(), new ResourceLocation("minecraft", "block/cube"))
                        .texture("particle", "buildcraftrobotics:blocks/zone_planner/default")
                        .texture("down", "buildcraftrobotics:blocks/zone_planner/default")
                        .texture("up", "buildcraftrobotics:blocks/zone_planner/top")
                        .texture("north", "buildcraftrobotics:blocks/zone_planner/front")
                        .texture("east", "buildcraftrobotics:blocks/zone_planner/right")
                        .texture("south", "buildcraftrobotics:blocks/zone_planner/back")
                        .texture("west", "buildcraftrobotics:blocks/zone_planner/left")
        );

    }
}
