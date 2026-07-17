package subham.itemfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class ChestFinder {

    public static final List<BlockPos> matchedChests = new ArrayList<>();
    public static Item currentSearchItem;

    public static void scanForItem(Item targetItem) {
        currentSearchItem = targetItem;
        matchedChests.clear();
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        BlockPos playerPos = client.player.blockPosition();
        int radius = 24;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    BlockEntity be = client.level.getBlockEntity(pos);
                    if (be instanceof ChestBlockEntity chest && containsItem(chest, targetItem)) {
                        matchedChests.add(pos.immutable());
                    }
                }
            }
        }

        if (matchedChests.isEmpty()) {
            client.player.sendSystemMessage(Component.literal("Koi chest nahi mila is item ke saath."));
        } else {
            client.player.sendSystemMessage(Component.literal(matchedChests.size() + " chest(s) mile:"));
            for (BlockPos pos : matchedChests) {
                client.player.sendSystemMessage(Component.literal(" -> X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ()));
            }
        }
    }

    private static boolean containsItem(Container container, Item targetItem) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).is(targetItem)) return true;
        }
        return false;
    }
                  }
