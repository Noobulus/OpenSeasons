package mod.noobulus.openseasons.util;

import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import mod.noobulus.openseasons.mixin.AccessorBiome;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

public class ModifiedTempAndHumid {
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

    public static float getModifiedTemperature(Biome biome, BlockPos pos) {
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

    public static float cmdTempMod = 0.0F;

    // TODO: fix the shit list - swamps, dark forests, and badlands all have color overrides that i don't really want below ground
    // TODO: implement alternative colormaps for different seasons and just colormaps in general for spruce, birch, lilypads, etc.
    // TODO: make cave biomes immune to scaling and add an allowlist for dimensions so we don't have temperate lava oceans in the nether

    private static float getHeightTemperature(Biome biome, BlockPos pos) {
        float tempToMod = ((AccessorBiome) (Object) biome).getClimateSettings().temperatureModifier.modifyTemperature(pos, biome.getBaseTemperature()) + cmdTempMod;
        float noiseMod = (float)(((AccessorBiome) (Object) biome).getTEMPERATURE_NOISE().getValue((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 8.0D);
        if (pos.getY() > 80) {
            return tempToMod - (noiseMod + (float)pos.getY() - 80.0F) * 0.05F / 40.0F; // vanilla scaling
        } else if (pos.getY() < 55 && pos.getY() > 0) {
            float moddedTemp = tempToMod + (((tempToMod > 0.5) ? -1 : 1) * ((0.05F * noiseMod) + ((55.0F - (float)pos.getY())) * 5.0F)) / 40.0F; // TODO: fix this garbage
            if (tempToMod > 0.5) {
                return (moddedTemp > 0.5) ? moddedTemp : 0.5F;
            } else {
                return (moddedTemp < 0.5) ? moddedTemp : 0.5F;
            }
        } else if (pos.getY() <= 0) {
            float moddedTemp =  0.5F + (((0.05F * noiseMod) + ((0.0F - (float)pos.getY())) * 5.0F)) / 160.0F; // get warmer below y=0
            return (moddedTemp < 2.0) ? moddedTemp : 2.0F;
        } else {
            return tempToMod;
        }
    }

    public static float getModifiedHumidity(Biome biome, BlockPos pos) {
        long i = pos.asLong();
        Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = humidCache.get(); // steal caching code from temps
        float f = long2floatlinkedopenhashmap.get(i);
        if (!Float.isNaN(f)) {
            return f;
        } else {
            float f1 = getHeightHumidity(biome, pos);
            if (long2floatlinkedopenhashmap.size() == 1024) {
                long2floatlinkedopenhashmap.removeFirstFloat();
            }

            long2floatlinkedopenhashmap.put(i, f1);
            return f1;
        }
    }

    public static float getHeightHumidity(Biome biome, BlockPos pos) {
        float humidToMod = biome.getDownfall();
        //float noiseMod = (float)(((AccessorBiome) (Object) biome).getTEMPERATURE_NOISE().getValue((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 8.0D);
        float noiseMod = 0F; //TODO: add noise to humidity scaling like with temperature
        if (pos.getY() < 55) {
            float moddedHumid = humidToMod + (((humidToMod > 0.5) ? -1 : 1) * ((0.05F * noiseMod) + ((55.0F - (float)pos.getY())) * 2.0F)) / 40.0F; // TODO: fix this garbage
            if (humidToMod > 0.5) {
                return (moddedHumid > 0.5) ? moddedHumid : 0.5F;
            } else {
                return (moddedHumid < 0.5) ? moddedHumid : 0.5F;
            }
        } else { // TODO: figure out behavior below y = 0
            return humidToMod;
        }
    }
}
