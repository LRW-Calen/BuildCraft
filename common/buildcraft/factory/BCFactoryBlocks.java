package buildcraft.factory;

import buildcraft.factory.block.*;
import buildcraft.factory.tile.*;
import buildcraft.lib.block.BlockPropertiesCreater;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class BCFactoryBlocks {
    private static final RegistrationHelper HELPER = new RegistrationHelper(BCFactory.MODID);

    public static RegistryObject<BlockAutoWorkbenchItems> autoWorkbenchItems;
    //    public static RegistryObject<BlockAutoWorkbenchFluids> autoWorkbenchFluids;
    public static RegistryObject<BlockMiningWell> miningWell;
    public static RegistryObject<BlockPump> pump;
    public static RegistryObject<BlockTube> tube;
    public static RegistryObject<BlockFloodGate> floodGate;
    public static RegistryObject<BlockTank> tank;
    public static RegistryObject<BlockChute> chute;
    public static RegistryObject<BlockDistiller> distiller;
    public static RegistryObject<BlockHeatExchange> heatExchange;

    public static RegistryObject<BlockWaterGel> waterGel;

    public static RegistryObject<BlockEntityType<TileAutoWorkbenchItems>> autoWorkbenchItemsTile;
    public static RegistryObject<BlockEntityType<TileAutoWorkbenchFluids>> autoWorkbenchFluidsTile;
    public static RegistryObject<BlockEntityType<TileMiningWell>> miningWellTile;
    public static RegistryObject<BlockEntityType<TilePump>> pumpTile;
    public static RegistryObject<BlockEntityType<TileFloodGate>> floodGateTile;
    public static RegistryObject<BlockEntityType<TileTank>> tankTile;
    public static RegistryObject<BlockEntityType<TileChute>> chuteTile;
    public static RegistryObject<BlockEntityType<TileDistiller_BC8>> distillerTile;
    public static RegistryObject<BlockEntityType<TileHeatExchange>> heatExchangeTile;

    static {
        autoWorkbenchItems = HELPER.addBlockAndItem("block.autoworkbench.item", BlockPropertiesCreater.createDefaultProperties(Material.STONE), BlockAutoWorkbenchItems::new);
//        autoWorkbenchFluids = HELPER.addBlockAndItem("block.autoworkbench.fluid", BlockPropertiesCreater.createDefaultProperties(Material.STONE), BlockAutoWorkbenchFluids::new);
        miningWell = HELPER.addBlockAndItem("block.mining_well", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockMiningWell::new);
        pump = HELPER.addBlockAndItem("block.pump", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockPump::new);
        tube = HELPER.addBlock(
                "block.tube",
                BlockBehaviour.Properties.of(Material.METAL)
                        .strength(-1.0F, 3600000.0F) // setBlockUnbreakable()
                        .noOcclusion()
                        .noDrops()
                ,
                BlockTube::new
        );
        floodGate = HELPER.addBlockAndItem("block.flood_gate", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockFloodGate::new);
        tank = HELPER.addBlockAndItem(
                "block.tank",
                BlockPropertiesCreater.createDefaultProperties(Material.METAL)
                        .sound(SoundType.GLASS)
                        .lightLevel((state) -> 0)
                        .noOcclusion()
                ,
                BlockTank::new
        );
        chute = HELPER.addBlockAndItem("block.chute",
                BlockPropertiesCreater.createDefaultProperties(Material.METAL)
                        .lightLevel((state) -> 0)
                        .noOcclusion()
                ,
                BlockChute::new);
        distiller = HELPER.addBlockAndItem(
                "block.distiller",
                BlockPropertiesCreater.createDefaultProperties(Material.METAL)
                        .sound(SoundType.GLASS)
                        .lightLevel((state) -> 0)
                        .noOcclusion()
                ,
                BlockDistiller::new
        );
        heatExchange = HELPER.addBlockAndItem(
                "block.heat_exchange",
                BlockPropertiesCreater.createDefaultProperties(Material.METAL)
                        .sound(SoundType.GLASS)
                        .noOcclusion()
                        .isViewBlocking((p0, p1, p2) -> false)
                        .isSuffocating((p0, p1, p2) -> false) // 窒息
                ,
                BlockHeatExchange::new
        );
        waterGel = HELPER.addBlock(
                "block.water_gel",
                BlockPropertiesCreater.createDefaultProperties(Material.CLAY)
                        .sound(SoundType.SLIME_BLOCK)
                        .randomTicks()
                ,
                BlockWaterGel::new
        );

        autoWorkbenchItemsTile = HELPER.registerTile("tile.autoworkbench.item", TileAutoWorkbenchItems::new, autoWorkbenchItems);
//        autoWorkbenchFluidsTile = HELPER.registerTile("tile.autoworkbench.fluid", TileAutoWorkbenchFluids::new, autoWorkbenchFluids);
        miningWellTile = HELPER.registerTile("tile.mining_well", TileMiningWell::new, miningWell);
        pumpTile = HELPER.registerTile("tile.pump", TilePump::new, pump);
        floodGateTile = HELPER.registerTile("tile.flood_gate", TileFloodGate::new, floodGate);
        tankTile = HELPER.registerTile("tile.tank", TileTank::new, tank);
        chuteTile = HELPER.registerTile("tile.chute", TileChute::new, chute);
        distillerTile = HELPER.registerTile("tile.distiller", TileDistiller_BC8::new, distiller);
        heatExchangeTile = HELPER.registerTile("tile.heat_exchange", TileHeatExchange::new, heatExchange);
    }

    public static void fmlPreInit() {

    }

}
