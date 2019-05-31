package tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashTool {

    public static String getHashedString(String originalString) {
        try {
            MessageDigest digest = null;
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    originalString.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
