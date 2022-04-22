package mod.noobulus.openseasons.mixin;

import mod.noobulus.openseasons.seasons.SeasonMap;
import mod.noobulus.openseasons.util.ClimateChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(RotatedPillarBlock.class)
public abstract class RotatedPillarBlockMixin extends Block {
    private RotatedPillarBlockMixin() { super(null); }

    @Intrinsic
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Intrinsic
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
    }

    // plugin complains but mixin works, emi magic at work
    @Inject(at = @At("HEAD"), method = "randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo info) {
        if (state.is(BlockTags.LOGS_THAT_BURN) && ClimateChecks.getCurrentSeason().equals(SeasonMap.getByName("Fall"))) {
            if (random.nextInt(10) == 0) { // regularly 25
                int i = 5;
                int j = 4;

                Block mushroom = Blocks.BROWN_MUSHROOM;
                BlockState mushroomState = mushroom.defaultBlockState();

                for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                    if (level.getBlockState(blockpos).is(mushroom)) {
                        --i;
                        if (i <= 0) {
                            return;
                        }
                    }
                }

                BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

                for(int k = 0; k < 4; ++k) {
                    if (level.isEmptyBlock(blockpos1) && mushroomState.canSurvive(level, blockpos1)) {
                        pos = blockpos1;
                    }

                    blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                }

                if (level.isEmptyBlock(blockpos1) && mushroomState.canSurvive(level, blockpos1)) {
                    level.setBlock(blockpos1, mushroomState, 2);
                }
            }
        }
    }
}
