package mod.noobulus.openseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Biome.class)
public interface AccessorBiome {
    @Invoker
    float callGetTemperature(BlockPos pos);

    @Accessor
    Biome.ClimateSettings getClimateSettings();

    @Accessor
    PerlinSimplexNoise getTEMPERATURE_NOISE();

    @Accessor
    Biome.BiomeCategory getBiomeCategory();
}
