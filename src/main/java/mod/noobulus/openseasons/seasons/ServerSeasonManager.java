package mod.noobulus.openseasons.seasons;

import mod.noobulus.openseasons.init.DefaultSeasons;
import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import mod.noobulus.openseasons.util.NetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ServerSeasonManager {
    private static Season currentSeason = SeasonMap.getByName("INVALID"); // dumb fallback
    private static int currentTickCount = 0;

    public static Season getSeasonFromLevel(Level level) {
        long worldTime = level.getDayTime();
        int day = (int) (worldTime / 24000L % 2147483647L);
        return seasonFromInt(day % 8);
    }

    public static Season seasonFromInt(int in) {
        return switch (in) {
            case 2, 3 -> SeasonMap.getByName("Summer");
            case 4, 5 -> SeasonMap.getByName("Fall");
            case 6, 7 -> SeasonMap.getByName("Winter");
            default -> SeasonMap.getByName("Spring");
        };
    }

    @SubscribeEvent
    public static void updateSeason(TickEvent.WorldTickEvent e) { // TODO: one of these days figure out why this has random leftovers specifically when snow golems are in view
        Level level = e.world;
        if (!level.isClientSide()) {
            int refreshCooldown = 100; // every 5 sec
            if (currentTickCount % refreshCooldown == 0) {
                Season levelSeason = getSeasonFromLevel(e.world);
                if (levelSeason != currentSeason) {
                    ModifiedTempAndHumid.refreshCaches(); // clear caches because minecraft is a good video game
                    if (level.getServer() == null) {
                        return;
                    }
                    List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player : playerList) {
                        NetworkHelper.syncSeason(levelSeason.getName(), player);
                    }
                }
                currentSeason = levelSeason;
            }
            currentTickCount++;
        }
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }
}
