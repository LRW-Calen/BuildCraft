package buildcraft.lib;

import buildcraft.api.registry.EventBuildCraftReload;
import buildcraft.api.tiles.IDebuggable;
import buildcraft.lib.command.CommandBuildCraft;
import buildcraft.lib.gui.config.GuiConfigManager;
import buildcraft.lib.item.ItemDebugger;
import buildcraft.lib.client.guide.GuideManager;
import buildcraft.lib.client.render.DetachedRenderer;
import buildcraft.lib.debug.BCAdvDebugging;
import buildcraft.lib.debug.ClientDebuggables;
import buildcraft.lib.marker.MarkerCache;
import buildcraft.lib.misc.FakePlayerProvider;
import buildcraft.lib.misc.MessageUtil;
import buildcraft.lib.net.MessageDebugRequest;
import buildcraft.lib.net.MessageManager;
import buildcraft.lib.net.cache.BuildCraftObjectCaches;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public enum BCLibEventDistForgeBus
{
    INSTANCE;

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        LevelAccessor levelAccessor = event.getWorld();
        if (levelAccessor instanceof Level level)
        {
            MarkerCache.onWorldUnload(level);
            if (level instanceof ServerLevel serverLevel)
            {
                FakePlayerProvider.INSTANCE.unloadWorld(serverLevel);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onReloadFinish(EventBuildCraftReload.FinishLoad event)
    {
        // Note: when you need to add server-side listeners the client listeners need to be moved to BCLibProxy
        GuideManager.INSTANCE.onRegistryReload(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
//    public static void onConnectToServer(ClientConnectedToServerEvent event)
    public static void onConnectToServer(ClientPlayerNetworkEvent.LoggedInEvent event)
    {
        BuildCraftObjectCaches.onClientJoinServer();
    }


    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer)
        {
            ServerPlayer playerMP = (ServerPlayer) entity;
            // Delay sending join messages to player as it makes it work when in single-player
            MessageUtil.doDelayedServer(() -> MarkerCache.onPlayerJoinWorld(playerMP));
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderWorldLast(RenderLevelStageEvent event)
    {
//        if(event.getStage()!= RenderLevelStageEvent.Stage.AFTER_PARTICLES) AFTER_PARTICLES 会炸
        // DetachedRenderer.fromWorldOriginPre(buildcraftcore/DetachedRenderer.java:98)
        // GL11.glPushMatrix();
        // No context is current or a function that is not available in the current context was called. The JVM will abort execution.
//        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER)
        // Calen: AFTER_SKY is the right state for this render
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY)
        {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        float partialTicks = event.getPartialTick();
        DetachedRenderer.INSTANCE.renderWorldLastEvent(player, partialTicks, event.getPoseStack(), event.getCamera());
    }

    // Forge Bus
    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            BCAdvDebugging.INSTANCE.onServerPostTick();
            MessageUtil.postServerTick();
        }
    }

    // Forge Bus
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            BuildCraftObjectCaches.onClientTick();
            MessageUtil.postClientTick();
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player != null && ItemDebugger.isShowDebugInfo(player))
            {
                HitResult mouseOver = mc.hitResult;
                if (mouseOver != null)
                {
                    IDebuggable debuggable = ClientDebuggables.getDebuggableObject(mouseOver);
                    if (debuggable instanceof BlockEntity tile)
                    {
//                        MessageManager.sendToServer(new MessageDebugRequest(tile.getBlockPos(), mouseOver.sideHit));
                        MessageManager.sendToServer(new MessageDebugRequest(tile.getBlockPos(), ((BlockHitResult) mouseOver).getDirection()));
                    }
                    else if (debuggable instanceof Entity entity)
                    {
                        // TODO: Support entities!
                        // Calen add
                        MessageManager.sendToServer(new MessageDebugRequest(entity.getOnPos(), null));
                    }
                }
            }
        }
    }

    // Calen: from BCLib
    @SubscribeEvent
    public static void serverStarting(ServerStartingEvent event)
    {
//        event.registerServerCommand(new CommandBuildCraft());
        CommandDispatcher<CommandSourceStack> dispatcher = event.getServer().getCommands().getDispatcher();
        CommandBuildCraft.register(dispatcher);
    }

    // Calen: update guidebook not to early
    private static List<ResourceManagerReloadListener> reloadListeners = new ArrayList<>();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
//    public static void onTagsUpdatedEvent(TagsUpdatedEvent event)
    public static void onTagsUpdatedEvent(RecipesUpdatedEvent event)
    {
        for (ResourceManagerReloadListener reloadListener : reloadListeners)
        {
            reloadListener.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
        }
        GuiConfigManager.loadFromConfigFile();
    }

    // Calen: only client call
    public static void addReloadListeners(ResourceManagerReloadListener reloadListener)
    {
        reloadListeners.add(reloadListener);
    }

//    // Calen: to load guide
//    // moved from BCLibProxy$ClientProxy#fmlPostInit
//    // at fmlPostInit -> RuntimeException: getAtlasTexture called too early!
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void onTextureStitchPost(TextureStitchEvent.Post event)
//    {
//        if(event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS))
//        {
//            GuideManager.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
//        }
//    }
}
