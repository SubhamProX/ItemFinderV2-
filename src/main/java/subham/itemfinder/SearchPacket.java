package subham.itemfinder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SearchPacket(String itemId, int radius) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SearchPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("itemfinder", "search"));

    public static final StreamCodec<FriendlyByteBuf, SearchPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SearchPacket::itemId,
            ByteBufCodecs.VAR_INT, SearchPacket::radius,
            SearchPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
  }
