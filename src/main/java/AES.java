
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class AES {
    private static final String ALGORITHM = "AES";
    private static final String ENCRYPTION_KEY = "encryptionKey";
    
    public static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException{
        byte[] keyBytes = secretKey.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        return keySpec;
    }
    public static String generateRegistrationKey(String username, String password){
        String registrationKey = username + ":" + password + ":" + ENCRYPTION_KEY;
        
        return registrationKey;
    }
    
    public static void saveRegistrationKeyToFile(String registrationKey, String filename) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(filename); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(registrationKey);
            
        }
    }
    
    public static String readRegistrationKeyFromFile(String filename) throws IOException, ClassNotFoundException{
        try (FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis)){
            return (String) ois.readObject();
        } 
    }
}
