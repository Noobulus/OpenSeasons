package mod.noobulus.openseasons.init;

import mod.noobulus.openseasons.BuildConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class OSTags {
    public static void initTags() {}

    public static final TagKey<Biome> IS_SEASONS_DENIED = TagKey.create(Registry.BIOME_REGISTRY,
            new ResourceLocation(BuildConfig.MODID, "is_seasons_denied"));
    public static final TagKey<Biome> IS_SEASONS_ALLOWED = TagKey.create(Registry.BIOME_REGISTRY,
            new ResourceLocation(BuildConfig.MODID, "is_seasons_allowed"));
    public static final TagKey<Biome> IS_OVERWORLD_SURFACE = TagKey.create(Registry.BIOME_REGISTRY,
            new ResourceLocation(BuildConfig.MODID, "is_overworld_surface"));
    public static final TagKey<Biome> IS_CAVE = TagKey.create(Registry.BIOME_REGISTRY,
            new ResourceLocation(BuildConfig.MODID, "is_cave"));
}
