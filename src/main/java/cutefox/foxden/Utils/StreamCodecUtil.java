package cutefox.foxden.Utils;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;

public class StreamCodecUtil {

    public StreamCodecUtil() {
    }

    public static <T, B extends PacketByteBuf> PacketCodec<B, DefaultedList<T>> nonNullList(PacketCodec<B, T> elementCodec, T defaultElement) {
        return PacketCodec.of((value, buf) -> {
            buf.writeVarInt(value.size());
            Iterator var3 = value.iterator();

            while(var3.hasNext()) {
                T element = (T) var3.next();
                elementCodec.encode(buf, element);
            }

        }, (buf) -> {
            DefaultedList<T> list = DefaultedList.ofSize(buf.readVarInt(), defaultElement);
            list.replaceAll((element) -> {
                return elementCodec.decode(buf);
            });
            return list;
        });
    }
}
