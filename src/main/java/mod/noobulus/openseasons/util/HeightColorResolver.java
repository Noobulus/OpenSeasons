package mod.noobulus.openseasons.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;

public interface HeightColorResolver extends ColorResolver {
    @Override
    default int getColor(Biome biome, double x,  double z) {
        return getColor(biome, x, z);
    }

    int getColor(Biome biome, BlockPos pos);
}
