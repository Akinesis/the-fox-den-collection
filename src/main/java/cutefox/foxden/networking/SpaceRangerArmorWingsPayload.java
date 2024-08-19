package cutefox.foxden.networking;

import cutefox.foxden.Utils.Utils;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SpaceRangerArmorWingsPayload(boolean deployed, boolean helmetOpened) implements CustomPayload {

    public static final CustomPayload.Id<SpaceRangerArmorWingsPayload> ID = new CustomPayload.Id<>(Utils.id("ranger_deploy"));
    public static final PacketCodec<RegistryByteBuf, SpaceRangerArmorWingsPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SpaceRangerArmorWingsPayload::deployed,
            PacketCodecs.BOOL, SpaceRangerArmorWingsPayload::helmetOpened,
            SpaceRangerArmorWingsPayload::new);
// should you need to send more data, add the appropriate record parameters and change your codec:
    // public static final PacketCodec<RegistryByteBuf, BlockHighlightPayload> CODEC = PacketCodec.tuple(
    //         BlockPos.PACKET_CODEC, BlockHighlightPayload::blockPos,
    //         PacketCodecs.INTEGER, BlockHighlightPayload::myInt,
    //         Uuids.PACKET_CODEC, BlockHighlightPayload::myUuid,
    //         BlockHighlightPayload::new
    // );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

}
