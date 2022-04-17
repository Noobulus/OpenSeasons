package mod.noobulus.openseasons.util;

import mod.noobulus.openseasons.network.MsgSeasonChangeAck;
import mod.noobulus.openseasons.network.OpenSeasonsMessages;
import mod.noobulus.openseasons.seasons.ServerSeasonManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class NetworkHelper {
    @SubscribeEvent
    public static void syncOnLogin(PlayerEvent.PlayerLoggedInEvent e) {
        if (!(e.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        syncSeason(ServerSeasonManager.getCurrentSeason().getName(), player);
    }

    @SubscribeEvent
    public static void syncOnRejoin(PlayerEvent.PlayerRespawnEvent e) {
        if (!(e.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        syncSeason(ServerSeasonManager.getCurrentSeason().getName(), player);
    }

    public static void syncSeason(String seasonName, ServerPlayer player) {
        OpenSeasonsMessages.getNetwork()
                .send(PacketDistributor.PLAYER.with(() -> player), new MsgSeasonChangeAck(seasonName));
    }
}
