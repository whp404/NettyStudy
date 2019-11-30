package juejin.www.utils;

import javax.print.DocFlavor;

public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

    Byte MESSAGE_GROUP_CREATE = 5;

    Byte MESSAGE_GROUP_CREATE_RESPONSE = 6;

    //加入群聊的命令
    Byte MESSAGE_JOIN_GROUP = 7;

    Byte MESSAGE_JOIN_GROUP_RESPONSE = 8;
}
