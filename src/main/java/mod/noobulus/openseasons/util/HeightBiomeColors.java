package mod.noobulus.openseasons.util;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;

public class HeightBiomeColors extends BiomeColors {
    public static final HeightColorResolver GRASS_COLOR_RESOLVER = BiomeColorUtils::getGrassColorFromPos;
    public static final HeightColorResolver FOLIAGE_COLOR_RESOLVER = BiomeColorUtils::getFoliageColorFromPos;
    public static final HeightColorResolver WATER_COLOR_RESOLVER = (Biome biome, BlockPos pos) -> {
        return biome.getWaterColor();
    };
}
