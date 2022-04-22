package mod.noobulus.openseasons.init;

import mod.noobulus.openseasons.seasons.SeasonMap;

public class DefaultSeasons {
    public static void initSeasons() { // rainTime, rainDelay, thunderTime, thunderDelay
        SeasonMap.newSeason(2, 0f, 0f, "Spring", new float[]{1F, 1F, 0.1F, 0.1F, 0.1F, 0.1F, 1F, 1F});
        SeasonMap.newSeason(2, 0.5f, 0.25f, "Summer", new float[]{0.1F, 0.1F, 0.1F, 0.1F, 1F, 1F, 0.1F, 0.1F});
        SeasonMap.newSeason(2, -0.25f, 0f, "Fall", new float[]{1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F});
        SeasonMap.newSeason(2, -0.5f, -0.25f, "Winter", new float[]{1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F});
    }
}
