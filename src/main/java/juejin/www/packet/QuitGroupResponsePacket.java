package juejin.www.packet;

import lombok.Data;

import static juejin.www.utils.Command.MESSAGE_QUIT_GROUP_RESPONSE;
@Data
public class QuitGroupResponsePacket extends Packet {


    private String groupId;

    private boolean success;

    @Override
    public Byte getCommand() {
        return MESSAGE_QUIT_GROUP_RESPONSE;
    }
}
