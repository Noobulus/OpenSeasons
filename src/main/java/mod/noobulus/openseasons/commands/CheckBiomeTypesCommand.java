package mod.noobulus.openseasons.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Either;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Optional;
import java.util.Set;

public class CheckBiomeTypesCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("os:types").executes((command) -> {
            Entity sender = command.getSource().getEntity();
            if (sender instanceof ServerPlayer player) {
                BlockPos blockpos = new BlockPos(player.getEyePosition());
                Either<ResourceKey<Biome>, Biome> either = player.level.getBiome(blockpos).unwrap();
                Optional<ResourceKey<Biome>> optRk = either.left();
                StringBuilder output = new StringBuilder();
                if(optRk.isPresent()) {
                    Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(optRk.get());
                    for(BiomeDictionary.Type type : biomeTypes) {
                        output.append(type.getName()).append(", ");
                    }
                    output.delete(output.length() - 2, output.length() - 1); // terrible way to get rid of the last comma
                }
                command.getSource().sendSuccess(new TextComponent("Biome Types: " + output), true);
                return 1;
            }
            return 0;
        }));
    }
}
