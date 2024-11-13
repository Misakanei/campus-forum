package com.forum;

import com.forum.utils.MD5Util;
import org.junit.jupiter.api.Test;

public class md5Utiltest {
    @Test
    void testMD5Util() {
        String str = "123456";
        System.out.println(MD5Util.inputPassToFormPass(str));
    }
}
