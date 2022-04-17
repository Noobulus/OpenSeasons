package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends BaseFireBlock {
    protected FireBlockMixin(Properties pProperties, float pFireDamage) {
        super(pProperties, pFireDamage);
    }

    @ModifyVariable(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("STORE"), ordinal = 2)
    private int modifyFireTickrate(int k, BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        return ModifiedTempAndHumid.getClimateFireChanceMod(pLevel.getBiome(pPos), pPos);
    }

    @ModifyVariable(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("STORE"), ordinal = 1)
    private boolean disableHumidFlag(boolean flag) { // i reject your climate-based fire spread and substitute my own
        return false;
    }

    @ModifyVariable(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("STORE"), ordinal = 8)
    private int modifyFireSpread(int i2, BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        return (int) (i2 * ModifiedTempAndHumid.getClimateFireSpreadMult(pLevel.getBiome(pPos), pPos));
    }
}
