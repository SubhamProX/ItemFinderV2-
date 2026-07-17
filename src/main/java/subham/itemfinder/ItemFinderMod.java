package subham.itemfinder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemFinderMod implements ModInitializer {

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(SearchPacket.TYPE, SearchPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SearchResultPacket.TYPE, SearchResultPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(SearchPacket.TYPE, (payload, context) -> {
            ServerLevel level = context.player().serverLevel();
            BlockPos center = context.player().blockPosition();
            int radius = payload.radius();

            Identifier itemId = Identifier.parse(payload.itemId());
            Item targetItem = BuiltInRegistries.ITEM.get(itemId);

            StringBuilder positions = new StringBuilder();
            int count = 0;

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = center.offset(x, y, z);

                        BlockState state = level.getBlockState(pos);
                        if (state.isAir() || !state.hasBlockEntity()) continue;

                        BlockEntity be = level.getBlockEntity(pos);
                        if (be == null) continue;

                        Container container = null;
                        if (be instanceof ChestBlockEntity chest) container = chest;
                        else if (be instanceof ShulkerBoxBlockEntity shulker) container = shulker;

                        if (container != null && containsItem(container, targetItem)) {
                            if (count > 0) positions.append("|");
                            positions.append(pos.getX()).append(",").append(pos.getY()).append(",").append(pos.getZ());
                            count++;
                        }
                    }
                }
            }

            SearchResultPacket result = new SearchResultPacket(positions.toString(), count);
            context.responseSender().sendPacket(result);
        });
    }

    private static boolean containsItem(Container container, Item targetItem) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).is(targetItem)) return true;
        }
        return false;
    }
          }
