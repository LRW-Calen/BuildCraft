package buildcraft.energy;

import buildcraft.api.BCModules;
import buildcraft.energy.item.BCBucketItem;
import buildcraft.lib.fluid.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BCEnergyFluids {
    private static final DeferredRegister<Fluid> fluidRegister = DeferredRegister.create(ForgeRegistries.FLUIDS, BCEnergy.MODID);
    private static final DeferredRegister<Block> blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, BCEnergy.MODID);
    private static final DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, BCEnergy.MODID);


    static {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        blockRegister.register(bus);
        fluidRegister.register(bus);
        itemRegister.register(bus);
    }

    public static List<RegistryObject<BCFluid.Source>> getAllStill() {
        return Collections.unmodifiableList(allStill);
    }

    public static List<RegistryObject<BCFluid.Flowing>> getAllFlow() {
        return Collections.unmodifiableList(allFlow);
    }

    private static final DispenseItemBehavior BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
            Level world = source.getLevel();
            DispensibleContainerItem bucket = (DispensibleContainerItem) stack.getItem();
            BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (bucket.emptyContents(null, world, pos, null)) {
                bucket.checkExtraContent(null, world, stack, pos);
                return new ItemStack(Items.BUCKET);
            }
            return super.execute(source, stack);
        }
    };

    public static void registerBucketDispenserBehavior() {
        for (RegistryObject<BCFluid.Source> fluid : getAllStill()) {
            DispenserBlock.registerBehavior(fluid.get().getBucket(), BUCKET_DISPENSE_BEHAVIOR);
        }
    }

    public static RegistryObject<BCFluid.Source>[] crudeOil;
    /** All 3 fuels (no residue) */
    public static RegistryObject<BCFluid.Source>[] oilDistilled;
    /** The 3 heaviest components (fuelLight, fuelDense and oilResidue) */
    public static RegistryObject<BCFluid.Source>[] oilHeavy;
    /** The 2 lightest fuels (no dense fuel) */
    public static RegistryObject<BCFluid.Source>[] fuelMixedLight;
    /** The 2 heaviest fuels (no gaseous fuel) */
    public static RegistryObject<BCFluid.Source>[] fuelMixedHeavy;
    /** The 2 heaviest products (fuelDense and oilResidue) */
    public static RegistryObject<BCFluid.Source>[] oilDense;

    // End products in order from least to most dense
    public static RegistryObject<BCFluid.Source>[] fuelGaseous;
    public static RegistryObject<BCFluid.Source>[] fuelLight;
    public static RegistryObject<BCFluid.Source>[] fuelDense;
    public static RegistryObject<BCFluid.Source>[] oilResidue;

    public static RegistryObject<BCFluid.Source>[] tar;

    public static final List<RegistryObject<BCFluid.Source>> allStill = new ArrayList<>();
    public static final List<RegistryObject<BCFluid.Flowing>> allFlow = new ArrayList<>();
    public static int[][] data = { //@formatter:off
        // Tabular form of all the fluid values
        // density, viscosity, boil, spread,  tex_light,   tex_dark, sticky, flammable
        {      900,      2000,    3,      6, 0x50_50_50, 0x05_05_05,      1,         1 },// Crude Oil
        {     1200,      4000,    3,      4, 0x10_0F_10, 0x42_10_42,      1,         0 },// Residue
        {      850,      1800,    3,      6, 0xA0_8F_1F, 0x42_35_20,      1,         1 },// Heavy Oil
        {      950,      1600,    3,      5, 0x87_6E_77, 0x42_24_24,      1,         1 },// Dense Oil
        {      750,      1400,    2,      8, 0xE4_AF_78, 0xB4_7F_00,      0,         1 },// Distilled Oil
        {      600,       800,    2,      7, 0xFF_AF_3F, 0xE0_7F_00,      0,         1 },// Dense Fuel
        {      700,      1000,    2,      7, 0xF2_A7_00, 0xC4_87_00,      0,         1 },// Mixed Heavy Fuels
        {      400,       600,    1,      8, 0xFF_FF_30, 0xE4_CF_00,      0,         1 },// Light Fuel
        {      650,       900,    1,      9, 0xF6_D7_00, 0xC4_B7_00,      0,         1 },// Mixed Light Fuels
        {      300,       500,    0,     10, 0xFA_F6_30, 0xE0_D9_00,      0,         1 },// Gas Fuel
    };//@formatter:on

    // Calen
    public static String STILL_SUFFIX = "_still";
    public static String FLOW_SUFFIX = "_flow";

    // Calen
    public static String FLUID_TRANSLATION_PREFIX = "buildcraft.fluid.heat_";
    public static String HEAT_TRANSLATION_PREFIX = "fluid.";
    public static boolean allowGas = true;

    public static void preInit() {
        if (BCModules.FACTORY.isLoaded()) {
            int index = 0;

            // Add all of the fluid states
            crudeOil = defineFluids(data[index++], "oil");
            oilResidue = defineFluids(data[index++], "oil_residue");
            oilHeavy = defineFluids(data[index++], "oil_heavy");
            oilDense = defineFluids(data[index++], "oil_dense");
            oilDistilled = defineFluids(data[index++], "oil_distilled");
            fuelDense = defineFluids(data[index++], "fuel_dense");
            fuelMixedHeavy = defineFluids(data[index++], "fuel_mixed_heavy");
            fuelLight = defineFluids(data[index++], "fuel_light");
            fuelMixedLight = defineFluids(data[index++], "fuel_mixed_light");
            fuelGaseous = defineFluids(data[index++], "fuel_gaseous");
        } else {
            crudeOil = new RegistryObject[] { defineFluid(data[0], 0, "oil") };
            oilResidue = new RegistryObject[0];
            oilHeavy = new RegistryObject[0];
            oilDense = new RegistryObject[0];
            oilDistilled = new RegistryObject[0];
            fuelDense = new RegistryObject[0];
            fuelMixedHeavy = new RegistryObject[0];
            fuelLight = new RegistryObject[] { defineFluid(data[7], 0, "fuel_light") };
            fuelMixedLight = new RegistryObject[0];
            fuelGaseous = new RegistryObject[0];
        }
    }

    private static RegistryObject<BCFluid.Source>[] defineFluids(int[] data, String name) {
        RegistryObject<BCFluid.Source>[] arr = new RegistryObject[3];
        for (int h = 0; h < 3; h++) {
            arr[h] = defineFluid(data, h, name);
        }
        return arr;
    }

    private static RegistryObject<BCFluid.Source> defineFluid(int[] data, int heat, String name) {
        final int density = data[0];
        final int baseViscosity = data[1];
        final int boilPoint = data[2];
        final int baseQuanta = data[3];
        final int texLight = data[4];
        final int texDark = data[5];
        final boolean sticky = BCEnergyConfig.oilIsSticky && data[6] == 1;
        final boolean flammable = BCEnergyConfig.enableOilBurn ? data[7] == 1 : false;

        String fullName = name + "_heat_" + heat;
        int tempAdjustedViscosity = baseViscosity * (4 - heat) / 4;
        int boilAdjustedDensity = density * (heat >= boilPoint ? -1 : 1);

        String fluidTexture = "buildcraftenergy:fluids/" + fullName;
        BCFluidRegistryContainer fluidRegistryContainer = new BCFluidRegistryContainer();

        FluidAttributes.Builder attributeBuilder = BCFluidAttributes.builder(
                        new ResourceLocation(fluidTexture + STILL_SUFFIX),
                        new ResourceLocation(fluidTexture + FLOW_SUFFIX)
                )
                .setHeat(heat)
                .setHeatable(true)
                .setColour(texLight, texDark)
//                .translationKey("buildcraft.fluid.heat_" + heat, name)
                .translationKey(HEAT_TRANSLATION_PREFIX + name)
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
                // Calen: if use color filter on white texture of water, there should be Water Overlay
                // if the texture is colored, [.overlay(WATER_OVERLAY) and .color(0xFFFFFFFF)] will make the fluid block looks white behind glass block because of .color(0xFFFFFFFF)
//                .overlay(WATER_OVERLAY)
                .overlay(null)
                .temperature(300 + 20 * heat)
                .density(boilAdjustedDensity)
                .viscosity(tempAdjustedViscosity)
                .color(0xFFFFFFFF) // Calen: filter colour based on the texture colour
                ;
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
//        BCFluidBase.Properties properties = new BCFluidBase.Properties(
                fluidRegistryContainer::getStillFluid,
                fluidRegistryContainer::getFlowingFluid,
                attributeBuilder
        )
                .bucket(fluidRegistryContainer::getBucket)
                .block(fluidRegistryContainer::getBlock)
                .tickRate(tempAdjustedViscosity / 200) // Calen: in 1.12.2 this is automatically calculated by mc, but 1.18.2 not
                // Distance that the fluid will travel: 1->16
                // Higher heat values travel a little further
                // Calen: 1.12.2 max = 16, 1.18.2 max = 8
                // 1.18.2 here is decrease, not range
                .levelDecreasePerBlock(8 / Math.min((baseQuanta + (baseQuanta > 6 ? heat : heat / 2)) / 2, 8));
//        if (boilAdjustedDensity < 0)
        if (boilAdjustedDensity < 0 && allowGas) {
            attributeBuilder.gaseous();
        }
        RegistryObject<BCFluid.Source> still = fluidRegister.register(
                fullName,
                () ->
                {
                    BCFluid.Source def = new BCFluid.Source(
//                            new ResourceLocation(fluidTexture + "_still"),
//                            new ResourceLocation(fluidTexture + "_flow"),
                            properties,
                            fluidRegistryContainer
                    );
//                    def.setMapColour(getMapColor(texDark));
//                    def.setFlammable(flammable);
//                    def.setHeat(heat);
//        def.setUnlocalizedName(name);
//                    def.setRegistryName(fullName);
//                    def.setTemperature(300 + 20 * heat); // Calen: moved to FluidAttributes
//        def.setDensity(boilAdjustedDensity);
//        def.setGaseous(def.getAttributes().getDensity() < 0); // moved to attributeBuilder
                    ;
//                    def.setHeatable(true);
//        FluidManager.register(def);

//        BCFluidBlock block = (BCFluidBlock) def.getBlock();
//        // TODO Calen setLightOpacity???
////        block.setLightOpacity(3);
//        block.setSticky(sticky);

                    return def;
                }
        );
        fluidRegistryContainer.setStill(
                still
        );
        RegistryObject<BCFluid.Flowing> flow = fluidRegister.register(
                fullName + "_flow",
                () -> new BCFluid.Flowing(properties, fluidRegistryContainer)
        );
        fluidRegistryContainer.setFlow(
                flow
        );
        allFlow.add(flow);
        fluidRegistryContainer.setBucket(
                itemRegister.register(
                        fullName + "_bucket",
                        () -> new BCBucketItem(
                                fluidRegistryContainer::getStillFluid,
                                new Item.Properties()
                                        .stacksTo(1)
                                        .craftRemainder(Items.BUCKET)
                        )
                )
        );
        String fluidBlockName = "fluid_block_" + name + "_heat_" + heat;
        fluidRegistryContainer.setBlock(
                blockRegister.register(
                        fluidBlockName,
                        () -> new BCFluidBlock(
                                fluidRegistryContainer::getStillFluid,
                                BlockBehaviour.Properties.of(new BCMaterialFluid(getMapColor(texDark), flammable))
                                        .noCollission()
                                        .randomTicks()
                                        .strength(100.0F)
                                        .noDrops()
                                ,
                                sticky
                        )
                )
        );
        allStill.add(still);
        return still;
    }

    //    private static MapColor getMapColor(int color)
    private static MaterialColor getMapColor(int color) {
//        MapColor bestMapColor = MapColor.BLACK;
        MaterialColor bestMapColor = MaterialColor.COLOR_BLACK;
        int currentDifference = Integer.MAX_VALUE;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

//        for (MapColor mapColor : MapColor.COLORS)
        for (MaterialColor mapColor : MaterialColor.MATERIAL_COLORS) {
//            if (mapColor == null || mapColor.colorValue == 0)
            if (mapColor == null || mapColor.col == 0) {
                continue;
            }
//            int mr = (mapColor.colorValue >> 16) & 0xFF;
            int mr = (mapColor.col >> 16) & 0xFF;
//            int mg = (mapColor.colorValue >> 8) & 0xFF;
            int mg = (mapColor.col >> 8) & 0xFF;
//            int mb = mapColor.colorValue & 0xFF;
            int mb = mapColor.col & 0xFF;

            int dr = mr - r;
            int dg = mg - g;
            int db = mb - b;

            int difference = dr * dr + dg * dg + db * db;

            if (difference < currentDifference) {
                currentDifference = difference;
                bestMapColor = mapColor;
            }
        }
        return bestMapColor;
    }
}
