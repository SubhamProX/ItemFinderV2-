package subham.itemfinder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SearchResultPacket(String encodedPositions, int totalFound) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SearchResultPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("itemfinder", "search_result"));

    public static final StreamCodec<FriendlyByteBuf, SearchResultPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SearchResultPacket::encodedPositions,
            ByteBufCodecs.VAR_INT, SearchResultPacket::totalFound,
            SearchResultPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
  }
