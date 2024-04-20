package buildcraft.factory;

import buildcraft.factory.tile.TileMiner;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BCFactory.MODID)
public class BCFactoryForgeBusEventDist {
    @SubscribeEvent
    public static void onPlayerDestroyBlock(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() != BCFactoryBlocks.tube.get()) {
            return;
        }
        BlockPos currentPos = event.getPos();
        Level world = event.getPlayer().getLevel();
        // noinspection StatementWithEmptyBody
        while (world.getBlockState(currentPos = currentPos.above()).getBlock() == BCFactoryBlocks.tube.get()) {
        }
        if (world.getBlockEntity(currentPos) instanceof TileMiner) {
            event.setCanceled(true);
        }

    }
}
