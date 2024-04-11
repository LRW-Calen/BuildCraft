package buildcraft.datagen.silicon;

import buildcraft.silicon.BCSilicon;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.silicon.BCSiliconItems;
import buildcraft.silicon.BCSiliconModBusEventDist;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SiliconItemModelProvider extends ItemModelProvider
{
    private static final ResourceLocation generated = new ResourceLocation("minecraft", "item/generated");

    public SiliconItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        BCSiliconItems.variantGateMap.values().forEach(
                reg ->
                        getBuilder(reg.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"))
        );
        withExistingParent(BCSiliconItems.chipsetRedstone.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftsilicon:items/redstone_chipset/red");
        withExistingParent(BCSiliconItems.chipsetDiamond.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftsilicon:items/redstone_chipset/diamond");
        withExistingParent(BCSiliconItems.chipsetGold.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftsilicon:items/redstone_chipset/gold");
        withExistingParent(BCSiliconItems.chipsetIron.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftsilicon:items/redstone_chipset/iron");
        withExistingParent(BCSiliconItems.chipsetQuartz.get().getRegistryName().toString(), generated)
                .texture("layer0", "buildcraftsilicon:items/redstone_chipset/quartz");

        // Tables
        withExistingParent(BCSiliconBlocks.advancedCraftingTable.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/table/advanced_crafting"));
        withExistingParent(BCSiliconBlocks.assemblyTable.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/table/assembly"));
        withExistingParent(BCSiliconBlocks.chargingTable.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/table/charging"));
        withExistingParent(BCSiliconBlocks.integrationTable.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/table/integration"));
        withExistingParent(BCSiliconBlocks.programmingTable.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/table/programming"))
                .texture("glass", "minecraft:block/white_stained_glass");
        // laser
        withExistingParent(BCSiliconBlocks.laser.get().getRegistryName().toString(), new ResourceLocation("buildcraftsilicon:block/laser"));
        // gate_copier
        ResourceLocation gateCopier = BCSiliconItems.gateCopier.get().getRegistryName();
        getBuilder(gateCopier.toString())
                .override()
                .model(
                        withExistingParent(gateCopier.getNamespace() + ":item/" + gateCopier.getPath() + "/empty", generated)
                                .texture("layer0", "buildcraftsilicon:items/gatecopier/empty")
                )
                .predicate(BCSiliconModBusEventDist.PREDICATE_HAS_DATA, 0)
                .end()
                .override()
                .model(
                        withExistingParent(gateCopier.getNamespace() + ":item/" + gateCopier.getPath() + "/full", generated)
                                .texture("layer0", "buildcraftsilicon:items/gatecopier/full")
                )
                .predicate(BCSiliconModBusEventDist.PREDICATE_HAS_DATA, 1)
                .end()
        ;

        getBuilder(BCSiliconItems.plugLens.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
        getBuilder(BCSiliconItems.plugPulsar.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
        getBuilder(BCSiliconItems.plugLightSensor.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
        getBuilder(BCSiliconItems.plugFacade.get().getRegistryName().toString()).parent(new ModelFile.UncheckedModelFile("minecraft:builtin/entity"));
    }
}
