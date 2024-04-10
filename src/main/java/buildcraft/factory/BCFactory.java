package buildcraft.factory;

import buildcraft.core.BCCore;
import buildcraft.factory.client.render.RenderPump;
import buildcraft.factory.loot.LootConditionSpreading;
import buildcraft.factory.recipe.DistillationRecipeSerializer;
import buildcraft.factory.recipe.HeatExchangeRecipeSerializer;
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
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Consumer;

@Mod(BCFactory.MOD_ID)
@Mod.EventBusSubscriber(modid = BCFactory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCFactory
{
    public static final String MOD_ID = "buildcraftfactory";

    public BCFactory()
    {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        BCFactoryBlockEntities.BLOCK_ENTITIES.register(modEventBus);
//        BCFactoryBlocks.BLOCKS.register(modEventBus);
//        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, this::attachAbility);

        // Calen: reg pump tube texture for load
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            RenderPump.textureStitchPre();
        }

        modEventBus.addGenericListener(MenuType.class, BCFactoryMenuTypes::registerAll);
        modEventBus.register(BCFactoryModels.class);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(BCFactoryBlocks.tank.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BCFactoryBlocks.distiller.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BCFactoryBlocks.heatExchange.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BCFactoryBlocks.chute.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        IForgeRegistry<RecipeSerializer<?>> registry = event.getRegistry();
        registry.register(HeatExchangeRecipeSerializer.HEATABLE);
        registry.register(HeatExchangeRecipeSerializer.COOLABLE);
        registry.register(DistillationRecipeSerializer.INSTANCE);

        // Calen
        LootConditionSpreading.reg();
    }

    @SubscribeEvent
    public static void fmlPreInit(FMLConstructModEvent event)
    {
        RegistryConfig.useOtherModConfigFor(MOD_ID, BCCore.MOD_ID);

        BCFactoryBlocks.init();
        BCFactoryItems.init();

        // Calen: moved to @Mod.EventBusSubscriber
//        BCFactoryModels.fmlPreInit();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void fmlInit(FMLClientSetupEvent event)
    {

//        BCFactoryModels.fmlInit();
    }


    private static final TagManager tagManager = new TagManager();

    static
    {
        startBatch();// factory
        // BC Factory Items
        registerTag("item.plastic.sheet").reg("plastic_sheet").locale("plasticSheet");
        registerTag("item.water_gel_spawn").reg("water_gel_spawn").locale("waterGel");
//                .model("water_gel");
        registerTag("item.gel").reg("gel").locale("gel");
//                .model("gel");
        // BC Factory Item Blocks
        registerTag("item.block.plastic").reg("plastic_block").locale("plasticBlock");
//                .model("plastic_block/");
        registerTag("item.block.autoworkbench.item").reg("autoworkbench_item").locale("autoWorkbenchBlock");
//                .model("autoworkbench_item");
        registerTag("item.block.autoworkbench.fluid").reg("autoworkbench_fluid").locale("autoWorkbenchFluidBlock");
//                .model("autoworkbench_fluid");
        registerTag("item.block.mining_well").reg("mining_well").locale("miningWellBlock");
//                .model("mining_well");
        registerTag("item.block.pump").reg("pump").locale("pumpBlock");
//                .model("pump");
        registerTag("item.block.flood_gate").reg("flood_gate").locale("floodGateBlock");
//                .model("flood_gate");
        registerTag("item.block.tank").reg("tank").locale("tankBlock");
//                .model("tank");
        registerTag("item.block.chute").reg("chute").locale("chuteBlock");
//                .model("chute");
        registerTag("item.block.distiller").reg("distiller").locale("distiller");
//                .model("distiller");
        TagManager.TagEntry tag = registerTag("item.block.heat_exchange").reg("heat_exchange").locale("heat_exchange");
//        tag.model("heat_exchange");
        // BC Factory Blocks
        registerTag("block.autoworkbench.item").reg("autoworkbench_item").locale("autoWorkbenchBlock");
//                .model("autoworkbench_item");
        registerTag("block.autoworkbench.fluid").reg("autoworkbench_fluid").locale("autoWorkbenchFluidBlock");
//                .model("autoworkbench_fluid");
        registerTag("block.mining_well").reg("mining_well").locale("miningWellBlock");
//                .model("mining_well");
        registerTag("block.pump").reg("pump").locale("pumpBlock");
//                .model("pump");
        registerTag("block.tube").reg("tube").locale("tubeBlock");
//                .model("tube");
        registerTag("block.flood_gate").reg("flood_gate").locale("floodGateBlock");
//                .model("flood_gate");
        registerTag("block.tank").reg("tank").locale("tankBlock");
//                .model("tank");
        registerTag("block.chute").reg("chute").locale("chuteBlock");
//                .model("chute");
        registerTag("block.water_gel").reg("water_gel").locale("waterGel");
//                .model("water_gel");
        registerTag("block.distiller").reg("distiller").locale("distiller");
//                .model("distiller");
        tag = registerTag("block.heat_exchange").reg("heat_exchange").locale("heat_exchange");
//                .model("heat_exchange");
        // BC Factory Tiles
        registerTag("tile.autoworkbench.item").reg("autoworkbench_item");
        registerTag("tile.autoworkbench.fluid").reg("autoworkbench_fluid");
        registerTag("tile.mining_well").reg("mining_well");
        registerTag("tile.pump").reg("pump");
        registerTag("tile.flood_gate").reg("flood_gate");
        registerTag("tile.tank").reg("tank");
        registerTag("tile.chute").reg("chute");
        registerTag("tile.distiller").reg("distiller");
        registerTag("tile.heat_exchange").reg("heat_exchange");

//        endBatch(TagManager.prependTags("buildcraftfactory:", TagManager.EnumTagType.REGISTRY_NAME, TagManager.EnumTagType.MODEL_LOCATION)
        endBatch(TagManager.prependTags("buildcraftfactory:", TagManager.EnumTagType.REGISTRY_NAME)
                .andThen(TagManager.setTab("buildcraft.main"))
        );
    }

    private static TagManager.TagEntry registerTag(String id)
    {
        return tagManager.registerTag(id);
    }

    private static void startBatch()
    {
        tagManager.startBatch();
    }

    private static void endBatch(Consumer<TagManager.TagEntry> consumer)
    {
        tagManager.endBatch(consumer);
    }
}
