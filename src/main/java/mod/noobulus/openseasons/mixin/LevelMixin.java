package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ClimateChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Redirect(method = "Lnet/minecraft/world/level/Level;isRainingAt(Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectWarmEnoughToRain(Biome biome, BlockPos pos) {
        return ClimateChecks.canRain(this.getBiomeManager().getBiome(pos), pos);
    }

    @Shadow
    public abstract BiomeManager getBiomeManager();
}

