package juejin.www.packet;

import lombok.Data;

import static juejin.www.utils.Command.LIST_GROUP_MEMBER;
@Data
public class ListGroupMembersRequestPacket extends  Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBER;
    }
}
