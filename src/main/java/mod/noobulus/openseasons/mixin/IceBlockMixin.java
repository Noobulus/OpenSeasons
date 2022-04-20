package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin {
    @Shadow protected abstract void melt(BlockState pState, Level pLevel, BlockPos pPos);

    @Inject(method = "Lnet/minecraft/world/level/block/IceBlock;randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("TAIL"))
    private void makeIceMeltWhenHot(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {
        if (ModifiedTempAndHumid.shouldIceMelt(level.getBiome(pos), pos)) {
            this.melt(state, level, pos);
        }
    }
}
