package juejin.www.packet;

import lombok.Data;

import static juejin.www.utils.Command.MESSAGE_JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends  Packet{

    private boolean success;

    private String groupId;

    private String reason;

    @Override
    public Byte getCommand() {
        return MESSAGE_JOIN_GROUP_RESPONSE;
    }
}
