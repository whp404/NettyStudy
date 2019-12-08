package juejin.www.packet;

import static juejin.www.utils.Command.LOG_OUT_MESSAGE;

public class LogoutRequestPacket extends  Packet {




    @Override
    public Byte getCommand() {
        return LOG_OUT_MESSAGE;
    }
}
