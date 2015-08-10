package HxCKDMS.HxCSkills.network;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCSkills.Handlers.BloodDestruction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.Charset;
import java.util.UUID;

public class BloodDestructionPacket implements IMessage {
    public String puid;

    public BloodDestructionPacket() {
    }

    public BloodDestructionPacket(String uid) {
        puid = uid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        byte[] b = buf.getBytes(0, buf).array();
        puid = new String(b);
        puid = puid.substring(1, 9) + "-" + puid.substring(9, 13) + "-" + puid.substring(13, 17) + "-" + puid.substring(17, 21) + "-" + puid.substring(20);
        EntityPlayer player = HxCCore.server.getEntityWorld().getPlayerEntityByUUID(UUID.fromString(puid));
        BloodDestruction.doBloodDestruction(player);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] b = puid.replaceAll("-", "").getBytes(Charset.forName("UTF-8"));
        buf.writeBytes(b);
    }

    public static class Handler implements IMessageHandler<BloodDestructionPacket, IMessage> {
        @Override
        public IMessage onMessage(BloodDestructionPacket message,	MessageContext ctx) {
            return null;
        }
    }

}