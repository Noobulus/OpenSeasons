package mod.noobulus.openseasons.mixin;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Biome.BiomeBuilder.class)
public class BiomeBuilderMixin {
    @Redirect(method = "Lnet/minecraft/world/level/biome/Biome$BiomeBuilder;from(Lnet/minecraft/world/level/biome/Biome;)Lnet/minecraft/world/level/biome/Biome$BiomeBuilder;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitation()Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private static Biome.Precipitation redirectGetPrecipitation(Biome biome) {
        return ((AccessorBiome) (Object) biome).getClimateSettings().precipitation;
    }
}
