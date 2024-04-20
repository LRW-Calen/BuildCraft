package buildcraft.silicon;

import buildcraft.api.enums.EnumRedstoneChipset;
import buildcraft.api.facades.FacadeAPI;
import buildcraft.lib.item.ItemPluggableSimple;
import buildcraft.lib.registry.RegistrationHelper;
import buildcraft.lib.registry.TagManager;
import buildcraft.silicon.gate.EnumGateLogic;
import buildcraft.silicon.gate.EnumGateMaterial;
import buildcraft.silicon.gate.EnumGateModifier;
import buildcraft.silicon.gate.GateVariant;
import buildcraft.silicon.item.*;
import buildcraft.silicon.plug.PluggablePulsar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class BCSiliconItems {
    public static Item.Properties BC_SILICON_ITEM_DEFAULT_PROP =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .rarity(Rarity.COMMON) // 稀有度 影响tooltip的颜色
//                    .durability(99) // 耐久
//                    .food(E115) // 可以作为食物 E115是个FoodProperties 见TF:TFItems.java
//                    .fireResistant() // 抗燃
                    .stacksTo(64) // 堆叠
            ;
    public static Item.Properties BC_SILICON_PLUG_PROP =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_PLUGS_TAB)
                    .rarity(Rarity.COMMON) // 稀有度 影响tooltip的颜色
                    .stacksTo(64);
    public static Item.Properties BC_SILICON_ITEM_PROP_NO_STACK =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .rarity(Rarity.COMMON) // 稀有度 影响tooltip的颜色
//                    .durability(99) // 耐久
//                    .food(E115) // 可以作为食物 E115是个FoodProperties 见TF:TFItems.java
//                    .fireResistant() // 抗燃
                    .stacksTo(1) // 堆叠
            ;
    public static Item.Properties BC_SILICON_FACADE =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_FACADES_TAB)
                    .rarity(Rarity.COMMON) // 稀有度 影响tooltip的颜色
//                    .durability(99) // 耐久
//                    .food(E115) // 可以作为食物 E115是个FoodProperties 见TF:TFItems.java
//                    .fireResistant() // 抗燃
                    .durability(0) // maxDamage 里面设置了stackSize=1 这一步要放在stacksTo前面
                    .stacksTo(64) // 堆叠
            ;
    public static Item.Properties BC_SILICON_LENS =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_PLUGS_TAB)
                    .rarity(Rarity.COMMON) // 稀有度 影响tooltip的颜色
                    .durability(0) // maxDamage 里面设置了stackSize=1 这一步要放在stacksTo前面
                    .stacksTo(64) // 堆叠
            ;

    private static final RegistrationHelper HELPER = new RegistrationHelper(BCSilicon.MODID);

    public static RegistryObject<Item> chipsetRedstone;
    public static RegistryObject<Item> chipsetIron;
    public static RegistryObject<Item> chipsetGold;
    public static RegistryObject<Item> chipsetQuartz;
    public static RegistryObject<Item> chipsetDiamond;

    public static RegistryObject<ItemGateCopier> gateCopier;
    //    public static RegistryObject<ItemPluggableGate> plugGate;
    public static final Map<GateVariant, RegistryObject<ItemPluggableGate>> variantGateMap = new HashMap<>();
    public static RegistryObject<ItemPluggableLens> plugLens;
    public static RegistryObject<Item> plugPulsar;
    public static RegistryObject<Item> plugLightSensor;
    public static RegistryObject<ItemPluggableFacade> plugFacade;


    public static void preInit() {
//        redstoneChipset = HELPER.addItem(new ItemRedstoneChipset("item.redstone_chipset"));
        chipsetRedstone = HELPER.addItem("item.chipset.redstone", BC_SILICON_ITEM_DEFAULT_PROP, (idBC, properties) -> new ItemRedstoneChipset(idBC, properties, EnumRedstoneChipset.RED));
        chipsetIron = HELPER.addItem("item.chipset.iron", BC_SILICON_ITEM_DEFAULT_PROP, (idBC, properties) -> new ItemRedstoneChipset(idBC, properties, EnumRedstoneChipset.IRON));
        chipsetGold = HELPER.addItem("item.chipset.gold", BC_SILICON_ITEM_DEFAULT_PROP, (idBC, properties) -> new ItemRedstoneChipset(idBC, properties, EnumRedstoneChipset.GOLD));
        chipsetQuartz = HELPER.addItem("item.chipset.quartz", BC_SILICON_ITEM_DEFAULT_PROP, (idBC, properties) -> new ItemRedstoneChipset(idBC, properties, EnumRedstoneChipset.QUARTZ));
        chipsetDiamond = HELPER.addItem("item.chipset.diamond", BC_SILICON_ITEM_DEFAULT_PROP, (idBC, properties) -> new ItemRedstoneChipset(idBC, properties, EnumRedstoneChipset.DIAMOND));

        gateCopier = HELPER.addItem("item.gate_copier", BC_SILICON_ITEM_PROP_NO_STACK, ItemGateCopier::new);

//        plugGate = HELPER.addItem("item.plug.gate", BC_SILICON_PLUG_PROP, ItemPluggableGate::new);

        GateVariant gateVariant = new GateVariant(new CompoundTag());
        String registryId = TagManager.getTag("item.plug.gate", TagManager.EnumTagType.REGISTRY_NAME).replace(BCSilicon.MODID + ":", "");
        RegistryObject<ItemPluggableGate> plug = HELPER.addItem("item.plug.gate", registryId, BC_SILICON_PLUG_PROP, (idBC, prop) -> new ItemPluggableGate(idBC, prop, gateVariant));
        variantGateMap.put(gateVariant, plug);
        for (EnumGateMaterial material : EnumGateMaterial.VALUES) {
            if (!material.canBeModified) {
                continue;
            }
            for (EnumGateLogic logic : EnumGateLogic.VALUES) {
                for (EnumGateModifier modifier : EnumGateModifier.VALUES) {
                    GateVariant gateVariant_i = new GateVariant(logic, material, modifier);
                    String registryId_i = TagManager.getTag("item.plug.gate", TagManager.EnumTagType.REGISTRY_NAME).replace(BCSilicon.MODID + ":", "") + "_" + gateVariant_i.getVariantName();
                    RegistryObject<ItemPluggableGate> plug_i = HELPER.addItem("item.plug.gate", registryId_i, BC_SILICON_PLUG_PROP, (idBC, prop) -> new ItemPluggableGate(idBC, prop, gateVariant_i));
                    variantGateMap.put(gateVariant_i, plug_i);
                }
            }
        }

        plugLens = HELPER.addItem("item.plug.lens", BC_SILICON_LENS, ItemPluggableLens::new);
        plugPulsar = HELPER.addItem("item.plug.pulsar", BC_SILICON_PLUG_PROP, (id, p) -> new ItemPluggableSimple(id, p, BCSiliconPlugs.pulsar,
                PluggablePulsar::new, ItemPluggableSimple.PIPE_BEHAVIOUR_ACCEPTS_RS_POWER));
        plugLightSensor = HELPER.addItem("item.plug.light_sensor", BC_SILICON_PLUG_PROP, (id, p) -> new ItemPluggableSimple(id, p, BCSiliconPlugs.lightSensor));
        plugFacade = HELPER.addItem("item.plug.facade", BC_SILICON_FACADE, ItemPluggableFacade::new);
        FacadeAPI.facadeItem = plugFacade;
    }
}
