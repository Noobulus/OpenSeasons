package mod.noobulus.openseasons.init;

import mod.noobulus.openseasons.seasons.Season;

public class DefaultSeasons {
    public static void initSeasons() {}

    public static final Season SPRING = new Season(2, 0f, 1f, "Spring");
    public static final Season SUMMER = new Season(2, 1f, 0.5f, "Summer");
    public static final Season FALL = new Season(2, 0f, 0f, "Fall");
    public static final Season WINTER = new Season(2, -1f, -0.5f, "Winter");
    public static final Season INVALID = new Season(2, 0f, 0f, "INVALID");

    public static Season getByName(String name) { // this is probably not how i should do this
        return switch (name) {
            case "Spring" -> SPRING;
            case "Summer" -> SUMMER;
            case "Fall" -> FALL;
            case "Winter" -> WINTER;
            default -> INVALID;
        };
    }
}
