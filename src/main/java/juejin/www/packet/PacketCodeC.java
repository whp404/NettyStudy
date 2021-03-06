package juejin.www.packet;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import juejin.www.Handler.LogoutResponseHandler;
import juejin.www.utils.JSONSerializer;
import juejin.www.utils.Serializer;

import java.util.HashMap;
import java.util.Map;

import static juejin.www.utils.Command.*;


public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);


        packetTypeMap.put(MESSAGE_GROUP_CREATE, CreateGroupRequestPacket.class);
        packetTypeMap.put(MESSAGE_GROUP_CREATE_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(MESSAGE_JOIN_GROUP, JoinGroupRequestPacket.class);
        packetTypeMap.put(MESSAGE_JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(MESSAGE_QUIT_GROUP, QuitGroupRequestPacket.class);
        packetTypeMap.put(MESSAGE_QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(LOG_OUT_MESSAGE, LogoutRequestPacket.class);
        packetTypeMap.put(LOG_OUT_MESSAGE_RESPONSE, LogoutResponsePacket.class);





        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public void  encode(ByteBuf byteBuf,Packet packet){
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
