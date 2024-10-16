package huuduc;
/**
 *
 * @author huuducngo
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import org.apache.commons.codec.binary.Base32;

public class miniTOTP {
    
    private static final long TIME_STEP = 30;
    public String generateHash(String secret, long timeCounter) throws Exception {
        Base32 base32 = new Base32();
        byte[] keyBytes = base32.decode(secret);

        byte[] timeBytes = ByteBuffer.allocate(8).putLong(timeCounter).array();

        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec signKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        mac.init(signKey);

        byte[] hash = mac.doFinal(timeBytes);

        return getDigitsFromHash(hash);
    }
    public String getDigitsFromHash(byte[] hash) {
        int offset = hash[hash.length - 1] & 0x0F;

        int binary = ((hash[offset] & 0x7F) << 24) |
                     ((hash[offset + 1] & 0xFF) << 16) |
                     ((hash[offset + 2] & 0xFF) << 8) |
                     (hash[offset + 3] & 0xFF);
        int otp = binary % 1000000;
        
        return String.valueOf(otp);
    }

    public long getCurrentTimeCounter() {
        return System.currentTimeMillis()/ 1000L / TIME_STEP;
    }

    public static void main(String[] args) {
        try {
            miniTOTP totpGenerator = new miniTOTP();
            String  secret = "CQT6H2TUM2HCLNA5CKI7TD6CBXOARPC5";  // Example base64-encoded secret

            long timeCounter = totpGenerator.getCurrentTimeCounter();
            String digits = totpGenerator.generateHash(secret, timeCounter);

            System.out.println("Generated TOTP: " + digits);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
