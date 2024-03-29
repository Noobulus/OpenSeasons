package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OpenSeasonsCommands {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dp = event.getDispatcher();
        CheckClimateCommand.register(dp);
        BiomeHasSeasonsCommand.register(dp);
        CheckCurrentSeasonCommand.register(dp);
        CheckWeatherParamsCommand.register(dp);
    }
}
