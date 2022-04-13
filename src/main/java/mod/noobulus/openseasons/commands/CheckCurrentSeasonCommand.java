package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import mod.noobulus.openseasons.seasons.SeasonManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class CheckCurrentSeasonCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("os:get_season").executes((command) -> {
            command.getSource().sendSuccess(new TextComponent("Current Season: " + SeasonManager.getCurrentSeason().getName()), true);
            return 1;
        }));
    }
}

