package mod.noobulus.openseasons;

import mod.noobulus.openseasons.commands.OSCommands;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BuildConfig.MODID)
public class OpenSeasons
{
	public static final Logger LOGGER = LogManager.getLogger(BuildConfig.MODID);

	public OpenSeasons() {
		//ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
		//Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(BuildConfig.MODID + "-common.toml"));
		//IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(OSCommands.class);
		Registry.register();

	}

	public static ResourceLocation asId(String name) {
		return new ResourceLocation(BuildConfig.MODID, name);
	}
}