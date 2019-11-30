package juejin.www.packet;

import lombok.Data;

import java.util.List;

import static juejin.www.utils.Command.MESSAGE_GROUP_CREATE;
@Data
public class CreateGroupRequestPacket extends Packet{

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return MESSAGE_GROUP_CREATE;
    }
}
