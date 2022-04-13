package mod.noobulus.openseasons.seasons;

import mod.noobulus.openseasons.init.DefaultSeasons;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SeasonManager {
    private static Season currentSeason = DefaultSeasons.SPRING; // dumb fallback
    private static Season lastSeason = DefaultSeasons.SPRING;
    private static int currentTickCount = 0;

    public static Season getSeasonFromLevel(Level level) {
        long worldTime = level.getDayTime();
        int day = (int) (worldTime / 24000L % 2147483647L);
        return seasonFromInt(day % 8);
    }

    public static Season seasonFromInt(int in) {
        return switch (in) {
            case 2, 3 -> DefaultSeasons.SUMMER;
            case 4, 5 -> DefaultSeasons.FALL;
            case 6, 7 -> DefaultSeasons.WINTER;
            default -> DefaultSeasons.SPRING;
        };
    }

    @SubscribeEvent
    public static void updateSeason(TickEvent.WorldTickEvent e) {
        if (!e.world.isClientSide()) {
            int refreshCooldown = 100; // every 5 sec
            if (currentTickCount % refreshCooldown == 0) {
                Season levelSeason = getSeasonFromLevel(e.world);
                if (levelSeason != currentSeason) {
                    lastSeason = currentSeason;
                }
                currentSeason = levelSeason;
            }
            currentTickCount++;
        }
    }

    @SubscribeEvent
    public static void updateClientSeason(TickEvent.ClientTickEvent e) {
        if (currentSeason != lastSeason) {
            Minecraft.getInstance().levelRenderer.allChanged();
            lastSeason = currentSeason;
        }
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }

    public static void setCurrentSeason(Season season) {
        currentSeason = season;
    }
}
