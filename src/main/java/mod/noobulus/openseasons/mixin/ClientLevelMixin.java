package mod.noobulus.openseasons.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import mod.noobulus.openseasons.util.HeightBiomeColors;
import mod.noobulus.openseasons.util.HeightColorResolver;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {
    @Shadow @Mutable @Final private Object2ObjectArrayMap<ColorResolver, BlockTintCache> tintCaches;

    protected ClientLevelMixin(WritableLevelData pLevelData, ResourceKey<Level> pDimension, Holder<DimensionType> pDimensionTypeRegistration, Supplier<ProfilerFiller> pProfiler, boolean pIsClientSide, boolean pIsDebug, long pBiomeZoomSeed) {
        super(pLevelData, pDimension, pDimensionTypeRegistration, pProfiler, pIsClientSide, pIsDebug, pBiomeZoomSeed);
    }

    // TODO: work out all the places in Biome where temperature and humidity are used and hook into them so i can mess with 'em
    // ex. freak weather, future seasonal hooks, snow golems surviving under ground, etc. (i get a freebie of thundersnows from this one)

    // TODO: mixin to getSkyColor, getCloudColor, getCloudColor to make sunshowers work

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.tintCaches = Util.make(new Object2ObjectArrayMap<>(3), (map) -> {
            map.put(BiomeColors.GRASS_COLOR_RESOLVER, new BlockTintCache((pos) -> {
                return this.calculateBlockTint(pos, HeightBiomeColors.GRASS_COLOR_RESOLVER);
            }));
            map.put(BiomeColors.FOLIAGE_COLOR_RESOLVER, new BlockTintCache((pos) -> {
                return this.calculateBlockTint(pos, HeightBiomeColors.FOLIAGE_COLOR_RESOLVER);
            }));
            map.put(BiomeColors.WATER_COLOR_RESOLVER, new BlockTintCache((pos) -> {
                return this.calculateBlockTint(pos, HeightBiomeColors.WATER_COLOR_RESOLVER);
            }));
        });
    }

    public int calculateBlockTint(BlockPos pBlockPos, HeightColorResolver pColorResolver) {
        int i = Minecraft.getInstance().options.biomeBlendRadius;
        if (i == 0) {
            return pColorResolver.getColor(this.getBiome(pBlockPos), pBlockPos);
        } else {
            int j = (i * 2 + 1) * (i * 2 + 1);
            int k = 0;
            int l = 0;
            int i1 = 0;
            Cursor3D cursor3d = new Cursor3D(pBlockPos.getX() - i, pBlockPos.getY(), pBlockPos.getZ() - i, pBlockPos.getX() + i, pBlockPos.getY(), pBlockPos.getZ() + i);

            int j1;
            for(BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(); cursor3d.advance(); i1 += j1 & 255) {
                blockpos$mutableblockpos.set(cursor3d.nextX(), cursor3d.nextY(), cursor3d.nextZ());
                j1 = pColorResolver.getColor(this.getBiome(blockpos$mutableblockpos), blockpos$mutableblockpos);
                k += (j1 & 16711680) >> 16;
                l += (j1 & '\uff00') >> 8;
            }

            return (k / j & 255) << 16 | (l / j & 255) << 8 | i1 / j & 255;
        }
    }
}
