package com.tangzy.tzymvp.util;

import android.util.Base64;


/**
 * Description:Base64编码
 * author: mayuhai
 * created on: 2019-07-09 17:09
 */
public class Base64Encode {

    /**
     * @param content 需要编码的内容
     * @return 编码后的内容
     * @throws Exception
     */
    
    public static String encrypt(String content){
        String encodedString = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
        return encodedString;
    }

    /**
     * @param content 编码的内容
     * @return 解码的内容
     * @throws Exception
     */
    
    public static String decrypt(String content) {
        String decodedString =new String(Base64.decode(content,Base64.DEFAULT));
        return decodedString;
    }
}
