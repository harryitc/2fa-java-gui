/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hmac;


import com.google.zxing.BarcodeFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import org.apache.commons.codec.binary.Base32;



/**
 *
 * @author Admin
 */
public class library {

    private static final long TIME_STEP = 30; // 30 seconds time step

    // Generate the TOTP digits using HMAC-SHA1 or other HMAC algorithms
    public String generateHash(String secret, long timeCounter) throws Exception {
        // Decode the secret (assuming it is Base64 encoded)
       Base32 base32 = new Base32();
        byte[] keyBytes = base32.decode(secret);

        // Convert the time counter to a byte array
        byte[] timeBytes = ByteBuffer.allocate(8).putLong(timeCounter).array();

        // Use HMAC-SHA1 for generating the hash (can be HmacSHA256 or HmacSHA512)
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec signKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        mac.init(signKey);

        // Generate the HMAC hash
        byte[] hash = mac.doFinal(timeBytes);

        // Truncate the hash to a 6-digit TOTP
        return getDigitsFromHash(hash);
    }

    // Dynamic truncation of the hash to generate a 6-digit OTP
    public String getDigitsFromHash(byte[] hash) {
        // Get the offset from the last byte of the hash
        int offset = hash[hash.length - 1] & 0x0F;

        // Extract 4 bytes starting at the offset and convert to integer
        int binary = ((hash[offset] & 0x7F) << 24) |
                     ((hash[offset + 1] & 0xFF) << 16) |
                     ((hash[offset + 2] & 0xFF) << 8) |
                     (hash[offset + 3] & 0xFF);

        // Get the last 6 digits from the integer
        int otp = binary % 1_000_000;

        // Format OTP with leading zeros if necessary
        return String.valueOf(otp);
    }

    // Get the current time in seconds, divided by the time step (e.g., 30 seconds)
    public long getCurrentTimeCounter() {
        return System.currentTimeMillis()/ 1000L / TIME_STEP;
    }

    public static void main(String[] args) {
        try {
            library totpGenerator = new library();
            String  secret = "CQT6H2TUM2HCLNA5CKI7TD6CBXOARPC5";  // Example base64-encoded secret

            // Generate the TOTP using current time
            long timeCounter = totpGenerator.getCurrentTimeCounter();
            String digits = totpGenerator.generateHash(secret, timeCounter);

            // Output the generated TOTP
            System.out.println("Generated TOTP: " + digits);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

