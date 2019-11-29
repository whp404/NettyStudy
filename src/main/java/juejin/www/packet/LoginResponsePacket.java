package juejin.www.packet;


import lombok.Data;

import static juejin.www.utils.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    private String userId;

    private String userName;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
