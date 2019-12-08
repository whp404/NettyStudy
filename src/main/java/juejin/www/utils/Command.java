package juejin.www.utils;

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

    Byte MESSAGE_QUIT_GROUP = 9;

    Byte MESSAGE_QUIT_GROUP_RESPONSE = 10;

    Byte LIST_GROUP_MEMBER = 11;

    Byte LIST_GROUP_MEMBER_RESPONSE = 12;


    Byte LOG_OUT_MESSAGE = 13;

    Byte LOG_OUT_MESSAGE_RESPONSE = 14;

}
