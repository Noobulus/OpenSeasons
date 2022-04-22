package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ClimateChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends BaseFireBlock {
    protected FireBlockMixin(Properties pProperties, float pFireDamage) {
        super(pProperties, pFireDamage);
    }

    @ModifyArg(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FireBlock;tryCatchFire(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;ILjava/util/Random;ILnet/minecraft/core/Direction;)V"), index = 2)
    private int modifyFireChance(Level level, BlockPos pos, int chance, Random random, int age, Direction face) {
        return (int) (chance * ClimateChecks.getClimateFireChanceMult(level.getBiome(pos), pos));
    }

    @ModifyVariable(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("STORE"), ordinal = 1)
    private boolean disableHumidFlag(boolean flag) { // i reject your climate-based fire spread and substitute my own
        return false;
    }

    @ModifyVariable(method = "Lnet/minecraft/world/level/block/FireBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("STORE"), ordinal = 8)
    private int modifyFireEncouragement(int encouragement, BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        return (int) (encouragement * ClimateChecks.getClimateFireEncouragementMult(pLevel.getBiome(pPos), pPos));
    }
}
