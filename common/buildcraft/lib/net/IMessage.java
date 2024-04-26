package buildcraft.lib.net;

import buildcraft.api.core.BCLog;
import net.minecraft.network.FriendlyByteBuf;

public interface IMessage {
    void fromBytes(FriendlyByteBuf buf);

    void toBytes(FriendlyByteBuf buf);

    public static IMessage staticFromBytes(Class<? extends IMessage> clazz, FriendlyByteBuf buf) {
        try {
            IMessage message = clazz.newInstance();
            message.fromBytes(buf);
            return message;
        } catch (Exception e) {
            BCLog.logger.error(e);
            return null;
        }
    }
}
