package mod.noobulus.openseasons.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;

public class BiomeColorUtils {
    public static int getGrassColorFromPos(Holder<Biome> biome, BlockPos pos) {
        int i = biome.value().getSpecialEffects().getGrassColorOverride().orElseGet(() -> getGrassColorFromTextureAndPos(biome, pos));
        return biome.value().getSpecialEffects().getGrassColorModifier().modifyColor(pos.getX(), pos.getZ(), i);
    }

    private static int getGrassColorFromTextureAndPos(Holder<Biome> biome, BlockPos pos) {
        double d0 = Mth.clamp(ModifiedTempAndHumid.getModifiedTemperature(biome, pos), 0.0F, 1.0F);
        double d1 = Mth.clamp(ModifiedTempAndHumid.getModifiedHumidity(biome, pos), 0.0F, 1.0F);
        return GrassColor.get(d0, d1);
    }

    public static int getFoliageColorFromPos(Holder<Biome> biome, BlockPos pos) {
        return biome.value().getSpecialEffects().getFoliageColorOverride().orElseGet(() -> getFoliageColorFromTextureAndPos(biome, pos));
    }

    private static int getFoliageColorFromTextureAndPos(Holder<Biome> biome, BlockPos pos) {
        double d0 = Mth.clamp(ModifiedTempAndHumid.getModifiedTemperature(biome, pos), 0.0F, 1.0F);
        double d1 = Mth.clamp(ModifiedTempAndHumid.getModifiedHumidity(biome, pos), 0.0F, 1.0F);
        return FoliageColor.get(d0, d1);
    }
}

//DONE: make biome colors scale with height/rainfall
//TODO: make temperature scale downwards for caves
//TODO: make humidity scale the same way as temp
//TODO: add the actual hooks to change temp/humidity with seasons lol
