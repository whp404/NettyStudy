package juejin.www.packet;

import lombok.Data;
import lombok.NoArgsConstructor;

import static juejin.www.utils.Command.MESSAGE_RESPONSE;

@Data
@NoArgsConstructor
public class MessageResponsePacket extends Packet {

    private String message;
    //发送者id
    private String fromUserId;
    //发送者名称
    private String fromUserName;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}