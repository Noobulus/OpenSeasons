package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ClimateChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowGolem.class)
public abstract class SnowGolemMixin extends Entity {
    protected SnowGolemMixin(EntityType<?> pEntityType, Level pLevel) { // make java shut up
        super(pEntityType, pLevel);
    }

    @Redirect(method = "Lnet/minecraft/world/entity/animal/SnowGolem;aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnowGolemBurn(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectShouldGolemBurn(Biome biome, BlockPos pos) {
        return ClimateChecks.moddedShouldSnowGolemBurn(this.level.getBiome(pos), pos);
    }
}
