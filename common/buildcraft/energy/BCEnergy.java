package buildcraft.energy;

import buildcraft.core.BCCore;
import buildcraft.api.enums.EnumSpring;
import buildcraft.energy.recipe.CoolantRecipeSerializer;
import buildcraft.energy.recipe.FuelRecipeSerializer;
import buildcraft.lib.fluid.BCFluid;
import buildcraft.lib.registry.RegistryConfig;
import buildcraft.lib.registry.TagManager;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

@Mod(BCEnergy.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCEnergy
{
    public static final String MOD_ID = "buildcraftenergy";

    public BCEnergy()
    {
        RegistryConfig.useOtherModConfigFor(MOD_ID, BCCore.MOD_ID);
        BCEnergyConfig.preInit();


//        BCEnergyFluids.preInit();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        BCEnergyBlocks.init();
//        BCEnergyItems.init();
//        BCEnergyBlocks.BLOCK_ENTITIES.register(modEventBus);
//        BCEnergyFluids.FLUIDS.register(modEventBus);
        BCEnergyFluids.register(modEventBus);
//        BCEnergyBlocks.BLOCKS.register(modEventBus);
//        BCEnergyItems.ITEMS.register(modEventBus);
//        modEventBus.addGenericListener(StructureFeature.class, BCStructures::register);
        BCEnergyWorldGen.init();
//        BCBiomeRegistry.BIOMES.register(modEventBus);
//        BCBiomeRegistry.init();

//        BCEnergyModels.fmlPreInit();

        modEventBus.addGenericListener(MenuType.class, BCEnergyMenuTypes::registerAll);
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        IForgeRegistry<RecipeSerializer<?>> registry = event.getRegistry();
        registry.register(FuelRecipeSerializer.INSTANCE);
        registry.register(CoolantRecipeSerializer.INSTANCE);
    }

    @SubscribeEvent
    public static void preInit(FMLConstructModEvent event)
    {
        // Calen: from BCEnergyProxy
        // Should before BCEnergyFluids.preInit() and BCEnergyBlocks.preInit() to set christmas special fluid data
        BCEnergyProxy.getProxy().fmlPreInit();

        BCEnergyBlocks.preInit();
        BCEnergyItems.preInit();
        BCEnergyFluids.preInit();
//        BCEnergyBlocks.preInit();
//        EnumSpring.OIL.liquidBlock = BCEnergyFluids.crudeOil[0].getBlock().getDefaultState();

//        HELPER.registerTile(TileSpringOil.class, "tile.spring.oil");
//        HELPER.registerTile(TileEngineStone_BC8.class, "tile.engine.stone");
//        HELPER.registerTile(TileEngineIron_BC8.class, "tile.engine.iron");
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event)
    {
        // Calen: from BCFluidBlock#<init>
        for (RegistryObject<BCFluid.Source> fluid : BCEnergyFluids.allStill)
        {
            ItemBlockRenderTypes.setRenderLayer(fluid.get().getSource(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(fluid.get().getFlowing(), RenderType.solid());
        }
    }

    @SubscribeEvent
    public static void commonInit(FMLCommonSetupEvent event)
    {
        BCEnergyFluids.registerBucketDispenserBehavior();
    }

    @SubscribeEvent
    public static void postInit(FMLLoadCompleteEvent event)
    {
        event.enqueueWork(() ->
        {
//            RegistryConfig.useOtherModConfigFor(MOD_ID, BuildCraft.MOD_ID);
//            BCEnergyConfig.preInit();
            // Calen: This SHOULD be put in enqueueWork or BCEnergyFluids.OIL is rot created
//            EnumSpring.OIL.liquidBlock = BCEnergyFluids.OIL.getBlock().defaultBlockState();
//            EnumSpring.OIL.liquidBlock = BCEnergyFluids.crudeOil[0].get().getBlock().defaultBlockState();
            EnumSpring.OIL.liquidBlock = BCEnergyFluids.crudeOil[0].get().defaultFluidState().createLegacyBlock();
        });

        // Calen: moved to datagen
//        // Calen: from BCEnergy#init
//        BCEnergyRecipes.init();
    }

    // Calen: moved to BCEnergyModels.class @Mod.EventBusSubscriber
//    @OnlyIn(Dist.CLIENT)
//    @Mod.EventBusSubscriber(modid = NameSpaces.BUILDCRAFT_ENERGY, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class ModBusEvents
//    {
//        @SubscribeEvent
//        public static void preInit(FMLCommonSetupEvent event)
//        {
//            BCEnergyModels.fmlPreInit();
//        }
//    }

    // TODO Calen biome???
    @SubscribeEvent
    public static void registerSerializers(RegistryEvent.Register<RecipeSerializer<?>> evt)
    {
//        Registry.register(Registry.BIOME_SOURCE, ">>>", BCBiomeProvider.TF_CODEC);
//        Registry.register(Registry.CHUNK_GENERATOR, ">>>", BCChunkGenerator.CODEC);
    }

    private static final TagManager tagManager = new TagManager();

    static
    {
        // Calen: in namespace Energy
        startBatch();
        // Items
//        registerTag("item.glob.oil").reg("glob_of_oil").oldReg("glob_oil").locale("globOil").model("glob_oil");
//        registerTag("item.glob_oil").reg("glob_oil").locale("globOil").model("glob_oil");
        registerTag("item.glob_oil").reg("glob_oil").locale("globOil");
//        registerTag("item.oil_placer").reg("oil_placer").locale("oilPlacer").model("glob_oil");
        registerTag("item.oil_placer").reg("oil_placer").locale("oilPlacer");

        // Item Blocks

//        endBatch(TagManager.prependTags("buildcraftenergy:", TagManager.EnumTagType.REGISTRY_NAME, TagManager.EnumTagType.MODEL_LOCATION)
        endBatch(TagManager.prependTags("buildcraftenergy:", TagManager.EnumTagType.REGISTRY_NAME)
                .andThen(TagManager.setTab("buildcraft.main"))
        );

        // Calen: in namespace Core
        startBatch();
        // Item Blocks
//        registerTag("item.block.engine.bc.stone").reg("engine_stone").locale("engineStone").model("");
        registerTag("item.block.engine.bc.stone").reg("engine_stone").locale("engineStone");
//        registerTag("item.block.engine.bc.iron").reg("engine_iron").locale("engineIron").model("");
        registerTag("item.block.engine.bc.iron").reg("engine_iron").locale("engineIron");
        // Blocks
        registerTag("block.engine.bc.stone").reg("engine_stone").locale("engineStone");
//        registerTag("block.engine.bc.stone").locale("engine_stone");
        registerTag("block.engine.bc.iron").reg("engine_iron").locale("engineIron");
//        registerTag("block.engine.bc.iron").locale("engine_iron");

        // Tiles
        registerTag("tile.engine.stone").reg("engine_stone");
        registerTag("tile.engine.iron").reg("engine_iron");
        registerTag("tile.spring.oil").reg("spring_oil");

//        endBatch(TagManager.prependTags("buildcraftcore:", TagManager.EnumTagType.REGISTRY_NAME, TagManager.EnumTagType.MODEL_LOCATION)
        endBatch(TagManager.prependTags("buildcraftcore:", TagManager.EnumTagType.REGISTRY_NAME)
                .andThen(TagManager.setTab("buildcraft.main"))
        );
    }

    private static TagManager.TagEntry registerTag(String id)
    {
//        return TagManager.registerTag(id);
        return tagManager.registerTag(id);
    }

    private static void startBatch()
    {
//        TagManager.startBatch();
        tagManager.startBatch();
    }

    private static void endBatch(Consumer<TagManager.TagEntry> consumer)
    {
//        TagManager.endBatch(consumer);
        tagManager.endBatch(consumer);
    }
}
