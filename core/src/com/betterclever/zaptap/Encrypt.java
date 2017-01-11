package com.betterclever.zaptap;

import com.badlogic.gdx.utils.Base64Coder;

/**
 * Created by betterclever on 11/01/17.
 */

public class Encrypt {

    public static String encrypt(int score){
        String sc = String.valueOf(score);
        return Base64Coder.encodeString(sc);
    }

    public static int decrypt(String encrypted){
        String de = Base64Coder.decodeString(encrypted);
        if(de.equals("")){
            return 0;
        }
        return Integer.parseInt(de);
    }

}
