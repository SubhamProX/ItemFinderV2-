package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class ChestHud {

    public static void register() {
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("itemfinder", "chest_list"),
                (graphics, deltaTracker) -> {
                    Minecraft client = Minecraft.getInstance();
                    if (client.player == null || ChestFinder.matchedChests.isEmpty()) return;

                    int y = 10;
                    graphics.text(client.font, "Item Finder - " + ChestFinder.matchedChests.size() + " found:", 10, y, 0xFFFFFFFF, true);
                    y += 12;

                    for (BlockPos pos : ChestFinder.matchedChests) {
                        double distance = Math.sqrt(client.player.blockPosition().distSqr(pos));
                        String line = String.format("X:%d Y:%d Z:%d  (%.1fm)", pos.getX(), pos.getY(), pos.getZ(), distance);
                        graphics.text(client.font, line, 10, y, 0xFFFFFF00, true);
                        y += 10;
                    }
                });
    }
}
