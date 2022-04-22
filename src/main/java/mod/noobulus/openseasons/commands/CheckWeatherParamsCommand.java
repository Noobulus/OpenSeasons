package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import mod.noobulus.openseasons.mixin.AccessorServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ServerLevelData;

public class CheckWeatherParamsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("openseasons").then(Commands.literal("weather").executes((command) -> {
            Entity sender = command.getSource().getEntity();
            if (sender instanceof ServerPlayer player) {
                ServerLevelData levelData = ((AccessorServerLevel) player.getLevel()).getServerLevelData();
                int clearTime = levelData.getClearWeatherTime();
                int rainTime = levelData.getRainTime();
                int thunderTime = levelData.getThunderTime();
                command.getSource().sendSuccess(new TextComponent("Clear Time: " + clearTime + ", Rain Time: " + rainTime + ", Thunder Time:" + thunderTime), true);
                return 1;
            }
            return 0;
        })));
    }
}
