package juejin.www.packet;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static juejin.www.utils.Command.MESSAGE_QUIT_GROUP;

@Data
public class QuitGroupRequestPacket extends Packet {

    String groupId;

    @Override
    public Byte getCommand() {
        return MESSAGE_QUIT_GROUP;
    }
}
