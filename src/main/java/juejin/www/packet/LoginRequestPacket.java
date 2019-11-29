package juejin.www.packet;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static juejin.www.utils.Command.LOGIN_REQUEST;

@Data
@Getter
@Setter
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
