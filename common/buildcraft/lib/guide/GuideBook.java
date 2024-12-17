package buildcraft.lib.guide;

import buildcraft.api.registry.IScriptableRegistry.ISimpleEntryDeserializer;
import buildcraft.lib.misc.JsonUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public final class GuideBook {

    public static final ISimpleEntryDeserializer<GuideBook> DESERIALISER = GuideBook::deserialize;

    public final ResourceLocation name;
    public final ResourceLocation itemIcon;
    public final MutableComponent title;
    public final boolean appendAllEntries;
    // TODO: Mod/resource pack display options!
    public final GuideContentsData data = new GuideContentsData(this);

    private static GuideBook deserialize(ResourceLocation name, JsonObject json, JsonDeserializationContext ctx) {
        ResourceLocation itemIcon = new ResourceLocation("buildcraftcore:guide_main");
        MutableComponent title = JsonUtil.getTextComponent(json, "title", "");
//        boolean addAll = JsonUtils.getBoolean(json, "all_entries", true);
        boolean addAll = GsonHelper.getAsBoolean(json, "all_entries", true);
        return new GuideBook(name, itemIcon, title, addAll);
    }

    public GuideBook(ResourceLocation name, ResourceLocation itemIcon, MutableComponent title, boolean appendAllEntries) {
        this.name = name;
        this.itemIcon = itemIcon;
        this.title = title;
        this.appendAllEntries = appendAllEntries;
    }

    @Override
    public String toString() {
        return "GuideBook [ " + name + ", title = " + title + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return name.equals(((GuideBook) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
