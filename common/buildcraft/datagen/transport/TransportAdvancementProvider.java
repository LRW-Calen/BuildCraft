package buildcraft.datagen.transport;

import buildcraft.datagen.base.BCBaseAdvancementProvider;
import buildcraft.datagen.core.CoreAdvancementProvider;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.silicon.BCSiliconItems;
import buildcraft.silicon.gate.EnumGateLogic;
import buildcraft.silicon.gate.EnumGateMaterial;
import buildcraft.silicon.gate.EnumGateModifier;
import buildcraft.silicon.gate.GateVariant;
import buildcraft.transport.BCTransport;
import buildcraft.transport.BCTransportItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

// Calen Completed
public class TransportAdvancementProvider extends BCBaseAdvancementProvider {
    private static final String NAMESPACE = BCTransport.MODID;
    private static Advancement ROOT;

    private static final ImpossibleTrigger.TriggerInstance IMPOSSIBLE = new ImpossibleTrigger.TriggerInstance();

    public static void registerTransportAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        ROOT = CoreAdvancementProvider.ROOT;
        // pipe_dream
        Advancement pipe_dream = Advancement.Builder.advancement().display(
                        (Item) BCTransportItems.pipeStructure.get(null).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_dream.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_dream.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(CoreAdvancementProvider.ROOT)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":pipe_dream");
        // plugging_the_gap
        Advancement plugging_the_gap = Advancement.Builder.advancement().display(
                        BCTransportItems.plugBlocker.get(),
                        new TranslatableComponent("advancements.buildcrafttransport.plugging_the_gap.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.plugging_the_gap.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(pipe_dream)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":plugging_the_gap");
        // pipe_logic
        Advancement pipe_logic = Advancement.Builder.advancement().display(
                        BCSiliconItems.variantGateMap.get(new GateVariant(new CompoundTag())).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_logic.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_logic.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(plugging_the_gap)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":pipe_logic");
        // all_plugged_up
        Advancement all_plugged_up = Advancement.Builder.advancement().display(
                        BCSiliconItems.plugLightSensor.get(),
                        new TranslatableComponent("advancements.buildcrafttransport.all_plugged_up.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.all_plugged_up.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .parent(pipe_logic)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":all_plugged_up");
        // categorizing_with_colors
        Advancement categorizing_with_colors = Advancement.Builder.advancement().display(
                        (Item) BCTransportItems.pipeItemEmzuli.get(null).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.categorizing_with_colors.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.categorizing_with_colors.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .parent(plugging_the_gap)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":categorizing_with_colors");
        // logic_transportation
        ItemStack wireStack_logic_transportation = new ItemStack(BCTransportItems.wire.get());
        ColourUtil.addColorTagToStack(wireStack_logic_transportation, DyeColor.byId(0));
        Advancement logic_transportation = Advancement.Builder.advancement().display(
                        wireStack_logic_transportation,
                        new TranslatableComponent("advancements.buildcrafttransport.logic_transportation.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.logic_transportation.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(pipe_dream)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":logic_transportation");
        // colorful_electrician
        ItemStack wireStack_colorful_electrician = new ItemStack(BCTransportItems.wire.get());
        ColourUtil.addColorTagToStack(wireStack_colorful_electrician, DyeColor.byId(5));
        Advancement colorful_electrician = Advancement.Builder.advancement().display(
                        wireStack_colorful_electrician,
                        new TranslatableComponent("advancements.buildcrafttransport.colorful_electrician.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.colorful_electrician.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .parent(logic_transportation)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":colorful_electrician");
        // extended_logic
        Advancement extended_logic = Advancement.Builder.advancement().display(
                        BCSiliconItems.variantGateMap.get(new GateVariant(EnumGateLogic.OR, EnumGateMaterial.GOLD, EnumGateModifier.DIAMOND)).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.extended_logic.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.extended_logic.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(pipe_logic)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":extended_logic");
        // pipe_diversification
        Advancement pipe_diversification = Advancement.Builder.advancement().display(
                        (Item) BCTransportItems.pipeItemDiaWood.get(null).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_diversification.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_diversification.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(pipe_dream)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":pipe_diversification");
        // pipe_fanatic
        Advancement pipe_fanatic = Advancement.Builder.advancement().display(
                        (Item) BCTransportItems.pipeItemDiamond.get(null).get(),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_fanatic.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.pipe_fanatic.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .parent(pipe_diversification)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":pipe_fanatic");
        // sealing_fluids
        Advancement sealing_fluids = Advancement.Builder.advancement().display(
                        BCTransportItems.waterproof.get(),
                        new TranslatableComponent("advancements.buildcrafttransport.sealing_fluids.title"),
                        new TranslatableComponent("advancements.buildcrafttransport.sealing_fluids.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(pipe_diversification)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":sealing_fluids");
    }
}
