package juejin.www.packet;

import lombok.Data;


import static juejin.www.utils.Command.MESSAGE_JOIN_GROUP;

@Data
public class JoinGroupRequestPacket extends  Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return MESSAGE_JOIN_GROUP;
    }
}
