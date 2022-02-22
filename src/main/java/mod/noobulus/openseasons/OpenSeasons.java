package mod.noobulus.tetrapak;

import mod.noobulus.tetrapak.loot.LootConditions;
import mod.noobulus.tetrapak.networking.Packets;
import mod.noobulus.tetrapak.predicate.PredicateManagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BuildConfig.MODID)
public class OpenSeasons
{
	public static final Logger LOGGER = LogManager.getLogger(BuildConfig.MODID);

	public OpenSeasons() {
		//ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
		//Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(BuildConfig.MODID + "-common.toml"));
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		Registry.register();
	}

	public static ResourceLocation asId(String name) {
		return new ResourceLocation(BuildConfig.MODID, name);
	}
}