//package buildcraft.lib.client.resource;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import net.minecraft.client.resources.data.IMetadataSection;
//import net.minecraft.client.resources.data.IMetadataSectionSerializer;
//import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
//import net.minecraft.util.JsonUtils;
//import net.minecraft1_12_2.util.JsonUtils;
//
//import java.lang.reflect.Type;
//
///** Generic metadata section, containing any types of data. */
//public class DataMetadataSection implements MetadataSection {
//    public static final String SECTION_NAME = "buildcraft_data";
//
//    public final JsonObject data;
//
//    public DataMetadataSection(JsonObject data) {
//        this.data = data;
//    }
//
//    public static final MetadataSectionSerializer<DataMetadataSection> DESERIALISER =
//        new MetadataSectionSerializer<DataMetadataSection>() {
//            @Override
//            public DataMetadataSection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
//                return new DataMetadataSection(JsonUtils.getJsonObject(json, SECTION_NAME));
//            }
//
//            @Override
//            public String getSectionName() {
//                return SECTION_NAME;
//            }
//        };
//}
