package mod.noobulus.openseasons.seasons;

import mod.noobulus.openseasons.init.DefaultSeasons;
import net.minecraft.client.Minecraft;

public class ClientSeasonManager {
    private static Season currentSeason = DefaultSeasons.INVALID; // dumb fallback

    public static void updateClientSeason(Season season) { // TODO: how bad actually is this?
        if (currentSeason != season) {
            currentSeason = season;
            Minecraft.getInstance().levelRenderer.allChanged();
        }
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }
}
