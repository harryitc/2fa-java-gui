/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package totp;

import java.security.MessageDigest;

/**
 *
 * @author test
 */
public class SHA {

    public static String DEFAULT_SHA_VERSION = "SHA"; // sha1 || Another hash: SHA-256
    public static String SALT = "AeSoftware"; // key specified


    public static String Hash(String password, String secretKey) {
        try {
            MessageDigest md = MessageDigest.getInstance(DEFAULT_SHA_VERSION); 

            String hash = secretKey + password + SALT;

            md.update(hash.getBytes());

            byte[] byteData = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            System.out.println("Error when hash: " + e);
        }
        return "INVALID";
    }
}
