package mod.noobulus.tetrapak;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = BuildConfig.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {


	static {
		ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

		// WIP

		COMMON_CONFIG = commonBuilder.build();
	}

	private Config() {
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		spec.setConfig(configData);
	}
}
