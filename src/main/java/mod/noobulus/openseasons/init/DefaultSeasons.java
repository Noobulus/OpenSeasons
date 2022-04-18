package mod.noobulus.openseasons.init;

import mod.noobulus.openseasons.seasons.SeasonMap;

public class DefaultSeasons {
    public static void initSeasons() {
        SeasonMap.newSeason(2, 0f, 0f, "Spring");
        SeasonMap.newSeason(2, 0.5f, 0.25f, "Summer");
        SeasonMap.newSeason(2, -0.25f, 0f, "Fall");
        SeasonMap.newSeason(2, -0.5f, -0.25f, "Winter");
    }
}
