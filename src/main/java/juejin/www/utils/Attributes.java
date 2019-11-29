package juejin.www.utils;

import io.netty.util.AttributeKey;

public interface Attributes{

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login"); // key 为 login？ value 为boolean?

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
