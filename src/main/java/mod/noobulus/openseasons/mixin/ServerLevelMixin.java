package mod.noobulus.openseasons.mixin;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    /* TODO: mixin to weather goodies to make rain more/less common seasonally
    advanceWeatherCycle and everything it messes with seem like good starting points */
}
