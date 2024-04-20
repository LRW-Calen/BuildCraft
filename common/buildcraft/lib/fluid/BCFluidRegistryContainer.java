package buildcraft.lib.fluid;

import net.minecraft.world.item.BucketItem;
import net.minecraftforge.registries.RegistryObject;

public class BCFluidRegistryContainer {
    private RegistryObject<BCFluid.Source> stillFluid;
    //    private RegistryObject<? extends ForgeFlowingFluid.Flowing> flowingFluid;
    private RegistryObject<BCFluid.Flowing> flowingFluid;
    private RegistryObject<BCFluidBlock> block;
    private RegistryObject<BucketItem> bucket;

    //    public ForgeFlowingFluid.Source getStillFluid()
    public BCFluid.Source getStillFluid() {
        return stillFluid.get();
    }

    //    public ForgeFlowingFluid.Flowing getFlowingFluid()
    public BCFluid.Flowing getFlowingFluid() {
        return flowingFluid.get();
    }

    public BCFluidBlock getBlock() {
        return block.get();
    }
//    public RegistryObject<BCFluidBlock> getBlockReg() {
//        return blockRO;
//    }

    public BucketItem getBucket() {
        return bucket.get();
    }

    //Make sure these update methods are package local as only the FluidDeferredRegister should be messing with them
    public void setStill(RegistryObject<BCFluid.Source> stillFluid) {
        this.stillFluid = stillFluid;
    }

    //    public void setFlowing(RegistryObject<? extends ForgeFlowingFluid.Flowing> flowingFluid)
    public void setFlow(RegistryObject<BCFluid.Flowing> flowingFluid) {
        this.flowingFluid = flowingFluid;
    }

    public void setBlock(RegistryObject<BCFluidBlock> block) {
        this.block = block;
    }

    public void setBucket(RegistryObject<BucketItem> bucket) {
        this.bucket = bucket;
    }

}