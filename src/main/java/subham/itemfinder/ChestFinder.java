package subham.itemfinder;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ChestFinder {

    public static final List<BlockPos> matchedChests = new ArrayList<>();
    public static Item currentSearchItem;
    public static int baselineCount = 0;
    public static boolean searchActive = false;

    public static void scanForItem(Item targetItem) {
        currentSearchItem = targetItem;
        searchActive = true;

        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            baselineCount = client.player.getInventory().countItem(targetItem);
        }

        String itemId = BuiltInRegistries.ITEM.getKey(targetItem).toString();
        ClientPlayNetworking.send(new SearchPacket(itemId, 48));
    }

    public static void onResultReceived(String encodedPositions, int totalFound) {
        matchedChests.clear();
        Minecraft client = Minecraft.getInstance();

        if (totalFound == 0) {
            if (client.player != null) {
                client.player.sendSystemMessage(Component.literal("§cNo chests found with this item."));
            }
            return;
        }

        for (String part : encodedPositions.split("\\|")) {
            String[] coords = part.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);
            matchedChests.add(new BlockPos(x, y, z));
        }

        if (client.player != null) {
            client.player.sendSystemMessage(Component.literal("§aFound " + totalFound + " container(s):"));
            for (BlockPos pos : matchedChests) {
                client.player.sendSystemMessage(Component.literal(" §7-> X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ()));
            }
        }
    }
}
