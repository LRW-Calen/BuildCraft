package buildcraft.lib.client.guide.parts.contents;

import buildcraft.api.statements.IStatement;
import buildcraft.lib.client.guide.PageLine;
import buildcraft.lib.client.guide.entry.PageEntryStatement;
import buildcraft.lib.client.guide.entry.PageValue;
import buildcraft.lib.client.guide.parts.GuidePage;
import buildcraft.lib.client.guide.parts.GuidePageFactory;
import buildcraft.lib.gui.ISimpleDrawable;
import buildcraft.lib.gui.statement.GuiElementStatementSource;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PageLinkStatement extends PageLink {

    public final IStatement statement;
    public final List<ITextComponent> tooltip;
    // public final String searchText;
    public final ITextComponent searchText;
    public final String textKey;

    public PageLinkStatement(boolean startVisible, IStatement statement) {
        super(createPageLine(statement), startVisible);
        this.statement = statement;
        List<ITextComponent> tip = statement.getTooltip();
        if (tip.isEmpty()) {
            String uniqueTag = statement.getUniqueTag();
            this.tooltip = ImmutableList.of(new StringTextComponent(uniqueTag));
//            this.searchText = uniqueTag.toLowerCase(Locale.ROOT);
            this.searchText = new StringTextComponent(uniqueTag);
            this.textKey = statement.getDescriptionKey().toLowerCase(Locale.ROOT);
        } else {
            this.tooltip = tip;
//            String joinedTooltip = joinedTooltip_StrList.stream().collect(Collectors.joining(" ", "", ""));
            IFormattableTextComponent joinedTooltip = new StringTextComponent("");
            for (int i = 0; i < tip.size(); i++) {
                joinedTooltip = joinedTooltip.append(tip.get(i));
                if (i < tip.size() - 1) {
                    joinedTooltip.append(new StringTextComponent(" "));
                }
            }
//            this.searchText = TextFormatting.getTextWithoutFormattingCodes(joinedTooltip).toLowerCase(Locale.ROOT);
            this.searchText = joinedTooltip;
            this.textKey = TextFormatting.stripFormatting(statement.getTooltipKey().stream().collect(Collectors.joining("_", "", "")).toLowerCase(Locale.ROOT));
        }
    }

    private static PageLine createPageLine(IStatement statement) {
        ISimpleDrawable icon = (p, x, y) -> GuiElementStatementSource.drawGuiSlot(statement, p, x, y);

        List<ITextComponent> tooltip = statement.getTooltip();
        List<String> tooltipKeys = statement.getTooltipKey();
        ITextComponent title = tooltip.isEmpty() ? new StringTextComponent(statement.getUniqueTag()) : tooltip.get(0);
        String titleKey = tooltipKeys.isEmpty() ? statement.getDescriptionKey() : tooltipKeys.get(0);
//        return new PageLine(icon, icon, 2, title, true);
        return new PageLine(icon, icon, 2, titleKey, title, true);
    }

    @Override
//    public String getSearchName()
    public ITextComponent getSearchName() {
        return searchText;
    }

    @Override
    public String getKey() {
        return textKey;
    }

    @Override
    public List<ITextComponent> getTooltip() {
        return tooltip.size() == 1 ? null : tooltip;
    }

    @Override
    public GuidePageFactory getFactoryLink() {
        // TODO: Populate this with useful information!
        return g -> new GuidePage(g, ImmutableList.of(), new PageValue<>(PageEntryStatement.INSTANCE, statement));
    }
}
