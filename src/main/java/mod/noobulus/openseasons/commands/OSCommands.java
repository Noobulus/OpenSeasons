package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OSCommands {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher dp = event.getDispatcher();
        CheckTempCommand.register(dp);
    }
}
