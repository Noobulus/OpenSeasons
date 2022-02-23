package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class CheckHumidityCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("os:humid").executes((command) -> {
            Entity sender = command.getSource().getEntity();
            if (sender instanceof ServerPlayer player) {
                BlockPos blockpos = new BlockPos(player.getEyePosition());
                float humid = player.level.getBiome(blockpos).getDownfall();
                command.getSource().sendSuccess(new TextComponent("Humidity at current location: " + humid), true);
                return 1;
            }
            return 0;
        }));
    }
}

