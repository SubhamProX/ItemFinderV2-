package subham.itemfinder;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class ItemFinderClient implements ClientModInitializer {
	private static KeyMapping openFinderKey;
openFinderKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
    "key.itemfinder.open",
    InputConstants.Type.KEYSYM,
    GLFW.GLFW_KEY_Y,
    "key.categories.misc"
));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openFinderKey.wasPressed()) {
				client.setScreen(new ItemFinderScreen());
			}
		});
	}
}
