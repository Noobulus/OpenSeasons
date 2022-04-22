package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ClimateChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Redirect(method = "Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectShouldFreeze(Biome biome, LevelReader level, BlockPos pos) {
        return ClimateChecks.shouldFreeze(level, pos);
    }

    @Redirect(method = "Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectShouldSnow(Biome biome, LevelReader level, BlockPos pos) {
        return ClimateChecks.shouldSnow(level, pos);
    }

    // fun fact, did you know @ModifyArgs doesn't work on forge? i didn't know!

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 0), index = 1)
    private int modifyMinThunderTime(int minThunderTime) {
        float mod = ClimateChecks.getCurrentSeason().getMinThunderTimeMod();
        return (int) (minThunderTime * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 0), index = 2)
    private int modifyMaxThunderTime(int maxThunderTime) {
        float mod = ClimateChecks.getCurrentSeason().getMaxThunderTimeMod();
        return (int) (maxThunderTime * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 1), index = 1)
    private int modifyMinThunderDelay(int minThunderDelay) {
        float mod = ClimateChecks.getCurrentSeason().getMinThunderDelayMod();
        return (int) (minThunderDelay * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 1), index = 2)
    private int modifyMaxThunderDelay(int maxThunderDelay) {
        float mod = ClimateChecks.getCurrentSeason().getMaxThunderDelayMod();
        return (int) (maxThunderDelay * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 2), index = 1)
    private int modifyMinRainTime(int minRainTime) {
        float mod = ClimateChecks.getCurrentSeason().getMinRainTimeMod();
        return (int) (minRainTime * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 2), index = 2)
    private int modifyMaxRainTime(int maxRainTime) {
        float mod = ClimateChecks.getCurrentSeason().getMaxRainTimeMod();
        return (int) (maxRainTime * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 3), index = 1)
    private int modifyMinRainDelay(int minRainDelay) {
        float mod = ClimateChecks.getCurrentSeason().getMinRainDelayMod();
        return (int) (minRainDelay * mod);
    }

    @ModifyArg(method = "Lnet/minecraft/server/level/ServerLevel;advanceWeatherCycle()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;randomBetweenInclusive(Ljava/util/Random;II)I", ordinal = 3), index = 2)
    private int modifyMaxRainDelay(int maxRainDelay) {
        float mod = ClimateChecks.getCurrentSeason().getMaxRainDelayMod();
        return (int) (maxRainDelay * mod);
    }

        /*some references:
        if (thundering) {
            j = Mth.randomBetweenInclusive(this.random, MIN_THUNDER_TIME, MAX_THUNDER_TIME);
        } else {
            j = Mth.randomBetweenInclusive(this.random, MIN_THUNDER_DELAY_TIME, MAX_THUNDER_DELAY_TIME);
        }
        [...]
        if (raining) {
            k = Mth.randomBetweenInclusive(this.random, MIN_RAIN_TIME, MAX_RAIN_TIME);
        } else {
            k = Mth.randomBetweenInclusive(this.random, MIN_RAIN_DELAY_TIME, MAX_RAIN_DELAY_TIME);
        }

        MIN_RAIN_DELAY_TIME = 12000;
        MAX_RAIN_DELAY_TIME = 180000;
        MIN_RAIN_TIME = 12000;
        MAX_RAIN_TIME = 24000;
        MIN_THUNDER_DELAY_TIME = 12000;
        MAX_THUNDER_DELAY_TIME = 180000;
        MIN_THUNDER_TIME = 3600;
        MAX_THUNDER_TIME = 15600;
    */
}
