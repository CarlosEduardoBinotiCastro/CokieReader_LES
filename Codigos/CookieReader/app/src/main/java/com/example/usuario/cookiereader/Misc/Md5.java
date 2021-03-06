package com.example.usuario.cookiereader.Misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Usuario on 23/05/2017.
 */

public class Md5 {

    private static final String md5(final String password) {
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    public String gerarMd5(String senha){
        return md5(senha);
    }
}
