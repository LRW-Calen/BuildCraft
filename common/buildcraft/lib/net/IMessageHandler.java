package buildcraft.lib.net;

import net.minecraftforge.network.NetworkEvent;

public interface IMessageHandler<REQ extends IMessage, REPLY extends IMessage> {
    REPLY onMessage(REQ message, NetworkEvent.Context ctx);
}
