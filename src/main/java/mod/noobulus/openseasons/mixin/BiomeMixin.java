package mod.noobulus.openseasons.mixin;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mod.noobulus.openseasons.util.ClimateChecks.getCurrentSeason;

@Mixin(Biome.class)
public class BiomeMixin {
    @Shadow @Final
    private Biome.ClimateSettings climateSettings;
    
    @Inject(method = "Lnet/minecraft/world/level/biome/Biome;getPrecipitation()Lnet/minecraft/world/level/biome/Biome$Precipitation;", at = @At("RETURN"), cancellable = true)
    public void getPrecipitation(CallbackInfoReturnable<Biome.Precipitation> cir) {
        /*Registry<Biome> BIR = BuiltinRegistries.BIOME;
        Holder<Biome> biome = BIR.getHolderOrThrow(BIR.getResourceKey(((Biome)(Object) this)).orElseThrow());
        if (biome.containsTag(OpenSeasonsTags.IS_SEASONS_DENIED) || !biome.containsTag(OpenSeasonsTags.IS_SEASONS_ALLOWED)) {
            return;
        }*/
        float temperature = this.climateSettings.temperature + getCurrentSeason().getTempMod();
        float humidity = this.climateSettings.downfall + getCurrentSeason().getHumidMod();
        if (humidity < 0.1F || temperature > 1.5F) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        } else if (temperature >= 0.15F) {
            cir.setReturnValue(Biome.Precipitation.RAIN);
        } else {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
}

