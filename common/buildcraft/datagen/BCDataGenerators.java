package buildcraft.datagen;

import buildcraft.builders.BCBuilders;
import buildcraft.core.BCCore;
import buildcraft.datagen.base.*;
import buildcraft.datagen.builders.BuildersBlockStateProvider;
import buildcraft.datagen.builders.BuildersItemModelProvider;
import buildcraft.datagen.builders.BuildersRecipeGenerator;
import buildcraft.datagen.core.CoreBlockStateProvider;
import buildcraft.datagen.core.CoreItemModelProvider;
import buildcraft.datagen.core.CoreRecipeGenerator;
import buildcraft.datagen.energy.*;
import buildcraft.datagen.factory.FactoryBlockStateProvider;
import buildcraft.datagen.factory.FactoryItemModelProvider;
import buildcraft.datagen.factory.FactoryRecipeGenerator;
import buildcraft.datagen.lib.LibItemModelProvider;
import buildcraft.datagen.lib.LibRecipeGenerator;
import buildcraft.datagen.robotics.RoboticsBlockStateProvider;
import buildcraft.datagen.robotics.RoboticsItemModelProvider;
import buildcraft.datagen.silicon.SiliconAssemblyRecipeGenerator;
import buildcraft.datagen.silicon.SiliconBlockStateProvider;
import buildcraft.datagen.silicon.SiliconItemModelProvider;
import buildcraft.datagen.silicon.SiliconRecipeGenerator;
import buildcraft.datagen.transport.TransportAssemblyRecipeGenerator;
import buildcraft.datagen.transport.TransportBlockStateProvider;
import buildcraft.datagen.transport.TransportItemModelProvider;
import buildcraft.datagen.transport.TransportRecipeGenerator;
import buildcraft.energy.BCEnergy;
import buildcraft.factory.BCFactory;
import buildcraft.robotics.BCRobotics;
import buildcraft.silicon.BCSilicon;
import buildcraft.transport.BCTransport;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

// Calen add
@Mod.EventBusSubscriber(modid = BCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCDataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Oil Texture
        generator.addProvider(new EnergyOilTextureProvider(generator, existingFileHelper));

//        // frozen fluid texture
//        generator.addProvider(new FrozenFluidTextureProvider(generator, existingFileHelper));

        // Tags
        BlockTagsProvider blockTagsProvider = new BCBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new BCItemTagsProvider(generator, existingFileHelper, blockTagsProvider));
        generator.addProvider(new BCFluidTagsProvider(generator, existingFileHelper));
        generator.addProvider(new EnergyBiomeTagsProvider(generator, existingFileHelper));

        // Recipes
        generator.addProvider(new BuildersRecipeGenerator(generator));
        generator.addProvider(new CoreRecipeGenerator(generator));
        generator.addProvider(new EnergyRecipeGenerator(generator));
        generator.addProvider(new FactoryRecipeGenerator(generator));
        generator.addProvider(new LibRecipeGenerator(generator));
        generator.addProvider(new SiliconRecipeGenerator(generator));
        generator.addProvider(new TransportRecipeGenerator(generator));
        // Mod Recipes
        generator.addProvider(new EnergyCustomRecipeGenerator(generator, existingFileHelper));
        generator.addProvider(new SiliconAssemblyRecipeGenerator(generator, existingFileHelper));
        generator.addProvider(new TransportAssemblyRecipeGenerator(generator, existingFileHelper));

        // Advancement
        generator.addProvider(new BCAdvancementProvider(generator, existingFileHelper));

        // Loot Table
        generator.addProvider(new BCLootGenerator(generator));

        // BlockState Model
        generator.addProvider(new CoreBlockStateProvider(generator, BCCore.MOD_ID, existingFileHelper));
        generator.addProvider(new BuildersBlockStateProvider(generator, BCBuilders.MOD_ID, existingFileHelper));
        generator.addProvider(new EnergyBlockStateProvider(generator, BCEnergy.MOD_ID, existingFileHelper));
        generator.addProvider(new FactoryBlockStateProvider(generator, BCFactory.MOD_ID, existingFileHelper));
        generator.addProvider(new SiliconBlockStateProvider(generator, BCSilicon.MOD_ID, existingFileHelper));
        generator.addProvider(new TransportBlockStateProvider(generator, BCTransport.MOD_ID, existingFileHelper));
        generator.addProvider(new RoboticsBlockStateProvider(generator, BCRobotics.MOD_ID, existingFileHelper));

        // Item Model
        generator.addProvider(new EnergyOilBucketModelProvider(generator, BCEnergy.MOD_ID, existingFileHelper));

        generator.addProvider(new CoreItemModelProvider(generator, BCCore.MOD_ID, existingFileHelper));
        generator.addProvider(new EnergyItemModelProvider(generator, BCEnergy.MOD_ID, existingFileHelper));
        generator.addProvider(new FactoryItemModelProvider(generator, BCFactory.MOD_ID, existingFileHelper));
        generator.addProvider(new BuildersItemModelProvider(generator, BCBuilders.MOD_ID, existingFileHelper));
        generator.addProvider(new SiliconItemModelProvider(generator, BCSilicon.MOD_ID, existingFileHelper));
        generator.addProvider(new TransportItemModelProvider(generator, BCTransport.MOD_ID, existingFileHelper));
        generator.addProvider(new LibItemModelProvider(generator, BCTransport.MOD_ID, existingFileHelper));
        generator.addProvider(new RoboticsItemModelProvider(generator, BCRobotics.MOD_ID, existingFileHelper));
    }

}
