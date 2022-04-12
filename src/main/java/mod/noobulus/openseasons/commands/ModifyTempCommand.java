package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import mod.noobulus.openseasons.util.ModifiedTempAndHumid;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class ModifyTempCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("os:modtemp").then(Commands.argument("temp", DoubleArgumentType.doubleArg()).executes((command) -> {
            Entity sender = command.getSource().getEntity();
            if (sender instanceof ServerPlayer) {
                float oldTemp = ModifiedTempAndHumid.cmdTempMod;
                ModifiedTempAndHumid.cmdTempMod = (float) DoubleArgumentType.getDouble(command, "temp");
                float newTemp = ModifiedTempAndHumid.cmdTempMod;
                command.getSource().sendSuccess(new TextComponent("Changed Temp Mod From " + oldTemp + " To " + newTemp), true);
                return 1;
            }
            return 0;
        })));
    }
}
