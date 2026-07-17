package subham.itemfinder;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ItemFinderClient implements ClientModInitializer {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("itemfinder", "custom_category")
    );

    private static KeyMapping openFinderKey;

    @Override
    public void onInitializeClient() {
        openFinderKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.itemfinder.open",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openFinderKey.consumeClick()) {
                Minecraft.getInstance().gui.setScreen(new ItemFinderScreen());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(
                SearchResultPacket.TYPE,
                (payload, context) -> context.client().execute(() ->
                        ChestFinder.onResultReceived(payload.encodedPositions(), payload.totalFound()))
        );

        // ChestHighlightRenderer.register();
        ChestTrailRenderer.register();
        ChestHud.register();
        InventoryWatcher.register();
    }
            }
