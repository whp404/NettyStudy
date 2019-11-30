package juejin.www.packet;

import lombok.Data;
import lombok.NoArgsConstructor;

import static juejin.www.utils.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String message;

    private String toUserId;

    public MessageRequestPacket(String toUserId,String message ) {
        this.message = message;
        this.toUserId = toUserId;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}