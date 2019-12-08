package juejin.www.packet;

import lombok.Data;

import static juejin.www.utils.Command.LOG_OUT_MESSAGE_RESPONSE;
@Data
public class LogoutResponsePacket extends  Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOG_OUT_MESSAGE_RESPONSE;
    }
}
