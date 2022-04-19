package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    /* TODO: mixin to weather goodies to make rain more/less common seasonally
    advanceWeatherCycle and everything it messes with seem like good starting points */

    /*@Redirect(method = "Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitation()Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation redirectGetBiomePrecipitation(Biome biome) {
        return Biome.Precipitation.RAIN;
    }*/

    @Redirect(method = "Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectShouldFreeze(Biome biome, LevelReader level, BlockPos pos) {
        return ModifiedTempAndHumid.shouldFreeze(level, pos);
    }

    @Redirect(method = "Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectShouldSnow(Biome biome, LevelReader level, BlockPos pos) {
        return ModifiedTempAndHumid.shouldSnow(level, pos);
    }
}
