package subham.itemfinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ItemFinderClient implements ClientModInitializer {
	private static KeyBinding openFinderKey;

	@Override
	public void onInitializeClient() {
		openFinderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.itemfinder.open",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_O,
			"category.itemfinder"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openFinderKey.wasPressed()) {
				client.setScreen(new ItemFinderScreen());
			}
		});
	}
}
