package mod.noobulus.openseasons.mixin;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Biome.class)
public class BiomeMixin {
    //TODO: make biome colors scale with height/rainfall
    //TODO: add the actual hooks to change temp/humidity with seasons lol
}
