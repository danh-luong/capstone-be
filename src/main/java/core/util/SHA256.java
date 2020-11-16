package core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String encrypt(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(input.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte tmp : digest) {
            sb.append(String.format("%02x", tmp & 0xff));
        }
        return sb.toString();
    }

    /**
     * To get encrypt password
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println(SHA256.encrypt("user"));
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
