package igentuman.ncsteamadditions.network;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NCSAPacketHandler {
    public static SimpleNetworkWrapper instance = null;

    public NCSAPacketHandler() {}

    public static void registerMessages(String channelName) {
        instance = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        // SERVER
        instance.registerMessage(OpenSideGuiPacket.Handler.class, OpenSideGuiPacket.class, nextID(), Side.SERVER);
    }

    private static int packetId = 0;

    public static int nextID() {
        return packetId++;
    }

}