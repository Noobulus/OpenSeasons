package mod.noobulus.openseasons;

import mod.noobulus.openseasons.commands.OpenSeasonsCommands;
import mod.noobulus.openseasons.init.DefaultSeasons;
import mod.noobulus.openseasons.init.OpenSeasonsTags;
import mod.noobulus.openseasons.init.Registry;
import mod.noobulus.openseasons.network.OpenSeasonsMessages;
import mod.noobulus.openseasons.seasons.ServerSeasonManager;
import mod.noobulus.openseasons.util.NetworkHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BuildConfig.MODID)
public class OpenSeasons
{
	public static final Logger LOGGER = LogManager.getLogger(BuildConfig.MODID);
	public static final String MOD_ID = BuildConfig.MODID;

	public OpenSeasons() {
		//ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
		//Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(BuildConfig.MODID + "-common.toml"));
		//IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(OpenSeasonsCommands.class);
		MinecraftForge.EVENT_BUS.register(ServerSeasonManager.class);
		MinecraftForge.EVENT_BUS.register(NetworkHelper.class);
		Registry.register();
		OpenSeasonsTags.initTags();
		DefaultSeasons.initSeasons();
		OpenSeasonsMessages.register();
	}

	public static ResourceLocation asId(String name) {
		return new ResourceLocation(BuildConfig.MODID, name);
	}
}