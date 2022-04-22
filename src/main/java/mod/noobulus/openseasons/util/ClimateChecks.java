package mod.noobulus.openseasons.util;

import mod.noobulus.openseasons.seasons.ClientSeasonManager;
import mod.noobulus.openseasons.seasons.Season;
import mod.noobulus.openseasons.seasons.ServerSeasonManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.DistExecutor;

public class ClimateChecks {
    public static Season getCurrentSeason() {
        return DistExecutor.unsafeRunForDist(
                () -> ClientSeasonManager::getCurrentSeason,
                () -> ServerSeasonManager::getCurrentSeason
        );
    }

    public static float getClimateFireChanceMult(Holder<Biome> biome, BlockPos pos) {
        // lower chance here means more fire, wack
        float humidity = ModifiedTempAndHumid.getModifiedHumidity(biome, pos);
        if (humidity > 0.75F) {
            return 0.5F; // vanilla does this, actually!
        } else if (humidity < 0.15F) {
            return 0.75F; // really dry biomes tick fire a lot faster
        } else if (humidity < 0.3F) {
            return 0.85F; // drier biomes tick fire a little faster
        } else {
            return 1F; // normal when 0.3 < humidity < 0.75
        }
    }

    public static float getClimateFireEncouragementMult(Holder<Biome> biome, BlockPos pos) {
        float humidity = ModifiedTempAndHumid.getModifiedHumidity(biome, pos);
        if (humidity > 0.75F) {
            return 0.5F; // more generous bottom bound than vanilla
        } else if (humidity < 0.15F) {
            return 12F; // dry as hell means tinderbox
        } else if (humidity < 0.3F) {
            return 8F; // increased spread in drier biomes
        } else {
            return 1F; // normal when 0.3 < humidity < 0.75
        }
    }

    public static boolean canRainAndPrecipitate(Holder<Biome> biome, BlockPos pos) {
        float humidity = ModifiedTempAndHumid.getModifiedHumidity(biome, pos);
        float temperature = ModifiedTempAndHumid.getModifiedTemperature(biome, pos);
        return canRain(biome, pos) && canPrecipitate(temperature, humidity);
    }

    public static boolean canSnowAndPrecipitate(Holder<Biome> biome, BlockPos pos) {
        float humidity = ModifiedTempAndHumid.getModifiedHumidity(biome, pos);
        float temperature = ModifiedTempAndHumid.getModifiedTemperature(biome, pos);
        return canSnow(biome, pos) && canPrecipitate(temperature, humidity);
    }

    public static boolean canRain(Holder<Biome> biome, BlockPos pos) {
        float temperature = ModifiedTempAndHumid.getModifiedTemperature(biome, pos);
        return temperature >= 0.15F;
    }

    public static boolean canSnow(Holder<Biome> biome, BlockPos pos) {
        return !canRain(biome, pos);
    }

    public static boolean canPrecipitate(float temperature, float humidity) {
        return humidity > 0.3F && temperature < 1.5F ;
    }

    public static boolean shouldFreeze(LevelReader levelReader, BlockPos pos) {
        return shouldFreeze(levelReader, pos, true);
    }

    public static boolean shouldFreeze(LevelReader levelReader, BlockPos waterPos, boolean mustBeAtEdge) {
        if (canSnow(levelReader.getBiome(waterPos), waterPos)) {
            if (waterPos.getY() >= levelReader.getMinBuildHeight() && waterPos.getY() < levelReader.getMaxBuildHeight() && levelReader.getBrightness(LightLayer.BLOCK, waterPos) < 10) {
                BlockState blockstate = levelReader.getBlockState(waterPos);
                FluidState fluidstate = levelReader.getFluidState(waterPos);
                if (fluidstate.getType() == Fluids.WATER && blockstate.getBlock() instanceof LiquidBlock) {
                    if (!mustBeAtEdge) {
                        return true;
                    }
                    return !(levelReader.isWaterAt(waterPos.west()) && levelReader.isWaterAt(waterPos.east()) && levelReader.isWaterAt(waterPos.north()) && levelReader.isWaterAt(waterPos.south()));
                }
            }

        }
        return false;
    }

    public static boolean moddedShouldSnowGolemBurn(Holder<Biome> biome, BlockPos pos) {
        return ModifiedTempAndHumid.getModifiedTemperature(biome, pos) > 1.0F;
    }

    public static boolean shouldSnow(LevelReader levelReader, BlockPos pos) {
        if (canSnowAndPrecipitate(levelReader.getBiome(pos), pos)) {
            if (pos.getY() >= levelReader.getMinBuildHeight() && pos.getY() < levelReader.getMaxBuildHeight() && levelReader.getBrightness(LightLayer.BLOCK, pos) < 10) {
                BlockState blockstate = levelReader.getBlockState(pos);
                return blockstate.isAir() && Blocks.SNOW.defaultBlockState().canSurvive(levelReader, pos);
            }
        }
        return false;
    }

    public static boolean shouldSnowMelt(Holder<Biome> biome, BlockPos pos) {
        float temperature = ModifiedTempAndHumid.getModifiedTemperature(biome, pos);
        return temperature > 0.25F;
    }

    public static boolean shouldIceMelt(Holder<Biome> biome, BlockPos pos) {
        float temperature = ModifiedTempAndHumid.getModifiedTemperature(biome, pos);
        return temperature > 0.35F;
    }
}
