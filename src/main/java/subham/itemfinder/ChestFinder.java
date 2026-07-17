package subham.itemfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ChestFinder {

    public static final List<BlockPos> matchedChests = new ArrayList<>();
    public static Item currentSearchItem;

    public static void scanForItem(Item targetItem) {
        currentSearchItem = targetItem;
        matchedChests.clear();

        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        if (level == null || client.player == null) return;

        BlockPos center = client.player.blockPosition();
        int radius = 24;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x, y, z);

                    BlockState state = level.getBlockState(pos);
                    if (state.isAir() || !state.hasBlockEntity()) continue;

                    BlockEntity be = level.getBlockEntity(pos);
                    if (be == null) continue;

                    if (be instanceof ChestBlockEntity chest && containsItem(chest, targetItem)) {
                        matchedChests.add(pos.immutable());
                    } else if (be instanceof ShulkerBoxBlockEntity shulker && containsItem(shulker, targetItem)) {
                        matchedChests.add(pos.immutable());
                    }
                }
            }
        }

        if (matchedChests.isEmpty()) {
            client.player.sendSystemMessage(Component.literal("§cNo chests found with this item."));
        } else {
            client.player.sendSystemMessage(Component.literal("§aFound " + matchedChests.size() + " container(s):"));
            for (BlockPos pos : matchedChests) {
                client.player.sendSystemMessage(Component.literal(" §7-> X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ()));
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
