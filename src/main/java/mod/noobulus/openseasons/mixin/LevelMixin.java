package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    // TODO: make rain happen in places where it's humid and cool enough to

    @Inject(method = "Lnet/minecraft/world/level/Level;isRainingAt(Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "RETURN", ordinal = 3), cancellable = true)
    private void injectModifiedRain(BlockPos pPosition, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ModifiedTempAndHumid.shouldRainHere(this.getBiomeManager().getBiome(pPosition), pPosition));
    }

    @Shadow
    public abstract BiomeManager getBiomeManager();
}
