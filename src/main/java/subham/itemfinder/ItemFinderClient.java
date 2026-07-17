package subham.itemfinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;

public class ItemFinderClient implements ClientModInitializer {

    private static KeyMapping openFinderKey;

    @Override
    public void onInitializeClient() {
        openFinderKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.itemfinder.open",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_Y,
            KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openFinderKey.consumeClick()) {
                Minecraft.getInstance().setScreen(new ItemFinderScreen());
            }
        });
    }
}
