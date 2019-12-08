package juejin.www.packet;


import juejin.www.utils.Session;
import lombok.Data;

import java.util.List;

import static juejin.www.utils.Command.LIST_GROUP_MEMBER_RESPONSE;
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private  String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBER_RESPONSE;
    }
}
