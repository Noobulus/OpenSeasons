package mod.noobulus.openseasons.seasons;

import java.util.HashMap;
import java.util.Map;

public class SeasonMap {
    private static final Map<String, Season> SEASON_MAP = new HashMap<>();
    private static final Season InvalidSeason = new Season(2, 0f, 0f, "INVALID", new float[]{1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F});

    public static Season getByName(String name) { // this is probably not how i should do this
        return SEASON_MAP.getOrDefault(name, InvalidSeason);
    }

    public static void addSeason(String name, Season season) {
        SEASON_MAP.put(name, season);
    }

    public static void newSeason(int days, float tempMod, float humidMod, String name, float[] weatherMods) {
        Season season = new Season(days, tempMod, humidMod, name, weatherMods);
        addSeason(name, season);
    }
}
