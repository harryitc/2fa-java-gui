package totp;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.Timer;
import lib_totp.code.HashingAlgorithm;
import lib_totp.time.SystemTimeProvider;
import lib_totp.time.TimeProvider;
import org.apache.commons.codec.binary.Base32;


/**
 *
 * @author huuducngo
 */
public class frm_stepbystep_totp extends javax.swing.JFrame {

    /**
     * Creates new form frm_stepbystep_totp
     */
    
    private final String fileName = System.getProperty("user.dir") + "/secretkey.txt";
    private final int TIMESTEP = 30;
    private final int DIGIT_TOKEN = 6;
    private final int DELAY_PER_SECOND = 500;
    private final TimeProvider timeProvider = new SystemTimeProvider();
    Timer time;
    long currentTime, timeStep, timeStamp;
    Font highlightFont = new Font("Helvetica Neue", Font.BOLD, 13);
    Font normalFont = new Font("Helvetica Neue", Font.PLAIN, 13);
    
    
    public frm_stepbystep_totp() {
        initComponents();
        
        setFile();
        
        getTime();
        
    }
   
    private void getTime() {
        
        this.time = new Timer(DELAY_PER_SECOND, (ActionEvent e) -> {
            try {
                currentTime = (TIMESTEP - (timeProvider.getTime() % TIMESTEP));
                timeStep = (timeProvider.getTime() / TIMESTEP);
                timeStamp = (timeProvider.getTime());
                lb_second.setText(String.valueOf(currentTime) + "s");
                lb_time.setText(String.valueOf(timeStep));
                lb_timeStamp.setText(String.valueOf(timeStamp + "s"));
                
                hashMAC();
            } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
                Logger.getLogger(frm_stepbystep_totp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        time.start();
    }
    
    private void setFile() {
        try {
            StringBuffer sb;
            try (BufferedReader br = new BufferedReader(
                    new FileReader(fileName))) {
                sb = new StringBuffer();
                char[] ca = new char[5];
                while (br.ready()){
                    int len = br.read(ca);
                    sb.append(ca,0,len);
                }
            }
            System.out.println("Secret Key: " + sb);
            String str = sb.toString();
            
            txt_secretkey.setText(str.toUpperCase());
            
        } catch (IOException e) {
        }
    }
    
    private byte[] generateHash(String key, long counter) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(key);
        SecretKeySpec signKey = new SecretKeySpec(decodedKey, HashingAlgorithm.SHA1.getHmacAlgorithm());
        Mac mac = Mac.getInstance(HashingAlgorithm.SHA1.getHmacAlgorithm());
        mac.init(signKey);
        return mac.doFinal(data);
    }
    
    private void hashMAC() throws InvalidKeyException, NoSuchAlgorithmException {
        
        String key = txt_secretkey.getText();
        
        byte[] hash = generateHash(key, timeStep);
        
        String[] sb = new String[hash.length];
        
        for (int i = 0; i < hash.length; i++)
            sb[i] = Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1).toUpperCase();

        
        for(int i = 0; i < hash.length; i++) {
            setText(i, sb[i]);
            setFont(i, normalFont);
        }
            
        
        int offset = hash[hash.length - 1] & 0xF;
        lb_offset_byte.setText(String.valueOf(offset));
        for(int i = 0; i < 4; i++) {
            setText(i+20, String.valueOf(getText(offset + i)));
            setFont(i+20, highlightFont);
            setFont(offset + i, highlightFont);
        }
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        txt_token_byte.setText(String.valueOf(truncatedHash));
        
        truncatedHash %= Math.pow(10, DIGIT_TOKEN);
        String token = String.format("%0" + DIGIT_TOKEN + "d", truncatedHash);
        txt_token.setText(token);
    }
    
    
    
    private String getText(int i) {
        String result = null;
        switch (i) {
            case 0:
                result = txt_byte_1.getText();
                break;
            case 1:
                result = txt_byte_2.getText();
                break;
            case 2:
                result = txt_byte_3.getText();
                break;
            case 3:
                result = txt_byte_4.getText();
                break;
            case 4:
                result = txt_byte_5.getText();
                break;
            case 5:
                result = txt_byte_6.getText();
                break;
            case 6:
                result = txt_byte_7.getText();
                break;
            case 7:
                result = txt_byte_8.getText();
                break;
            case 8:
                result = txt_byte_9.getText();
                break;
            case 9:
                result = txt_byte_10.getText();
                break;
            case 10:
                result = txt_byte_11.getText();
                break;
            case 11:
                result = txt_byte_12.getText();
                break;
            case 12:
                result = txt_byte_13.getText();
                break;
            case 13:
                result = txt_byte_14.getText();
                break;
            case 14:
                result = txt_byte_15.getText();
                break;
            case 15:
                result = txt_byte_16.getText();
                break;
            case 16:
                result = txt_byte_17.getText();
                break;
            case 17:
                result = txt_byte_18.getText();
                break;
            case 18:
                result = txt_byte_19.getText();
                break;
            case 19:
                result = txt_byte_20.getText();
                break;
            case 20:
                result = txt_byte_21.getText();
                break;
            case 21:
                result = txt_byte_22.getText();
                break;
            case 22:
                result = txt_byte_23.getText();
                break;
            case 23:
                result = txt_byte_24.getText();
                break;
            default:
                throw new AssertionError();
        }
        return result;
    }
    
    private void setText(int i, String value) {
        switch (i) {
            case 0:
                txt_byte_1.setText(value);
                break;
            case 1:
                txt_byte_2.setText(value);
                break;
            case 2:
                txt_byte_3.setText(value);
                break;
            case 3:
                txt_byte_4.setText(value);
                break;
            case 4:
                txt_byte_5.setText(value);
                break;
            case 5:
                txt_byte_6.setText(value);
                break;
            case 6:
                txt_byte_7.setText(value);
                break;
            case 7:
                txt_byte_8.setText(value);
                break;
            case 8:
                txt_byte_9.setText(value);
                break;
            case 9:
                txt_byte_10.setText(value);
                break;
            case 10:
                txt_byte_11.setText(value);
                break;
            case 11:
                txt_byte_12.setText(value);
                break;
            case 12:
                txt_byte_13.setText(value);
                break;
            case 13:
                txt_byte_14.setText(value);
                break;
            case 14:
                txt_byte_15.setText(value);
                break;
            case 15:
                txt_byte_16.setText(value);
                break;
            case 16:
                txt_byte_17.setText(value);
                break;
            case 17:
                txt_byte_18.setText(value);
                break;
            case 18:
                txt_byte_19.setText(value);
                break;
            case 19:
                txt_byte_20.setText(value);
                break;
            case 20:
                txt_byte_21.setText(value);
                break;
            case 21:
                txt_byte_22.setText(value);
                break;
            case 22:
                txt_byte_23.setText(value);
                break;
            case 23:
                txt_byte_24.setText(value);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void setFont(int i, Font value) {
        switch (i) {
            case 0:
                txt_byte_1.setFont(value);
                break;
            case 1:
                txt_byte_2.setFont(value);
                break;
            case 2:
                txt_byte_3.setFont(value);
                break;
            case 3:
                txt_byte_4.setFont(value);
                break;
            case 4:
                txt_byte_5.setFont(value);
                break;
            case 5:
                txt_byte_6.setFont(value);
                break;
            case 6:
                txt_byte_7.setFont(value);
                break;
            case 7:
                txt_byte_8.setFont(value);
                break;
            case 8:
                txt_byte_9.setFont(value);
                break;
            case 9:
                txt_byte_10.setFont(value);
                break;
            case 10:
                txt_byte_11.setFont(value);
                break;
            case 11:
                txt_byte_12.setFont(value);
                break;
            case 12:
                txt_byte_13.setFont(value);
                break;
            case 13:
                txt_byte_14.setFont(value);
                break;
            case 14:
                txt_byte_15.setFont(value);
                break;
            case 15:
                txt_byte_16.setFont(value);
                break;
            case 16:
                txt_byte_17.setFont(value);
                break;
            case 17:
                txt_byte_18.setFont(value);
                break;
            case 18:
                txt_byte_19.setFont(value);
                break;
            case 19:
                txt_byte_20.setFont(value);
                break;
            case 20:
                txt_byte_21.setFont(value);
                break;
            case 21:
                txt_byte_22.setFont(value);
                break;
            case 22:
                txt_byte_23.setFont(value);
                break;
            case 23:
                txt_byte_24.setFont(value);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_Title = new javax.swing.JLabel();
        lb_Timestep = new javax.swing.JLabel();
        lb_time = new javax.swing.JLabel();
        lb_secretkey = new javax.swing.JLabel();
        txt_secretkey = new javax.swing.JTextField();
        txt_byte_1 = new javax.swing.JTextField();
        txt_byte_2 = new javax.swing.JTextField();
        txt_byte_3 = new javax.swing.JTextField();
        txt_byte_4 = new javax.swing.JTextField();
        txt_byte_5 = new javax.swing.JTextField();
        txt_byte_6 = new javax.swing.JTextField();
        txt_byte_7 = new javax.swing.JTextField();
        txt_byte_8 = new javax.swing.JTextField();
        txt_byte_9 = new javax.swing.JTextField();
        txt_byte_10 = new javax.swing.JTextField();
        txt_byte_11 = new javax.swing.JTextField();
        txt_byte_12 = new javax.swing.JTextField();
        txt_byte_13 = new javax.swing.JTextField();
        txt_byte_14 = new javax.swing.JTextField();
        txt_byte_15 = new javax.swing.JTextField();
        txt_byte_16 = new javax.swing.JTextField();
        txt_byte_17 = new javax.swing.JTextField();
        txt_byte_18 = new javax.swing.JTextField();
        txt_byte_19 = new javax.swing.JTextField();
        txt_byte_20 = new javax.swing.JTextField();
        lb_offset = new javax.swing.JLabel();
        txt_byte_21 = new javax.swing.JTextField();
        txt_byte_22 = new javax.swing.JTextField();
        txt_byte_23 = new javax.swing.JTextField();
        txt_byte_24 = new javax.swing.JTextField();
        lb_offset_byte = new javax.swing.JLabel();
        lb_formular_1 = new javax.swing.JLabel();
        txt_token_byte = new javax.swing.JTextField();
        lb_mod = new javax.swing.JLabel();
        lb_formular = new javax.swing.JLabel();
        txt_token = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lb_second = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_timeStamp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lb_Title.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        lb_Title.setText("STEP BY STEP - TOTP");

        lb_Timestep.setText("TimeStep:");

        lb_time.setText("TIME");

        lb_secretkey.setText("Secret Key:");

        txt_byte_1.setText("XX");

        txt_byte_2.setText("XX");

        txt_byte_3.setText("XX");

        txt_byte_4.setText("XX");

        txt_byte_5.setText("XX");

        txt_byte_6.setText("XX");

        txt_byte_7.setText("XX");

        txt_byte_8.setText("XX");

        txt_byte_9.setText("XX");

        txt_byte_10.setText("XX");

        txt_byte_11.setText("XX");

        txt_byte_12.setText("XX");

        txt_byte_13.setText("XX");

        txt_byte_14.setText("XX");

        txt_byte_15.setText("XX");

        txt_byte_16.setText("XX");

        txt_byte_17.setText("XX");

        txt_byte_18.setText("XX");

        txt_byte_19.setText("XX");

        txt_byte_20.setText("XX");

        lb_offset.setText("Offset:");

        txt_byte_21.setText("XX");

        txt_byte_22.setText("XX");

        txt_byte_23.setText("XX");

        txt_byte_24.setText("XX");

        lb_offset_byte.setText("XX");

        lb_formular_1.setText("+ 0x 7FFFFFFF");

        lb_mod.setText(") Mod 10^6");

        lb_formular.setText("(");

        txt_token.setFont(new java.awt.Font("Helvetica Neue", 1, 42)); // NOI18N

        jLabel1.setText("|");

        lb_second.setText("Second");

        jLabel2.setText("|");

        lb_timeStamp.setText("TimeStamp");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_formular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_formular_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_token, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lb_Title)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_Timestep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_time)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_second)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_timeStamp))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lb_secretkey)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_secretkey))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_byte_1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_token_byte, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_mod))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_byte_21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_byte_24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_offset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_offset_byte))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_byte_11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_byte_20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Title)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Timestep)
                    .addComponent(lb_time)
                    .addComponent(jLabel1)
                    .addComponent(lb_second)
                    .addComponent(jLabel2)
                    .addComponent(lb_timeStamp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_secretkey)
                    .addComponent(txt_secretkey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_byte_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_byte_12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_byte_11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_offset_byte)
                    .addComponent(lb_offset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_byte_24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_byte_22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_byte_21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_byte_23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_formular_1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_token_byte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_mod)
                            .addComponent(lb_formular)))
                    .addComponent(txt_token))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.time.stop();
    }//GEN-LAST:event_formWindowClosed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_stepbystep_totp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_stepbystep_totp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_stepbystep_totp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_stepbystep_totp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_stepbystep_totp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lb_Timestep;
    private javax.swing.JLabel lb_Title;
    private javax.swing.JLabel lb_formular;
    private javax.swing.JLabel lb_formular_1;
    private javax.swing.JLabel lb_mod;
    private javax.swing.JLabel lb_offset;
    private javax.swing.JLabel lb_offset_byte;
    private javax.swing.JLabel lb_second;
    private javax.swing.JLabel lb_secretkey;
    private javax.swing.JLabel lb_time;
    private javax.swing.JLabel lb_timeStamp;
    private javax.swing.JTextField txt_byte_1;
    private javax.swing.JTextField txt_byte_10;
    private javax.swing.JTextField txt_byte_11;
    private javax.swing.JTextField txt_byte_12;
    private javax.swing.JTextField txt_byte_13;
    private javax.swing.JTextField txt_byte_14;
    private javax.swing.JTextField txt_byte_15;
    private javax.swing.JTextField txt_byte_16;
    private javax.swing.JTextField txt_byte_17;
    private javax.swing.JTextField txt_byte_18;
    private javax.swing.JTextField txt_byte_19;
    private javax.swing.JTextField txt_byte_2;
    private javax.swing.JTextField txt_byte_20;
    private javax.swing.JTextField txt_byte_21;
    private javax.swing.JTextField txt_byte_22;
    private javax.swing.JTextField txt_byte_23;
    private javax.swing.JTextField txt_byte_24;
    private javax.swing.JTextField txt_byte_3;
    private javax.swing.JTextField txt_byte_4;
    private javax.swing.JTextField txt_byte_5;
    private javax.swing.JTextField txt_byte_6;
    private javax.swing.JTextField txt_byte_7;
    private javax.swing.JTextField txt_byte_8;
    private javax.swing.JTextField txt_byte_9;
    private javax.swing.JTextField txt_secretkey;
    private javax.swing.JTextField txt_token;
    private javax.swing.JTextField txt_token_byte;
    // End of variables declaration//GEN-END:variables
}
