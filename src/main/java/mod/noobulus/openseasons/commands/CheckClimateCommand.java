package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import mod.noobulus.openseasons.mixin.AccessorBiome;
import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class CheckClimateCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("os:climate").executes((command) -> {
            Entity sender = command.getSource().getEntity();
            if (sender instanceof ServerPlayer player) {
                BlockPos blockpos = new BlockPos(player.getEyePosition());
                float temp = ((AccessorBiome) (Object) player.level.getBiome(blockpos)).callGetTemperature(blockpos);
                float humid = player.level.getBiome(blockpos).getDownfall();
                float modTemp = ModifiedTempAndHumid.getModifiedTemperature(player.level.getBiome(blockpos), blockpos);
                float modHumid = ModifiedTempAndHumid.getModifiedHumidity(player.level.getBiome(blockpos), blockpos);
                command.getSource().sendSuccess(new TextComponent("Base Temp/Humid: " + temp + "/" + humid + " | Mod Temp/Humid: " + modTemp + "/" + modHumid), true);
                return 1;
            }
            return 0;
        }));
    }
}
