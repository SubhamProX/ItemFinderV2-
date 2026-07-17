package subham.itemfinder;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class InventoryWatcher {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!ChestFinder.searchActive || ChestFinder.currentSearchItem == null) return;
            if (client.player == null) return;

            int currentCount = client.player.getInventory().countItem(ChestFinder.currentSearchItem);

            if (currentCount > ChestFinder.baselineCount) {
                client.player.sendSystemMessage(Component.literal("§a✔ You found it!"));

                ChestFinder.searchActive = false;
                ChestFinder.matchedChests.clear();
                ChestFinder.currentSearchItem = null;
            }
        });
    }
        }
