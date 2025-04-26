// 加密工具类示例
package com.utils;
import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}