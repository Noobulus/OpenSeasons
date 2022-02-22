package mod.noobulus.openseasons;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Registry {

	public static final Logger LOGGER = LogManager.getLogger(BuildConfig.MODID);

	// WIP

	static Item.Properties itemProps() {
		return new Item.Properties();
	}

	static RegistryObject<SoundEvent> addSound(String name) {
		SoundEvent event = new SoundEvent(new ResourceLocation(BuildConfig.MODID, name));
		return SOUND_EVENTS.register(name, () -> event);
	}

	static RegistryObject<Item> addItem(String name, Item item) {
		ITEM_MAP.put(name, item);
		return ITEMS.register(name, () -> item);
	}

	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
