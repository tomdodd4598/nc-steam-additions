package igentuman.ncsteamadditions.network;

import io.netty.buffer.ByteBuf;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITileGui;
import nc.tile.internal.fluid.Tank;
import nclegacy.tile.ITileGuiLegacy;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class NCSProcessorUpdatePacket extends TileUpdatePacket {
    public boolean isProcessing;
    public double time;
    public int energyStored;
    public double baseProcessTime;
    public double baseProcessPower;
    public List<Tank.TankInfo> tanksInfo;
    public float currentReactivity;
    public float targetReactivity;
    public int adjustmentAttempts;

    public NCSProcessorUpdatePacket() {
    }

    public NCSProcessorUpdatePacket(BlockPos pos, boolean isProcessing, double time, int energyStored, double baseProcessTime, double baseProcessPower, List<Tank> tanks, float currentReactivity, float targetReactivity, int adjustmentAttempts) {
        this.pos = pos;
        this.isProcessing = isProcessing;
        this.time = time;
        this.energyStored = energyStored;
        this.baseProcessTime = baseProcessTime;
        this.baseProcessPower = baseProcessPower;
        this.tanksInfo = Tank.TankInfo.getInfoList(tanks);
        this.currentReactivity = currentReactivity;
        this.targetReactivity = targetReactivity;
        this.adjustmentAttempts = adjustmentAttempts;

    }

    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.isProcessing = buf.readBoolean();
        this.time = buf.readDouble();
        this.energyStored = buf.readInt();
        this.baseProcessTime = buf.readDouble();
        this.baseProcessPower = buf.readDouble();
        this.tanksInfo = readTankInfos(buf);
        this.currentReactivity = buf.readFloat();
        this.targetReactivity = buf.readFloat();
        this.adjustmentAttempts = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeBoolean(this.isProcessing);
        buf.writeDouble(this.time);
        buf.writeInt(this.energyStored);
        buf.writeDouble(this.baseProcessTime);
        buf.writeDouble(this.baseProcessPower);
        writeTankInfos(buf, this.tanksInfo);
        buf.writeFloat(this.currentReactivity);
        buf.writeFloat(this.targetReactivity);
        buf.writeInt(this.adjustmentAttempts);
    }

    public static class Handler extends nc.network.tile.TileUpdatePacket.Handler<NCSProcessorUpdatePacket, ITileGuiLegacy<NCSProcessorUpdatePacket>> {
        public Handler() {
        }

        protected void onTileUpdatePacket(NCSProcessorUpdatePacket message, ITileGuiLegacy processor) {
            processor.onTileUpdatePacket(message);
        }
    }
}
