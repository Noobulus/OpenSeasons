package mod.noobulus.openseasons.util;

import mod.noobulus.openseasons.mixin.AccessorBiome;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;

public class BiomeColorUtils {
    public static int getGrassColorFromPos(Biome biome, BlockPos pos) {
        int i = biome.getSpecialEffects().getGrassColorOverride().orElseGet(() -> getGrassColorFromTextureAndPos(biome, pos));
        return biome.getSpecialEffects().getGrassColorModifier().modifyColor(pos.getX(), pos.getZ(), i);
    }

    private static int getGrassColorFromTextureAndPos(Biome biome, BlockPos pos) {
        double d0 = Mth.clamp(((AccessorBiome) (Object) biome).callGetTemperature(pos), 0.0F, 1.0F);
        double d1 = Mth.clamp(biome.getDownfall(), 0.0F, 1.0F);
        return GrassColor.get(d0, d1);
    }

    public static int getFoliageColorFromPos(Biome biome, BlockPos pos) {
        return biome.getSpecialEffects().getFoliageColorOverride().orElseGet(() -> getFoliageColorFromTextureAndPos(biome, pos));
    }

    private static int getFoliageColorFromTextureAndPos(Biome biome, BlockPos pos) {
        double d0 = Mth.clamp(((AccessorBiome) (Object) biome).callGetTemperature(pos), 0.0F, 1.0F);
        double d1 = Mth.clamp(biome.getDownfall(), 0.0F, 1.0F);
        return FoliageColor.get(d0, d1);
    }
}

//TODO: make biome colors scale with height/rainfall
//TODO: add the actual hooks to change temp/humidity with seasons lol
