package subham.itemfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

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
        int radius = 32;
        int chunkRadius = (radius / 16) + 1; // ek extra chunk buffer ke saath

        // Math.floorDiv use kiya taaki negative coordinates pe bhi sahi chunk mile
        int playerChunkX = Math.floorDiv(playerPos.getX(), 16);
        int playerChunkZ = Math.floorDiv(playerPos.getZ(), 16);

        for (int cx = -chunkRadius; cx <= chunkRadius; cx++) {
            for (int cz = -chunkRadius; cz <= chunkRadius; cz++) {
                int chunkX = playerChunkX + cx;
                int chunkZ = playerChunkZ + cz;

                if (!client.level.hasChunk(chunkX, chunkZ)) continue;

                LevelChunk chunk = client.level.getChunk(chunkX, chunkZ);

                for (BlockEntity be : chunk.getBlockEntities().values()) {
                    BlockPos pos = be.getBlockPos();
                    if (pos.distSqr(playerPos) > (double) radius * radius) continue;

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
            if (container.getItem(i).is(targetItem)) {
                return true;
            }
        }
        return false;
    }
                }
