package mod.noobulus.openseasons.init;

import mod.noobulus.openseasons.seasons.Season;

public class DefaultSeasons {
    public static void initSeasons() {}

    public static final Season SPRING = new Season(2, 0f, 1f, "Spring");
    public static final Season SUMMER = new Season(2, 1f, 0.5f, "Summer");
    public static final Season FALL = new Season(2, 0f, 0f, "Fall");
    public static final Season WINTER = new Season(2, -1f, -0.5f, "Winter");
}
