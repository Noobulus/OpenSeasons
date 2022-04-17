package mod.noobulus.openseasons.network;

import io.netty.buffer.ByteBuf;
import mod.noobulus.openseasons.init.DefaultSeasons;
import mod.noobulus.openseasons.seasons.ClientSeasonManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// sent from server to client whenever a season changes
public record MsgSeasonChangeAck(String name) {
    public static MsgSeasonChangeAck deserialize(ByteBuf bufferIn) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(bufferIn);
        String seasonName = buffer.readUtf();
        return new MsgSeasonChangeAck(seasonName);
    }

    public void serialize(ByteBuf bufferIn) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(bufferIn);
        buffer.writeUtf(this.name);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                ClientSeasonManager.updateClientSeason(DefaultSeasons.getByName(name)));
    }
}
