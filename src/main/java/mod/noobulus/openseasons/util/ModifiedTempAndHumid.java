package mod.noobulus.openseasons.util;

import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import mod.noobulus.openseasons.init.OpenSeasonsTags;
import mod.noobulus.openseasons.mixin.AccessorBiome;
import mod.noobulus.openseasons.seasons.ClientSeasonManager;
import mod.noobulus.openseasons.seasons.Season;
import mod.noobulus.openseasons.seasons.ServerSeasonManager;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.DistExecutor;

public class ModifiedTempAndHumid {
    private static int colderAboveLevel = 80;
    private static int caveLevelBelow = 55;
    private static int warmCaveLevelBelow = 0;
    private static float skyScaleFactor = 0.05F / 40.0F;
    private static float caveTempScaleFactor = 5.0F / 40.0F;
    private static float caveHumidScaleFactor = 2.0F / 40.0F;
    private static float warmCaveScaleFactor = 5.0F / 160.0F;
    private static float caveTemp = 0.5F;
    private static float caveHumidity = 0.5F;
    private static float warmCaveTemp = 2.0F;
    private static float warmCaveHumidity = 0.5F;
    private static float caveNoiseScale = 0.05F;

    private static final ThreadLocal<Long2FloatLinkedOpenHashMap> tempCache = ThreadLocal.withInitial(() -> {
        return Util.make(() -> {
            Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
                protected void rehash(int scrungle) {
                }
            };
            long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
            return long2floatlinkedopenhashmap;
        });
    });

    private static final ThreadLocal<Long2FloatLinkedOpenHashMap> humidCache = ThreadLocal.withInitial(() -> {
        return Util.make(() -> {
            Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
                protected void rehash(int scrongle) {
                }
            };
            long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
            return long2floatlinkedopenhashmap;
        });
    });

    public static void refreshCaches() {
        tempCache.get().clear();
        humidCache.get().clear();
    }

    public static float getModifiedTemperature(Holder<Biome> biome, BlockPos pos) {
        long i = pos.asLong();
        Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = tempCache.get();
        float f = long2floatlinkedopenhashmap.get(i);
        if (!Float.isNaN(f)) {
            return f;
        } else {
            float f1 = getHeightTemperature(biome, pos);
            if (long2floatlinkedopenhashmap.size() == 1024) {
                long2floatlinkedopenhashmap.removeFirstFloat();
            }

            long2floatlinkedopenhashmap.put(i, f1);
            return f1;
        }
    }

    // TODO: fix the shit list - swamps, dark forests, and badlands all have color overrides that i don't really want below ground
    // TODO: implement alternative colormaps for different seasons and just colormaps in general for spruce, birch, lilypads, etc.
    // TODO: figure out how to support non-overworld biomes

    private static float getHeightTemperature(Holder<Biome> biome, BlockPos pos) {
        float tempToMod = ((AccessorBiome) (Object) biome.value()).getClimateSettings().temperatureModifier.modifyTemperature(pos, biome.value().getBaseTemperature());
        if (climateDenyListed(biome)) {
            return tempToMod;
        }
        tempToMod += getCurrentSeason().getTempMod();
        int yPos = pos.getY();
        float noiseMod = (float)(((AccessorBiome) (Object) biome.value()).getTEMPERATURE_NOISE().getValue((pos.getX() / 8.0F), (pos.getZ() / 8.0F), false) * 8.0D);
        if (yPos > colderAboveLevel) {
            return tempToMod - (noiseMod + yPos - colderAboveLevel) * skyScaleFactor;
        } else if (yPos < caveLevelBelow && yPos > warmCaveLevelBelow) {
            float moddedTemp = tempToMod + (((tempToMod > caveTemp) ? -1 : 1) * (((noiseMod * caveNoiseScale) + caveLevelBelow - yPos) * caveTempScaleFactor));
            if (tempToMod > caveTemp) {
                return Math.max(moddedTemp, caveTemp);
            } else {
                return Math.min(moddedTemp, caveTemp);
            }
        } else if (yPos <= warmCaveLevelBelow) {
            float moddedTemp =  caveTemp + (((noiseMod * caveNoiseScale) + warmCaveLevelBelow - yPos) * warmCaveScaleFactor); // get warmer below y=0
            return Math.min(moddedTemp, warmCaveTemp);
        } else {
            return tempToMod;
        }
    }

    public static float getModifiedHumidity(Holder<Biome> biome, BlockPos pos) {
        long i = pos.asLong();
        Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = humidCache.get(); // steal caching code from temps
        float f = long2floatlinkedopenhashmap.get(i);
        if (!Float.isNaN(f)) {
            return f;
        } else {
            float f1 = Mth.clamp(getHeightHumidity(biome, pos), 0f, 1f); // constrain humidity to between 0 and 1
            if (long2floatlinkedopenhashmap.size() == 1024) {
                long2floatlinkedopenhashmap.removeFirstFloat();
            }

            long2floatlinkedopenhashmap.put(i, f1);
            return f1;
        }
    }

    private static float getHeightHumidity(Holder<Biome> biome, BlockPos pos) {
        float humidToMod = biome.value().getDownfall();
        if (climateDenyListed(biome)) {
            return humidToMod;
        }
        //float noiseMod = (float)(((AccessorBiome) (Object) biome).getTEMPERATURE_NOISE().getValue((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 8.0D)
        humidToMod += getCurrentSeason().getHumidMod();
        int yPos = pos.getY();
        float noiseMod = 0F; //TODO: add noise to humidity scaling like with temperature
        if (yPos < caveLevelBelow) {
            float moddedHumid = humidToMod + (((humidToMod > caveHumidity) ? -1 : 1) * ((noiseMod * caveNoiseScale) + caveLevelBelow - yPos) * caveHumidScaleFactor);
            // ((tempToMod > caveTemp) ? -1 : 1) * ((noiseMod + warmCaveLevelBelow - yPos) * caveScaleFactor)
            if (humidToMod > caveHumidity) {
                return Math.max(moddedHumid, caveHumidity);
            } else {
                return Math.min(moddedHumid, caveHumidity);
            }
        } else { // TODO: figure out behavior below y = 0
            return humidToMod;
        }
    }

    public static boolean climateDenyListed(Holder<Biome> biome) {
        return biome.containsTag(OpenSeasonsTags.IS_SEASONS_DENIED) || !biome.containsTag(OpenSeasonsTags.IS_SEASONS_ALLOWED);
        //Biome.BiomeCategory category = ((AccessorBiome) (Object) biome.value()).getBiomeCategory()
        //return category.equals(Biome.BiomeCategory.UNDERGROUND) || category.equals(Biome.BiomeCategory.NETHER) || category.equals(Biome.BiomeCategory.THEEND)
    }

    public static Season getCurrentSeason() {
        return DistExecutor.safeRunForDist(
                () -> ClientSeasonManager::getCurrentSeason,
                () -> ServerSeasonManager::getCurrentSeason
        );
    }

    /* gigantic block of methods that are used for redirects somewhere else
    if this code is jank i blame vanilla */

    public static int getClimateFireChanceMod(Holder<Biome> biome, BlockPos pos) {
        // default chances are 300 for cardinals and 250 for vertical
        float humidity = getModifiedHumidity(biome, pos);
        float temperature = getModifiedTemperature(biome, pos);
        return 0;
    }

    public static float getClimateFireEncouragementMult(Holder<Biome> biome, BlockPos pos) {
        float humidity = getModifiedHumidity(biome, pos);
        if (humidity > 0.75F) {
            return 0.5F; // more generous bottom bound that vanilla
        } else if (humidity < 0.15F) {
            return 4F; // dry as hell means tinderbox
        } else if (humidity < 0.3F) {
            return 2F; // increase spread in
        } else {
            return 1F; // normal when 0.3 < humidity < 0.75
        }
    }

    public static boolean shouldRainHere(Holder<Biome> biome, BlockPos pos) {
        return true;
        /*float humidity = getModifiedHumidity(biome, pos);
        float temperature = getModifiedTemperature(biome, pos);
        return humidity > 0.3 && temperature < 0.95;*/
    }

    public static boolean moddedWarmEnoughToRain(Holder<Biome> biome, BlockPos pos) { // TODO: make snow work
        return false;
        /*float humidity = getModifiedHumidity(biome, pos);
        float temperature = getModifiedTemperature(biome, pos);
        return humidity > 0.3 && temperature < 0.95;*/
    }

    public static boolean moddedColdEnoughToSnow(Holder<Biome> biome, BlockPos pos) {
        return !moddedWarmEnoughToRain(biome, pos);
    }

    public static boolean shouldFreeze(LevelReader levelReader, BlockPos pos) {
        return shouldFreeze(levelReader, pos, true);
    }

    public static boolean shouldFreeze(LevelReader levelReader, BlockPos waterPos, boolean mustBeAtEdge) {
        if (!moddedWarmEnoughToRain(levelReader.getBiome(waterPos), waterPos)) {
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
        return getModifiedTemperature(biome, pos) > 1.0F;
    }

    public static boolean shouldSnow(LevelReader levelReader, BlockPos pos) {
        if (moddedWarmEnoughToRain(levelReader.getBiome(pos), pos)) {
            return false;
        } else {
            if (pos.getY() >= levelReader.getMinBuildHeight() && pos.getY() < levelReader.getMaxBuildHeight() && levelReader.getBrightness(LightLayer.BLOCK, pos) < 10) {
                BlockState blockstate = levelReader.getBlockState(pos);
                return blockstate.isAir() && Blocks.SNOW.defaultBlockState().canSurvive(levelReader, pos);
            }
            return false;
        }
    }
}
