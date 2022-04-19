package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow @Nullable private ClientLevel level;

    @Redirect(method = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectWarmEnoughToRainInRender(Biome biome, BlockPos pos) {
        if (level == null) { // prevent NPE shenanigans
            return false;
        }
        return ModifiedTempAndHumid.canRain(level.getBiome(pos), pos);
    }

    @Redirect(method = "Lnet/minecraft/client/renderer/LevelRenderer;tickRain(Lnet/minecraft/client/Camera;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectWarmEnoughToRainInTick(Biome biome, BlockPos pos) {
        if (level == null) { // prevent NPE shenanigans
            return false;
        }
        return ModifiedTempAndHumid.canRain(level.getBiome(pos), pos);
    }


}
