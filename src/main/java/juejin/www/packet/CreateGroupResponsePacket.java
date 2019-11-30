package juejin.www.packet;

import lombok.Data;

import java.util.List;

import static juejin.www.utils.Command.MESSAGE_GROUP_CREATE_RESPONSE;
@Data
public class CreateGroupResponsePacket extends  Packet {

    private boolean success;

    private String GroupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return MESSAGE_GROUP_CREATE_RESPONSE;
    }
}
